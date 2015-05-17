package net.npg.abattle.client.view.screens;

import net.npg.abattle.client.local.GameBaseParametersImpl;
import net.npg.abattle.client.local.LocalGameStartup;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.GameParameterPartScreen;
import net.npg.abattle.client.view.screens.GameScreen;
import net.npg.abattle.client.view.screens.MyStage;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.Widgets;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;

/**
 * single player screen
 */
@SuppressWarnings("all")
public class SingleScreen extends BasicScreen {
  private GameParameterPartScreen parameterScreen;
  
  @Override
  public void create() {
    Widgets _widgets = this.getWidgets();
    GameParameterPartScreen _gameParameterPartScreen = new GameParameterPartScreen(_widgets, false, this);
    this.parameterScreen = _gameParameterPartScreen;
    final Procedure0 _function = new Procedure0() {
      @Override
      public void apply() {
        SingleScreen.this.startGame();
      }
    };
    MyStage _stage = this.getStage();
    this.parameterScreen.create(_function, _stage);
  }
  
  public void startGame() {
    try {
      this.parameterScreen.finish();
      ConfigurationComponent _configuration = this.getConfiguration();
      _configuration.save();
      this.pause();
      GameBaseParametersImpl _gameParameters = this.parameterScreen.getGameParameters();
      ScreenSwitcher _switcher = this.getSwitcher();
      LocalGameStartup _localGameStartup = new LocalGameStartup(_gameParameters, _switcher);
      final GameScreen screen = _localGameStartup.run();
      ScreenSwitcher _switcher_1 = this.getSwitcher();
      _switcher_1.switchToScreen(screen);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
    return Screens.Single;
  }
  
  private ConfigurationComponent getConfiguration() {
    ComponentLookup _instance = ComponentLookup.getInstance();
    return _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
  }
}
