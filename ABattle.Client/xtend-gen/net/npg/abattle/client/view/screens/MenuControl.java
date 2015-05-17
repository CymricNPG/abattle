package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import net.npg.abattle.client.startup.Startup;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.ScreenManager;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.common.component.ExternalRegisterComponent;
import net.npg.abattle.common.error.impl.SimpleErrorHandler;
import net.npg.abattle.common.utils.LifecycleControl;
import net.npg.abattle.common.utils.Validate;

@SuppressWarnings("all")
public class MenuControl extends Game {
  private static boolean firstBoot = true;
  
  private ExternalRegisterComponent externalRegisterComponent;
  
  public MenuControl(final ExternalRegisterComponent externalRegisterComponent) {
    Validate.notNulls(externalRegisterComponent);
    this.externalRegisterComponent = externalRegisterComponent;
  }
  
  @Override
  public void create() {
    if (MenuControl.firstBoot) {
      LifecycleControl _control = LifecycleControl.getControl();
      _control.setSuppressPaused(true);
      SimpleErrorHandler _simpleErrorHandler = new SimpleErrorHandler();
      Startup.l0(_simpleErrorHandler, this.externalRegisterComponent);
      MenuControl.firstBoot = false;
    } else {
      Startup.restart0();
    }
    final ScreenManager screenManager = new ScreenManager(this);
    screenManager.init();
    screenManager.switchToScreen(Screens.Main);
  }
  
  @Override
  public void resize(final int width, final int height) {
    super.resize(width, height);
  }
  
  @Override
  public void setScreen(final Screen screen) {
    if ((screen instanceof BasicScreen)) {
      ((BasicScreen) screen).init();
    }
    super.setScreen(screen);
  }
}
