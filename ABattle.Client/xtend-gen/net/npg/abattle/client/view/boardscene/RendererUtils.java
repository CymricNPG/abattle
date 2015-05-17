package net.npg.abattle.client.view.boardscene;

import com.badlogic.gdx.graphics.Color;
import com.google.common.base.Optional;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.utils.ColorConvert;

@SuppressWarnings("all")
public class RendererUtils {
  /**
   * animation timer
   */
  private final static long startTime = System.currentTimeMillis();
  
  /**
   * returns a number between 0 and animationCount, which is increased every 1/animationsPerSecond
   */
  public static int getAnimationNumber(final float animationsPerSecond, final int animationCount) {
    long _currentTimeMillis = System.currentTimeMillis();
    final long time = (_currentTimeMillis - RendererUtils.startTime);
    return (((int) ((((float) time) * animationsPerSecond) / 1000.0)) % animationCount);
  }
  
  public static Color getOwnerColor(final ClientCell cell) {
    final Optional<Player> owner = cell.<Player>getOwner();
    boolean _isPresent = owner.isPresent();
    if (_isPresent) {
      Player _get = owner.get();
      net.npg.abattle.common.model.Color _color = _get.getColor();
      return ColorConvert.convert(_color);
    } else {
      return Color.WHITE;
    }
  }
}
