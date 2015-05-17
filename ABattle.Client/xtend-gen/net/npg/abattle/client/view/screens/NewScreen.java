package net.npg.abattle.client.view.screens;

import net.npg.abattle.client.ClientConstants;
import net.npg.abattle.client.lan.LANGameStartup;
import net.npg.abattle.client.local.GameBaseParametersImpl;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.GameParameterPartScreen;
import net.npg.abattle.client.view.screens.MyStage;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.Widgets;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;

@SuppressWarnings("all")
public class NewScreen extends BasicScreen {
  private GameParameterPartScreen parameterScreen;
  
  @Override
  public void create() {
    Widgets _widgets = this.getWidgets();
    GameParameterPartScreen _gameParameterPartScreen = new GameParameterPartScreen(_widgets, true, this);
    this.parameterScreen = _gameParameterPartScreen;
    final Procedure0 _function = new Procedure0() {
      @Override
      public void apply() {
        NewScreen.this.startGame();
      }
    };
    MyStage _stage = this.getStage();
    this.parameterScreen.create(_function, _stage);
  }
  
  public void startGame() {
    this.parameterScreen.finish();
    ConfigurationComponent _configuration = this.getConfiguration();
    _configuration.save();
    this.pause();
    GameBaseParametersImpl _gameParameters = this.parameterScreen.getGameParameters();
    final LANGameStartup startup = new LANGameStartup(_gameParameters);
    try {
      startup.create();
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        String _message = e.getMessage();
        ClientConstants.LOG.error(_message, e);
        startup.dispose();
        ScreenSwitcher _switcher = this.getSwitcher();
        String _message_1 = e.getMessage();
        _switcher.switchToScreen(Screens.Error, _message_1);
        return;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    ScreenSwitcher _switcher_1 = this.getSwitcher();
    _switcher_1.switchToScreen(Screens.Waiting, startup);
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
    return Screens.New;
  }
  
  private ConfigurationComponent getConfiguration() {
    ComponentLookup _instance = ComponentLookup.getInstance();
    return _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
  }
}
