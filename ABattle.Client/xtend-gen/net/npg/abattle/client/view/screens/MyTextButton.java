package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.google.common.base.Objects;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class MyTextButton extends TextButton {
  private static MyTextButton lastOver = null;
  
  private Procedure1<Boolean> hoverProcedure;
  
  public MyTextButton(final String text, final TextButton.TextButtonStyle style) {
    super(text, style);
  }
  
  @Override
  public void draw(final Batch batch, final float parentAlpha) {
    boolean _and = false;
    boolean _and_1 = false;
    boolean _isOver = this.isOver();
    if (!_isOver) {
      _and_1 = false;
    } else {
      boolean _notEquals = (!Objects.equal(this, MyTextButton.lastOver));
      _and_1 = _notEquals;
    }
    if (!_and_1) {
      _and = false;
    } else {
      boolean _notEquals_1 = (!Objects.equal(this.hoverProcedure, null));
      _and = _notEquals_1;
    }
    if (_and) {
      this.hoverProcedure.apply(Boolean.valueOf(true));
      MyTextButton.lastOver = this;
    } else {
      boolean _and_2 = false;
      boolean _and_3 = false;
      boolean _isOver_1 = this.isOver();
      boolean _not = (!_isOver_1);
      if (!_not) {
        _and_3 = false;
      } else {
        boolean _equals = Objects.equal(this, MyTextButton.lastOver);
        _and_3 = _equals;
      }
      if (!_and_3) {
        _and_2 = false;
      } else {
        boolean _notEquals_2 = (!Objects.equal(this.hoverProcedure, null));
        _and_2 = _notEquals_2;
      }
      if (_and_2) {
        this.hoverProcedure.apply(Boolean.valueOf(false));
        MyTextButton.lastOver = null;
      }
    }
    super.draw(batch, parentAlpha);
  }
  
  public void addHoverListener(final Procedure1<Boolean> hoverProcedure) {
    this.hoverProcedure = hoverProcedure;
  }
}
