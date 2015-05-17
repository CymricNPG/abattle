package net.npg.abattle.server.game.impl;

import java.util.Collection;
import java.util.List;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.utils.FieldLoop;
import net.npg.abattle.common.utils.SingleList;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.commands.InitBoardCommand;
import net.npg.abattle.communication.command.data.CellData;
import net.npg.abattle.communication.command.data.CellDataBuilder;
import net.npg.abattle.communication.command.data.InitBoardData;
import net.npg.abattle.server.game.GameEnvironment;
import net.npg.abattle.server.model.BoardInitializedNotifier;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

@SuppressWarnings("all")
public class InitGameSender implements BoardInitializedNotifier {
  private GameEnvironment game;
  
  private CommandQueueServer commandQueue;
  
  public InitGameSender(final GameEnvironment game, final CommandQueueServer commandQueue) {
    Validate.notNulls(game, commandQueue);
    this.game = game;
    this.commandQueue = commandQueue;
  }
  
  private void addCommandToQueue(final InitBoardData boardUpdate, final Player player) {
    ServerGame _serverGame = this.game.getServerGame();
    int _id = _serverGame.getId();
    final InitBoardCommand command = new InitBoardCommand(boardUpdate, false, _id);
    int _id_1 = player.getId();
    List<Integer> _create = SingleList.<Integer>create(Integer.valueOf(_id_1));
    this.commandQueue.addCommand(command, CommandType.TOCLIENT, _create);
  }
  
  private InitBoardData createBoardUpdate(final Board<ServerPlayer, ServerCell, ServerLinks> board, final ServerPlayer player) {
    InitBoardData _xblockexpression = null;
    {
      final CellData[][] cellUpdates = this.fillCells(board);
      _xblockexpression = new InitBoardData(cellUpdates);
    }
    return _xblockexpression;
  }
  
  private CellData[][] fillCells(final Board<ServerPlayer, ServerCell, ServerLinks> board) {
    int _xSize = board.getXSize();
    int _ySize = board.getYSize();
    final CellData[][] cellUpdates = new CellData[_xSize][_ySize];
    int _xSize_1 = board.getXSize();
    int _ySize_1 = board.getYSize();
    final Procedure2<Integer, Integer> _function = new Procedure2<Integer, Integer>() {
      @Override
      public void apply(final Integer x, final Integer y) {
        final ServerCell originalCell = board.getCellAt((x).intValue(), (y).intValue());
        CellData[] _get = cellUpdates[(x).intValue()];
        CellData _buildCell = InitGameSender.this.buildCell(originalCell);
        _get[(y).intValue()] = _buildCell;
      }
    };
    FieldLoop.visitAllFields(_xSize_1, _ySize_1, _function);
    return cellUpdates;
  }
  
  private CellData buildCell(final ServerCell originalCell) {
    CellDataBuilder _cellDataBuilder = new CellDataBuilder();
    int _id = originalCell.getId();
    CellDataBuilder _id_1 = _cellDataBuilder.id(_id);
    int _height = originalCell.getHeight();
    CellDataBuilder _height_1 = _id_1.height(_height);
    CellTypes _cellType = originalCell.getCellType();
    int _ordinal = _cellType.ordinal();
    CellDataBuilder _cellType_1 = _height_1.cellType(_ordinal);
    return _cellType_1.build();
  }
  
  @Override
  public void boardCreated(final Board<ServerPlayer, ServerCell, ServerLinks> board) {
    ServerGame _serverGame = this.game.getServerGame();
    Collection<ServerPlayer> _players = _serverGame.getPlayers();
    final Function1<ServerPlayer, Boolean> _function = new Function1<ServerPlayer, Boolean>() {
      @Override
      public Boolean apply(final ServerPlayer it) {
        boolean _isComputer = it.isComputer();
        return Boolean.valueOf((!_isComputer));
      }
    };
    Iterable<ServerPlayer> _filter = IterableExtensions.<ServerPlayer>filter(_players, _function);
    for (final ServerPlayer player : _filter) {
      {
        final InitBoardData boardUpdate = this.createBoardUpdate(board, player);
        this.addCommandToQueue(boardUpdate, player);
      }
    }
  }
}
