package net.npg.abattle.android;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.google.common.base.Objects;
import net.npg.abattle.client.dependent.MultiplayerScreen;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.GameParameterPartScreen;
import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.screens.Layout;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.Widgets;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.i18n.I18N;
import net.npg.abattle.common.utils.Validate;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;

@SuppressWarnings("all")
public class MultiplayerScreenImpl implements MultiplayerScreen {
  private ScreenSwitcher switcher;
  
  private GameParameterPartScreen parameterScreen;
  
  @Override
  public void create(final Widgets widgets, final Stage stage, final BasicScreen screen) {
    final Procedure0 _function = new Procedure0() {
      @Override
      public void apply() {
        MultiplayerScreenImpl.this.quickStart();
      }
    };
    widgets.addButton(Layout.MULTI_QUICK, Icons.Quick, _function);
    final Procedure0 _function_1 = new Procedure0() {
      @Override
      public void apply() {
        MultiplayerScreenImpl.this.joinInvites();
      }
    };
    widgets.addButton(Layout.MULTI_INVITE, Icons.Invites, _function_1);
    GameParameterPartScreen _gameParameterPartScreen = new GameParameterPartScreen(widgets, true, screen);
    this.parameterScreen = _gameParameterPartScreen;
    final Procedure0 _function_2 = new Procedure0() {
      @Override
      public void apply() {
        MultiplayerScreenImpl.this.newGame();
      }
    };
    this.parameterScreen.create(_function_2, stage);
  }
  
  public void newGame() {
    boolean _notEquals = (!Objects.equal(this.switcher, null));
    if (_notEquals) {
      this.switcher.switchToScreen(Screens.Main);
    }
  }
  
  private void joinInvites() {
    boolean _notEquals = (!Objects.equal(this.switcher, null));
    if (_notEquals) {
      this.switcher.switchToScreen(Screens.Main);
    }
  }
  
  private void quickStart() {
    boolean _notEquals = (!Objects.equal(this.switcher, null));
    if (_notEquals) {
      this.switcher.switchToScreen(Screens.Main);
    }
    this.parameterScreen.finish();
    ComponentLookup _instance = ComponentLookup.getInstance();
    ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
    _component.save();
  }
  
  @Override
  public String getDefaultText() {
    return I18N.get("multi.default");
  }
  
  @Override
  public void setSwitcher(final ScreenSwitcher arg0) {
    Validate.notNull(arg0);
    this.switcher = arg0;
  }
}
