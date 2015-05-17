package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.screens.Layout;
import net.npg.abattle.client.view.screens.ParameterScreen;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.Widgets;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class ErrorScreen extends BasicScreen implements ParameterScreen<String> {
  @Accessors
  private String parameter;
  
  private Label label;
  
  @Override
  public void create() {
    Widgets _widgets = this.getWidgets();
    Label _addLabel = _widgets.addLabel(Layout.IMPR_TEXT.x, Layout.IMPR_TEXT.y, "...");
    this.label = _addLabel;
    Widgets _widgets_1 = this.getWidgets();
    Label _addLabel_1 = _widgets_1.addLabel(Layout.IMPR_TEXT.x, Layout.IMPR_TEXT.y, "...");
    this.label = _addLabel_1;
    Widgets _widgets_2 = this.getWidgets();
    final Procedure0 _function = new Procedure0() {
      @Override
      public void apply() {
        ScreenSwitcher _switcher = ErrorScreen.this.getSwitcher();
        _switcher.switchToScreen(Screens.Main);
      }
    };
    _widgets_2.addButton(Layout.Back, Icons.Back, _function);
  }
  
  @Override
  public void render() {
    this.label.setText(("ERROR\r\n" + this.parameter));
  }
  
  @Override
  public void backButton() {
    ScreenSwitcher _switcher = this.getSwitcher();
    _switcher.switchToScreen(Screens.Main);
  }
  
  @Override
  public Screens getType() {
    return Screens.Error;
  }
  
  @Pure
  public String getParameter() {
    return this.parameter;
  }
  
  public void setParameter(final String parameter) {
    this.parameter = parameter;
  }
}
