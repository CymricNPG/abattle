package net.npg.abattle.desktop.impl;

import com.badlogic.gdx.scenes.scene2d.Stage;
import net.npg.abattle.client.dependent.BackPressed;
import net.npg.abattle.client.dependent.MultiplayerScreen;
import net.npg.abattle.client.dependent.RequestHandler;
import net.npg.abattle.client.dependent.UIDialogComponent;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Widgets;
import net.npg.abattle.common.component.ComponentType;
import net.npg.abattle.common.i18n.I18N;
import net.npg.abattle.common.utils.impl.DisposeableImpl;
import net.npg.abattle.desktop.Main;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@ComponentType
@SuppressWarnings("all")
public class UIDialogComponentImpl extends DisposeableImpl implements UIDialogComponent {
  @Accessors(AccessorType.PUBLIC_GETTER)
  private RequestHandler requestHandler;
  
  public UIDialogComponentImpl(final Main requestHandler) {
    this.requestHandler = requestHandler;
  }
  
  public BackPressed getBackPressedHandler() {
    return ((BackPressed) this.requestHandler);
  }
  
  @Override
  public MultiplayerScreen getMultiplayerScreen() {
    return new MultiplayerScreen() {
      @Override
      public String getDefaultText() {
        return I18N.get("notyetimplemented");
      }
      
      @Override
      public void setSwitcher(final ScreenSwitcher switcher) {
      }
      
      @Override
      public void create(final Widgets widgets, final Stage stage, final BasicScreen screen) {
      }
    };
  }
  
  public Class<UIDialogComponent> getInterface() {
    return UIDialogComponent.class;
  }
  
  @Pure
  public RequestHandler getRequestHandler() {
    return this.requestHandler;
  }
}
