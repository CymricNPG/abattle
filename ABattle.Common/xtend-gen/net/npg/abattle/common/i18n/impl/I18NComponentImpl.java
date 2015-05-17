package net.npg.abattle.common.i18n.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import java.util.MissingResourceException;
import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.component.ComponentType;
import net.npg.abattle.common.i18n.I18NComponent;
import net.npg.abattle.common.utils.impl.DisposeableImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;

@ComponentType
@SuppressWarnings("all")
public class I18NComponentImpl extends DisposeableImpl implements I18NComponent {
  private I18NBundle myBundle;
  
  public I18NComponentImpl() {
    final FileHandle baseFileHandle = Gdx.files.internal("messages");
    I18NBundle _createBundle = I18NBundle.createBundle(baseFileHandle);
    this.myBundle = _createBundle;
  }
  
  @Override
  public String format(final String key, final String... params) {
    return this.myBundle.format(key, ((Object[]) params));
  }
  
  @Override
  public String get(final String key) {
    String _xtrycatchfinallyexpression = null;
    try {
      _xtrycatchfinallyexpression = this.myBundle.get(key);
    } catch (final Throwable _t) {
      if (_t instanceof MissingResourceException) {
        final MissingResourceException e = (MissingResourceException)_t;
        CommonConstants.LOG.error(("Cannot find key:" + key));
        return key;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }
  
  public Class<I18NComponent> getInterface() {
    return I18NComponent.class;
  }
}
