package net.npg.abattle.android;

import net.npg.abattle.android.MultiplayerScreenImpl;
import net.npg.abattle.android.RequestHandlerImpl;
import net.npg.abattle.client.dependent.BackPressed;
import net.npg.abattle.client.dependent.MultiplayerScreen;
import net.npg.abattle.client.dependent.RequestHandler;
import net.npg.abattle.client.dependent.UIDialogComponent;
import net.npg.abattle.common.component.ComponentType;
import net.npg.abattle.common.utils.impl.DisposeableImpl;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@ComponentType
@SuppressWarnings("all")
public class UIDialogComponentImpl extends DisposeableImpl implements UIDialogComponent {
  @Accessors(AccessorType.PUBLIC_GETTER)
  private RequestHandler requestHandler;
  
  public UIDialogComponentImpl(final RequestHandlerImpl requestHandler) {
    this.requestHandler = requestHandler;
  }
  
  public BackPressed getBackPressedHandler() {
    return ((BackPressed) this.requestHandler);
  }
  
  @Override
  public MultiplayerScreen getMultiplayerScreen() {
    return new MultiplayerScreenImpl();
  }
  
  public Class<UIDialogComponent> getInterface() {
    return UIDialogComponent.class;
  }
  
  @Pure
  public RequestHandler getRequestHandler() {
    return this.requestHandler;
  }
}
