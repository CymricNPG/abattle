package net.npg.abattle.client.view.screens;

import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.screens.Layout;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.Widgets;
import net.npg.abattle.common.CommonConstants;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;

@SuppressWarnings("all")
public class ImpressumScreen extends BasicScreen {
  private static String text = (("ABattle V" + CommonConstants.VERSION) + "\r\n\r\nRoland Spatzenegger\r\nWiesbachhornstr. 13\r\n81825 Muenchen\r\nTelefon: 089-40268100\r\nE-Mail: c@npg.net\r\n");
  
  @Override
  public void create() {
    Widgets _widgets = this.getWidgets();
    _widgets.addLabel(Layout.IMPR_TEXT.x, Layout.IMPR_TEXT.y, ImpressumScreen.text);
    Widgets _widgets_1 = this.getWidgets();
    final Procedure0 _function = new Procedure0() {
      @Override
      public void apply() {
        ScreenSwitcher _switcher = ImpressumScreen.this.getSwitcher();
        _switcher.switchToScreen(Screens.Main);
      }
    };
    _widgets_1.addButton(Layout.Back, Icons.Back, _function);
  }
  
  @Override
  public void render() {
  }
  
  @Override
  public void backButton() {
    ScreenSwitcher _switcher = this.getSwitcher();
    _switcher.switchToScreen(Screens.Main);
  }
  
  @Override
  public Screens getType() {
    return Screens.Impressum;
  }
}
