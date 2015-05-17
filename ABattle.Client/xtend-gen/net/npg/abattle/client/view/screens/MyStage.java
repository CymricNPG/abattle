package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.google.common.base.Objects;

@SuppressWarnings("all")
public class MyStage extends Stage {
  /**
   * Hard key listener
   */
  public interface OnHardKeyListener {
    /**
     * Happens when user press hard key
     * @param keyCode Back or Menu key (keyCode one of the constants in Input.Keys)
     * @param state 1 - key down, 0 - key up
     */
    public abstract void onHardKey(final int keyCode, final int state);
  }
  
  public MyStage() {
    super();
  }
  
  @Override
  public boolean keyDown(final int keyCode) {
    boolean _isBackKey = this.isBackKey(keyCode);
    if (_isBackKey) {
      MyStage.OnHardKeyListener _hardKeyListener = this.getHardKeyListener();
      boolean _notEquals = (!Objects.equal(_hardKeyListener, null));
      if (_notEquals) {
        MyStage.OnHardKeyListener _hardKeyListener_1 = this.getHardKeyListener();
        _hardKeyListener_1.onHardKey(keyCode, 1);
      }
    }
    return super.keyDown(keyCode);
  }
  
  public boolean isBackKey(final int keyCode) {
    return (((keyCode == Input.Keys.BACK) || (keyCode == Input.Keys.MENU)) || (keyCode == Input.Keys.ESCAPE));
  }
  
  @Override
  public boolean keyUp(final int keyCode) {
    boolean _isBackKey = this.isBackKey(keyCode);
    if (_isBackKey) {
      MyStage.OnHardKeyListener _hardKeyListener = this.getHardKeyListener();
      boolean _notEquals = (!Objects.equal(_hardKeyListener, null));
      if (_notEquals) {
        MyStage.OnHardKeyListener _hardKeyListener_1 = this.getHardKeyListener();
        _hardKeyListener_1.onHardKey(keyCode, 0);
      }
    }
    return super.keyUp(keyCode);
  }
  
  private MyStage.OnHardKeyListener _HardKeyListener = null;
  
  public void setHardKeyListener(final MyStage.OnHardKeyListener HardKeyListener) {
    this._HardKeyListener = HardKeyListener;
  }
  
  public MyStage.OnHardKeyListener getHardKeyListener() {
    return this._HardKeyListener;
  }
}
