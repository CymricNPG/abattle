package net.npg.abattle.client.view.screens;

import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.screens.Layout;
import net.npg.abattle.client.view.screens.MyTextButton;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.Widgets;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;

@SuppressWarnings("all")
public abstract class WinLooseScreen extends BasicScreen {
  public MyTextButton create(final Icons icon) {
    MyTextButton _xblockexpression = null;
    {
      Widgets _widgets = this.getWidgets();
      _widgets.addImage(50, 50, icon);
      Widgets _widgets_1 = this.getWidgets();
      final Procedure0 _function = new Procedure0() {
        @Override
        public void apply() {
          ScreenSwitcher _switcher = WinLooseScreen.this.getSwitcher();
          _switcher.switchToScreen(Screens.Main);
        }
      };
      _xblockexpression = _widgets_1.addButton(Layout.Back, Icons.Back, _function);
    }
    return _xblockexpression;
  }
  
  @Override
  public void render() {
  }
  
  @Override
  public void backButton() {
    ScreenSwitcher _switcher = this.getSwitcher();
    _switcher.switchToScreen(Screens.Main);
  }
}
