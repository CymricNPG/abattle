package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.client.view.boardscene.SceneRenderer;
import net.npg.abattle.client.view.boardscene.VisitableSceneElement;
import net.npg.abattle.common.error.ErrorHandler;
import net.npg.abattle.common.error.ErrorInfo;
import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientLinks;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.utils.FloatPoint;
import net.npg.abattle.common.utils.IntPoint;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class ErrorMessage implements VisitableSceneElement, ErrorHandler {
  private volatile long timeout = 0;
  
  private final static long DISPLAY_TIME = (1000L * 4);
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private volatile String errorMessage;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private FloatPoint pos;
  
  public ErrorMessage(final ClientGame game, final HexBase hexBase) {
    Board<ClientPlayer, ClientCell, ClientLinks> _board = game.getBoard();
    int _ySize = _board.getYSize();
    int _minus = (_ySize - 1);
    IntPoint _from = IntPoint.from(1, _minus);
    final Hex hex = new Hex(_from, hexBase);
    float _centerX = hex.getCenterX();
    float _bottom = hex.getBottom();
    double _plus = (_bottom + 0.2);
    FloatPoint _floatPoint = new FloatPoint(_centerX, _plus);
    this.pos = _floatPoint;
  }
  
  @Override
  public void accept(final SceneRenderer visitor) {
    visitor.visit(this);
  }
  
  public boolean isVisible() {
    boolean _and = false;
    long _currentTimeMillis = System.currentTimeMillis();
    boolean _greaterThan = (this.timeout > _currentTimeMillis);
    if (!_greaterThan) {
      _and = false;
    } else {
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(this.errorMessage);
      boolean _not = (!_isNullOrEmpty);
      _and = _not;
    }
    return _and;
  }
  
  @Override
  public void handleError(final ErrorInfo message) {
    String _message = message.getMessage();
    this.errorMessage = _message;
    long _currentTimeMillis = System.currentTimeMillis();
    long _plus = (_currentTimeMillis + ErrorMessage.DISPLAY_TIME);
    this.timeout = _plus;
  }
  
  @Pure
  public String getErrorMessage() {
    return this.errorMessage;
  }
  
  @Pure
  public FloatPoint getPos() {
    return this.pos;
  }
}
