package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.Gdx;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.screens.Layout;
import net.npg.abattle.client.view.screens.MyTextButton;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.Widgets;
import net.npg.abattle.common.i18n.I18N;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;

@SuppressWarnings("all")
public class MenuScreen extends BasicScreen {
  @Override
  public void create() {
    this.addButton(Layout.Menu_Option, Icons.Options, Screens.Options, Screens.Main);
    this.addButton(Layout.Menu_Single, Icons.Single, Screens.Single);
    this.addButton(Layout.Menu_Cloud, Icons.Cloud, Screens.Cloud);
    this.addButton(Layout.Menu_Local, Icons.Local, Screens.Local);
    this.addButton(Layout.Menu_Help, Icons.Help, Screens.Help);
    this.addButton(Layout.Menu_Impr, Icons.Impressum, Screens.Impressum);
  }
  
  private MyTextButton addButton(final Layout position, final Icons icon, final Screens screen, final Screens back) {
    Widgets _widgets = this.getWidgets();
    final Procedure0 _function = new Procedure0() {
      @Override
      public void apply() {
        ScreenSwitcher _switcher = MenuScreen.this.getSwitcher();
        _switcher.switchToScreen(screen, back);
      }
    };
    return _widgets.addButton(position, icon, _function);
  }
  
  private MyTextButton addButton(final Layout position, final Icons icon, final Screens screen) {
    Widgets _widgets = this.getWidgets();
    final Procedure0 _function = new Procedure0() {
      @Override
      public void apply() {
        ScreenSwitcher _switcher = MenuScreen.this.getSwitcher();
        _switcher.switchToScreen(screen);
      }
    };
    return _widgets.addButton(position, icon, _function);
  }
  
  @Override
  public String getDefaultText() {
    return I18N.get("menu_default");
  }
  
  @Override
  public void render() {
  }
  
  @Override
  public void backButton() {
    Gdx.app.exit();
  }
  
  @Override
  public Screens getType() {
    return Screens.Main;
  }
}
