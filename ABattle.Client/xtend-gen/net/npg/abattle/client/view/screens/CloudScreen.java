package net.npg.abattle.client.view.screens;

import com.google.common.base.Objects;
import net.npg.abattle.client.dependent.MultiplayerScreen;
import net.npg.abattle.client.dependent.UIDialogComponent;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.screens.Layout;
import net.npg.abattle.client.view.screens.MyStage;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.Widgets;
import net.npg.abattle.common.component.ComponentLookup;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;

@SuppressWarnings("all")
public class CloudScreen extends BasicScreen {
  private volatile MultiplayerScreen multiplayerScreen;
  
  public CloudScreen() {
    super();
  }
  
  @Override
  public void create() {
    Widgets _widgets = this.getWidgets();
    final Procedure0 _function = new Procedure0() {
      @Override
      public void apply() {
        ScreenSwitcher _switcher = CloudScreen.this.getSwitcher();
        _switcher.switchToScreen(Screens.Main);
      }
    };
    _widgets.addButton(Layout.Back, Icons.Back, _function);
    MultiplayerScreen _multiplayerScr = this.getMultiplayerScr();
    Widgets _widgets_1 = this.getWidgets();
    MyStage _stage = this.getStage();
    _multiplayerScr.create(_widgets_1, _stage, this);
  }
  
  @Override
  public void render() {
    MultiplayerScreen _multiplayerScr = this.getMultiplayerScr();
    ScreenSwitcher _switcher = this.getSwitcher();
    _multiplayerScr.setSwitcher(_switcher);
  }
  
  @Override
  public void backButton() {
    ScreenSwitcher _switcher = this.getSwitcher();
    _switcher.switchToScreen(Screens.Main);
  }
  
  @Override
  public Screens getType() {
    return Screens.Cloud;
  }
  
  private MultiplayerScreen getMultiplayerScr() {
    boolean _equals = Objects.equal(this.multiplayerScreen, null);
    if (_equals) {
      ComponentLookup _instance = ComponentLookup.getInstance();
      UIDialogComponent _component = _instance.<UIDialogComponent>getComponent(UIDialogComponent.class);
      MultiplayerScreen _multiplayerScreen = _component.getMultiplayerScreen();
      this.multiplayerScreen = _multiplayerScreen;
    }
    return this.multiplayerScreen;
  }
  
  @Override
  public String getDefaultText() {
    MultiplayerScreen _multiplayerScr = this.getMultiplayerScr();
    return _multiplayerScr.getDefaultText();
  }
}
