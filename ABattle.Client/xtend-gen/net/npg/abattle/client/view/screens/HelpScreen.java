package net.npg.abattle.client.view.screens;

import net.npg.abattle.client.asset.AssetManager;
import net.npg.abattle.client.dependent.RequestHandler;
import net.npg.abattle.client.dependent.ReturnControl;
import net.npg.abattle.client.dependent.UIDialogComponent;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.common.component.ComponentLookup;

@SuppressWarnings("all")
public class HelpScreen extends BasicScreen implements ReturnControl {
  private volatile boolean inHelpScreen = false;
  
  public HelpScreen() {
    super();
  }
  
  @Override
  public void create() {
    this.inHelpScreen = true;
    ComponentLookup _instance = ComponentLookup.getInstance();
    final AssetManager manager = _instance.<AssetManager>getComponent(AssetManager.class);
    final String htmlText = manager.loadTextfile("help.html");
    ComponentLookup _instance_1 = ComponentLookup.getInstance();
    UIDialogComponent _component = _instance_1.<UIDialogComponent>getComponent(UIDialogComponent.class);
    RequestHandler _requestHandler = _component.getRequestHandler();
    _requestHandler.showHTMLView(htmlText, this);
  }
  
  @Override
  public void render() {
    if ((!this.inHelpScreen)) {
      this.backButton();
    }
  }
  
  @Override
  public void backButton() {
    ScreenSwitcher _switcher = this.getSwitcher();
    _switcher.switchToScreen(Screens.Main);
  }
  
  @Override
  public Screens getType() {
    return Screens.Help;
  }
  
  @Override
  public void switchBack() {
    this.inHelpScreen = false;
  }
}
