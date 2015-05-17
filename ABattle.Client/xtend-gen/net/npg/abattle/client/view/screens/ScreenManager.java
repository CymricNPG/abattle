package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.Game;
import com.google.common.base.Objects;
import net.npg.abattle.client.view.screens.CloudScreen;
import net.npg.abattle.client.view.screens.ErrorScreen;
import net.npg.abattle.client.view.screens.HelpScreen;
import net.npg.abattle.client.view.screens.ImpressumScreen;
import net.npg.abattle.client.view.screens.LocalScreen;
import net.npg.abattle.client.view.screens.LooseScreen;
import net.npg.abattle.client.view.screens.MenuScreen;
import net.npg.abattle.client.view.screens.MyScreen;
import net.npg.abattle.client.view.screens.NewScreen;
import net.npg.abattle.client.view.screens.OptionsScreen;
import net.npg.abattle.client.view.screens.ParameterScreen;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.SingleScreen;
import net.npg.abattle.client.view.screens.WaitingScreen;
import net.npg.abattle.client.view.screens.WinScreen;
import net.npg.abattle.common.utils.MyHashMap;
import net.npg.abattle.common.utils.MyMap;
import net.npg.abattle.common.utils.Validate;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ScreenManager implements ScreenSwitcher {
  private MyMap<Screens, Class<? extends MyScreen>> screens;
  
  private MyScreen currentScreen;
  
  private Game control;
  
  public ScreenManager(final Game control) {
    MyHashMap<Screens, Class<? extends MyScreen>> _myHashMap = new MyHashMap<Screens, Class<? extends MyScreen>>();
    this.screens = _myHashMap;
    this.control = control;
    this.currentScreen = null;
  }
  
  public Class<? extends MyScreen> init() {
    Class<? extends MyScreen> _xblockexpression = null;
    {
      this.loadScreen(Screens.Local, LocalScreen.class);
      this.loadScreen(Screens.Cloud, CloudScreen.class);
      this.loadScreen(Screens.Options, OptionsScreen.class);
      this.loadScreen(Screens.Help, HelpScreen.class);
      this.loadScreen(Screens.Main, MenuScreen.class);
      this.loadScreen(Screens.Single, SingleScreen.class);
      this.loadScreen(Screens.Win, WinScreen.class);
      this.loadScreen(Screens.Loose, LooseScreen.class);
      this.loadScreen(Screens.Waiting, WaitingScreen.class);
      this.loadScreen(Screens.New, NewScreen.class);
      this.loadScreen(Screens.Impressum, ImpressumScreen.class);
      _xblockexpression = this.loadScreen(Screens.Error, ErrorScreen.class);
    }
    return _xblockexpression;
  }
  
  private Class<? extends MyScreen> loadScreen(final Screens screenId, final Class<? extends MyScreen> screen) {
    return this.screens.put(screenId, screen);
  }
  
  @Override
  public void switchToScreen(final Screens newScreen) {
    Validate.notNull(newScreen);
    final MyScreen screen = this.instantiateScreen(newScreen);
    assert (!(screen instanceof ParameterScreen));
    this.switchControl(screen);
  }
  
  private MyScreen switchControl(final MyScreen screen) {
    MyScreen _xblockexpression = null;
    {
      this.control.setScreen(screen);
      boolean _notEquals = (!Objects.equal(this.currentScreen, null));
      if (_notEquals) {
        this.currentScreen.dispose(true);
      }
      _xblockexpression = this.currentScreen = screen;
    }
    return _xblockexpression;
  }
  
  @Override
  public void switchToScreen(final Screens newScreen, final Object parameter) {
    Validate.notNull(newScreen);
    final MyScreen screen = this.instantiateScreen(newScreen);
    if ((screen instanceof ParameterScreen)) {
      ((ParameterScreen)screen).setParameter(parameter);
    }
    this.switchControl(screen);
  }
  
  private MyScreen instantiateScreen(final Screens screen) {
    try {
      final Class<? extends MyScreen> screenClass = this.screens.get(screen);
      final MyScreen newScreen = screenClass.newInstance();
      newScreen.instantiate(this);
      return newScreen;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public void switchToScreen(final MyScreen newScreen) {
    Validate.notNull(newScreen);
    assert (!(newScreen instanceof ParameterScreen));
    this.switchControl(newScreen);
  }
}
