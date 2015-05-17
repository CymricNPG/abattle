package net.npg.abattle.client.commands;

import com.google.common.base.Optional;
import net.npg.abattle.client.ClientException;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientLinks;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.client.impl.ClientCellImpl;
import net.npg.abattle.common.utils.FieldLoop;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandProcessor;
import net.npg.abattle.communication.command.CommandUpdateNotifier;
import net.npg.abattle.communication.command.ErrorCommand;
import net.npg.abattle.communication.command.commands.InitBoardCommand;
import net.npg.abattle.communication.command.data.CellData;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

@SuppressWarnings("all")
public class InitBoardCommandProcessor implements CommandProcessor<InitBoardCommand> {
  private ClientGame game;
  
  private CommandUpdateNotifier notifier;
  
  public InitBoardCommandProcessor(final ClientGame game, final CommandUpdateNotifier notifier) {
    Validate.notNulls(game, notifier);
    this.game = game;
    this.notifier = notifier;
  }
  
  @Override
  public Optional<ErrorCommand> execute(final InitBoardCommand command, final int destination) {
    try {
      Optional<ErrorCommand> _xblockexpression = null;
      {
        Validate.notNull(command);
        final CellData[][] cellUpdates = command.initBoard.cells;
        Board<ClientPlayer, ClientCell, ClientLinks> _board = this.game.getBoard();
        final ClientBoard board = ((ClientBoard) _board);
        boolean _isUpdateValid = this.isUpdateValid(board, cellUpdates);
        boolean _not = (!_isUpdateValid);
        if (_not) {
          throw new ClientException("Server send wrong board update");
        }
        this.updateBoard(board, cellUpdates);
        this.notifier.receivedCommand(command);
        _xblockexpression = Optional.<ErrorCommand>absent();
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void updateBoard(final Board<ClientPlayer, ClientCell, ClientLinks> board, final CellData[][] cellUpdates) {
    int _xSize = board.getXSize();
    int _ySize = board.getYSize();
    final Procedure2<Integer, Integer> _function = new Procedure2<Integer, Integer>() {
      @Override
      public void apply(final Integer x, final Integer y) {
        CellData[] _get = cellUpdates[(x).intValue()];
        CellData _get_1 = _get[(y).intValue()];
        InitBoardCommandProcessor.this.createCell(board, (x).intValue(), (y).intValue(), _get_1);
      }
    };
    FieldLoop.visitAllFields(_xSize, _ySize, _function);
  }
  
  private void createCell(final Board<ClientPlayer, ClientCell, ClientLinks> board, final int x, final int y, final CellData update) {
    IntPoint _from = IntPoint.from(x, y);
    CellTypes[] _values = CellTypes.values();
    CellTypes _get = _values[update.cellType];
    GameConfiguration _gameConfiguration = this.game.getGameConfiguration();
    CheckModelElement _checker = _gameConfiguration.getChecker();
    final ClientCellImpl newCell = new ClientCellImpl(update.id, _from, update.height, _get, _checker);
    this.updateCell(newCell, update);
    board.setCellAt(newCell);
  }
  
  private void updateCell(final ClientCell cell, final CellData update) {
    cell.setVisible(false);
    cell.setHeight(update.height);
    CellTypes[] _values = CellTypes.values();
    final CellTypes cellType = _values[update.cellType];
    cell.setCellType(cellType);
  }
  
  private boolean isUpdateValid(final Board<ClientPlayer, ClientCell, ClientLinks> board, final CellData[][] cellUpdates) {
    int _xSize = board.getXSize();
    int _length = cellUpdates.length;
    boolean _equals = (_xSize == _length);
    if (_equals) {
      int _ySize = board.getYSize();
      CellData[] _get = cellUpdates[0];
      int _length_1 = _get.length;
      return (_ySize == _length_1);
    }
    return false;
  }
}
