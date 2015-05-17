package net.npg.abattle.server.game.impl.fog;

import com.google.common.base.Optional;
import net.npg.abattle.common.hex.Directions;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.server.game.impl.fog.Fog;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerPlayer;

@SuppressWarnings("all")
public class SimpleFog implements Fog {
  protected final static String NAME = "simplefog";
  
  @Override
  public String getName() {
    return SimpleFog.NAME;
  }
  
  @Override
  public boolean isVisible(final ClientCell[][] clientBoard, final ServerBoard board, final ServerPlayer player, final ClientCell cell) {
    IntPoint _boardCoordinate = cell.getBoardCoordinate();
    final int x = _boardCoordinate.x;
    IntPoint _boardCoordinate_1 = cell.getBoardCoordinate();
    final int y = _boardCoordinate_1.y;
    for (final Directions direction : Directions.cachedValues) {
      {
        int _xadd = direction.getXadd();
        final int newX = (x + _xadd);
        int _yadd = direction.getYadd(x);
        final int newY = (y + _yadd);
        boolean _isOutsideBoard = this.isOutsideBoard(board, newX, newY);
        if(_isOutsideBoard) continue;;
        ClientCell[] _get = clientBoard[newX];
        final ClientCell testCell = _get[newY];
        boolean _and = false;
        Optional<Player> _owner = testCell.<Player>getOwner();
        boolean _isPresent = _owner.isPresent();
        if (!_isPresent) {
          _and = false;
        } else {
          int _id = player.getId();
          Optional<Player> _owner_1 = testCell.<Player>getOwner();
          Player _get_1 = _owner_1.get();
          int _id_1 = _get_1.getId();
          boolean _equals = (_id == _id_1);
          _and = _equals;
        }
        if (_and) {
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean isOutsideBoard(final ServerBoard board, final int newX, final int newY) {
    boolean _or = false;
    boolean _or_1 = false;
    if (((newX < 0) || (newY < 0))) {
      _or_1 = true;
    } else {
      int _xSize = board.getXSize();
      boolean _greaterEqualsThan = (newX >= _xSize);
      _or_1 = _greaterEqualsThan;
    }
    if (_or_1) {
      _or = true;
    } else {
      int _ySize = board.getYSize();
      boolean _greaterEqualsThan_1 = (newY >= _ySize);
      _or = _greaterEqualsThan_1;
    }
    return _or;
  }
}
