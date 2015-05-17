package net.npg.abattle.server.game.impl;

import com.google.common.base.Optional;
import java.util.Collection;
import java.util.List;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.Link;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientLinks;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.utils.FieldLoop;
import net.npg.abattle.common.utils.SingleList;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.commands.BoardUpdateCommand;
import net.npg.abattle.communication.command.data.BoardUpdateData;
import net.npg.abattle.communication.command.data.CellUpdateData;
import net.npg.abattle.communication.command.data.CellUpdateDataBuilder;
import net.npg.abattle.communication.command.data.LinkUpdateData;
import net.npg.abattle.communication.command.data.LinkUpdateHelper;
import net.npg.abattle.server.game.GameEnvironment;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerPlayer;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

@SuppressWarnings("all")
public class GameSender {
  private GameEnvironment game;
  
  private CommandQueueServer commandQueue;
  
  public GameSender(final GameEnvironment game, final CommandQueueServer commandQueue) {
    Validate.notNull(game);
    Validate.notNull(commandQueue);
    this.game = game;
    this.commandQueue = commandQueue;
  }
  
  public void sendGameData() {
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
        final ClientGame clientGame = this.game.getClientGame(player);
        Board<ClientPlayer, ClientCell, ClientLinks> _board = clientGame.getBoard();
        final BoardUpdateData boardUpdate = this.createBoardUpdate(_board, player);
        this.addCommandToQueue(boardUpdate, player);
      }
    }
  }
  
  private void addCommandToQueue(final BoardUpdateData boardUpdate, final Player player) {
    ServerGame _serverGame = this.game.getServerGame();
    int _id = _serverGame.getId();
    final BoardUpdateCommand command = new BoardUpdateCommand(boardUpdate, false, _id);
    int _id_1 = player.getId();
    List<Integer> _create = SingleList.<Integer>create(Integer.valueOf(_id_1));
    this.commandQueue.addCommand(command, CommandType.TOCLIENT, _create);
  }
  
  private BoardUpdateData createBoardUpdate(final Board<ClientPlayer, ClientCell, ClientLinks> board, final ServerPlayer player) {
    BoardUpdateData _xblockexpression = null;
    {
      final CellUpdateData[][] cellUpdates = this.fillCells(board);
      final LinkUpdateData[] linkUpdates = this.fillLinks(board);
      _xblockexpression = new BoardUpdateData(cellUpdates, linkUpdates);
    }
    return _xblockexpression;
  }
  
  private LinkUpdateData[] fillLinks(final Board<ClientPlayer, ClientCell, ClientLinks> board) {
    ClientLinks _links = board.getLinks();
    final Collection<Link<ClientCell>> links = _links.getLinks();
    final Function1<Link<ClientCell>, LinkUpdateData> _function = new Function1<Link<ClientCell>, LinkUpdateData>() {
      @Override
      public LinkUpdateData apply(final Link<ClientCell> it) {
        int _id = it.getId();
        ClientCell _sourceCell = it.getSourceCell();
        int _id_1 = _sourceCell.getId();
        ClientCell _destinationCell = it.getDestinationCell();
        int _id_2 = _destinationCell.getId();
        return new LinkUpdateData(_id, _id_1, _id_2);
      }
    };
    Iterable<LinkUpdateData> _map = IterableExtensions.<Link<ClientCell>, LinkUpdateData>map(links, _function);
    return LinkUpdateHelper.toArray(_map);
  }
  
  private CellUpdateData[][] fillCells(final Board<ClientPlayer, ClientCell, ClientLinks> board) {
    int _xSize = board.getXSize();
    int _ySize = board.getYSize();
    final CellUpdateData[][] cellUpdates = new CellUpdateData[_xSize][_ySize];
    int _xSize_1 = board.getXSize();
    int _ySize_1 = board.getYSize();
    final Procedure2<Integer, Integer> _function = new Procedure2<Integer, Integer>() {
      @Override
      public void apply(final Integer x, final Integer y) {
        final ClientCell originalCell = board.getCellAt((x).intValue(), (y).intValue());
        CellUpdateData _xifexpression = null;
        boolean _isVisible = originalCell.isVisible();
        if (_isVisible) {
          _xifexpression = GameSender.this.buildCell(originalCell);
        } else {
          _xifexpression = null;
        }
        final CellUpdateData cell = _xifexpression;
        CellUpdateData[] _get = cellUpdates[(x).intValue()];
        _get[(y).intValue()] = cell;
      }
    };
    FieldLoop.visitAllFields(_xSize_1, _ySize_1, _function);
    return cellUpdates;
  }
  
  private CellUpdateData buildCell(final ClientCell originalCell) {
    CellUpdateData _xblockexpression = null;
    {
      int _xifexpression = (int) 0;
      Optional<Player> _owner = originalCell.<Player>getOwner();
      boolean _isPresent = _owner.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        _xifexpression = ((int) 0);
      } else {
        Optional<Player> _owner_1 = originalCell.<Player>getOwner();
        Player _get = _owner_1.get();
        _xifexpression = _get.getId();
      }
      final int ownerId = _xifexpression;
      CellUpdateDataBuilder _cellUpdateDataBuilder = new CellUpdateDataBuilder();
      int _id = originalCell.getId();
      CellUpdateDataBuilder _id_1 = _cellUpdateDataBuilder.id(_id);
      CellUpdateDataBuilder _owner_2 = _id_1.owner(ownerId);
      boolean _hasBattle = originalCell.hasBattle();
      CellUpdateDataBuilder _battle = _owner_2.battle(_hasBattle);
      int _strength = originalCell.getStrength();
      CellUpdateDataBuilder _strength_1 = _battle.strength(_strength);
      _xblockexpression = _strength_1.build();
    }
    return _xblockexpression;
  }
}
