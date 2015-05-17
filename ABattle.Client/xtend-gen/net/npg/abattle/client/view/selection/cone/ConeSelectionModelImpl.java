package net.npg.abattle.client.view.selection.cone;

import net.npg.abattle.client.view.selection.twocells.Cursor;
import net.npg.abattle.client.view.selection.twocells.SelectionModelImpl;
import net.npg.abattle.common.hex.Directions;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.utils.FloatPoint;
import net.npg.abattle.common.utils.IntPoint;

@SuppressWarnings("all")
public class ConeSelectionModelImpl extends SelectionModelImpl {
  private HexBase hexBase;
  
  private ClientBoard board;
  
  public ConeSelectionModelImpl(final ClientBoard board, final HexBase hexBase, final ClientPlayer localPlayer) {
    super(board, hexBase, localPlayer);
    this.hexBase = hexBase;
    this.board = board;
  }
  
  @Override
  public synchronized void dragSelection(final FloatPoint screenCoordinate) {
    boolean _inSelectionMode = this.inSelectionMode();
    boolean _not = (!_inSelectionMode);
    if(_not) return;;
    IntPoint _cellByPoint = this.hexBase.getCellByPoint(screenCoordinate);
    Cursor _startCursor = this.getStartCursor();
    IntPoint _boardCoordinate = _startCursor.getBoardCoordinate();
    boolean _equals = _cellByPoint.equals(_boardCoordinate);
    if (_equals) {
      Cursor _endCursor = this.getEndCursor();
      _endCursor.hide();
      return;
    }
    Cursor _startCursor_1 = this.getStartCursor();
    FloatPoint _worldCoordinate = _startCursor_1.getWorldCoordinate();
    final Directions direction = this.hexBase.getDirections(_worldCoordinate, screenCoordinate);
    Cursor _startCursor_2 = this.getStartCursor();
    IntPoint _boardCoordinate_1 = _startCursor_2.getBoardCoordinate();
    final IntPoint endBoardCoordinate = direction.getAdjacentCoordinateTo(_boardCoordinate_1);
    Cursor _endCursor_1 = this.getEndCursor();
    boolean _setBoardCoordinate = _endCursor_1.setBoardCoordinate(endBoardCoordinate);
    if (_setBoardCoordinate) {
      Cursor _endCursor_2 = this.getEndCursor();
      _endCursor_2.show();
    } else {
      Cursor _endCursor_3 = this.getEndCursor();
      _endCursor_3.hide();
    }
  }
  
  @Override
  public synchronized void endSelection(final FloatPoint coordinate) {
    IntPoint _cellByPoint = this.hexBase.getCellByPoint(coordinate);
    Cursor _startCursor = this.getStartCursor();
    IntPoint _boardCoordinate = _startCursor.getBoardCoordinate();
    boolean _equals = _cellByPoint.equals(_boardCoordinate);
    if (_equals) {
      Cursor _endCursor = this.getEndCursor();
      _endCursor.setWorldCoordinate(coordinate);
    }
    this.resetSelection();
  }
}
