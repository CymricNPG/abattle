/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.component;

import com.google.common.base.Objects;
import java.util.Collection;
import java.util.Set;
import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.component.Component;
import net.npg.abattle.common.component.Reusable;
import net.npg.abattle.common.utils.MyHashMap;
import net.npg.abattle.common.utils.MyMap;
import net.npg.abattle.common.utils.StopException;
import net.npg.abattle.common.utils.Validate;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

/**
 * a hack to avoid spring ...
 * 
 * @author spatzenegger
 */
@SuppressWarnings("all")
public final class ComponentLookup {
  /**
   * The instance.
   */
  private static volatile ComponentLookup instance = null;
  
  /**
   * The component map.
   */
  private final MyMap<Class<? extends Component>, Component> componentMap;
  
  private final MyMap<Class<? extends Component>, Exception> firstComponentRegistrations;
  
  /**
   * Creates the instance.
   */
  public static synchronized ComponentLookup createInstance() {
    boolean _equals = Objects.equal(ComponentLookup.instance, null);
    Validate.isTrue(_equals);
    ComponentLookup _componentLookup = new ComponentLookup();
    ComponentLookup.instance = _componentLookup;
    Runtime _runtime = Runtime.getRuntime();
    _runtime.addShutdownHook(new Thread() {
      @Override
      public void run() {
        ComponentLookup.shutdownInstance();
      }
    });
    CommonConstants.LOG.debug("ComponentLookup created.");
    return ComponentLookup.instance;
  }
  
  /**
   * Gets the single instance of ComponentLookup.
   * TODO throw shutdown exception
   * @return single instance of ComponentLookup
   */
  public static ComponentLookup getInstance() {
    boolean _equals = Objects.equal(ComponentLookup.instance, null);
    if (_equals) {
      throw new StopException();
    }
    return ComponentLookup.instance;
  }
  
  /**
   * Checks if is initialized.
   * 
   * @return true, if is initialized
   */
  public static boolean isInitialized() {
    return (!Objects.equal(ComponentLookup.instance, null));
  }
  
  /**
   * Shutdown instance.
   */
  public static synchronized void shutdownInstance() {
    boolean _equals = Objects.equal(ComponentLookup.instance, null);
    if (_equals) {
      return;
    }
    CommonConstants.LOG.debug("ComponentLookup destroyed.");
    Collection<Component> _values = ComponentLookup.instance.componentMap.values();
    final Function1<Component, Boolean> _function = new Function1<Component, Boolean>() {
      @Override
      public Boolean apply(final Component it) {
        boolean _isDisposed = it.isDisposed();
        return Boolean.valueOf((!_isDisposed));
      }
    };
    Iterable<Component> _filter = IterableExtensions.<Component>filter(_values, _function);
    final Procedure1<Component> _function_1 = new Procedure1<Component>() {
      @Override
      public void apply(final Component it) {
        it.dispose();
      }
    };
    IterableExtensions.<Component>forEach(_filter, _function_1);
    ComponentLookup.instance.componentMap.clear();
    ComponentLookup.instance.firstComponentRegistrations.clear();
    ComponentLookup.instance = null;
  }
  
  /**
   * Instantiates a new component lookup.
   */
  private ComponentLookup() {
    super();
    MyHashMap<Class<? extends Component>, Component> _myHashMap = new MyHashMap<Class<? extends Component>, Component>();
    this.componentMap = _myHashMap;
    MyHashMap<Class<? extends Component>, Exception> _myHashMap_1 = new MyHashMap<Class<? extends Component>, Exception>();
    this.firstComponentRegistrations = _myHashMap_1;
  }
  
  /**
   * Gets the implementation for a given interface.
   * 
   * @param <T> the generic type
   * @param componentInterface the component interface
   * @return the component
   */
  @SuppressWarnings("unchecked")
  public <T extends Component> T getComponent(final Class<T> componentInterface) {
    final Component component = this.componentMap.get(componentInterface);
    boolean _equals = Objects.equal(component, null);
    if (_equals) {
      String _simpleName = componentInterface.getSimpleName();
      String _plus = ("Unknown component:" + _simpleName);
      throw new IllegalArgumentException(_plus);
    }
    return ((T) component);
  }
  
  /**
   * has the component be registered already?
   * 
   * @param componentInterface
   * @return yes = true
   */
  public boolean hasComponent(final Class<? extends Component> componentInterface) {
    return this.componentMap.containsKey(componentInterface);
  }
  
  /**
   * Register implementation of an interface, if already registered, return the old implementation.
   * 
   * @param <T> the generic type
   * @param interfaceOfComponent the interface the component implements
   * @param component the component
   * @return the component or the old already registered interface implementaion
   */
  @SuppressWarnings("unchecked")
  public <T extends Component> T registerComponent(final T component, final Class<? extends Component> interfaceOfComponent) {
    Validate.notNull(component);
    Validate.notNull(interfaceOfComponent);
    String _canonicalName = interfaceOfComponent.getCanonicalName();
    String _plus = ("Register:" + _canonicalName);
    CommonConstants.LOG.info(_plus);
    boolean _containsKey = this.componentMap.containsKey(interfaceOfComponent);
    if (_containsKey) {
      String _canonicalName_1 = interfaceOfComponent.getCanonicalName();
      String _plus_1 = ("Already Registered:" + _canonicalName_1);
      RuntimeException _runtimeException = new RuntimeException("Stacktrace");
      CommonConstants.LOG.warn(_plus_1, _runtimeException);
      Exception _get = this.firstComponentRegistrations.get(interfaceOfComponent);
      CommonConstants.LOG.warn("First appearance:", _get);
      Component _get_1 = this.componentMap.get(interfaceOfComponent);
      return ((T) _get_1);
    } else {
      this.componentMap.put(interfaceOfComponent, component);
      RuntimeException _runtimeException_1 = new RuntimeException("Stacktrace");
      this.firstComponentRegistrations.put(interfaceOfComponent, _runtimeException_1);
      return component;
    }
  }
  
  /**
   * Register implementation of an interface, if already registered, return the old implementation.
   * 
   * @param <T> the generic type
   * @param component the component
   * @return the component or the old already registered interface implementaion
   */
  public <T extends Component> T registerComponent(final T component) {
    Validate.notNull(component);
    Class<? extends Component> _interface = component.getInterface();
    return this.<T>registerComponent(component, _interface);
  }
  
  public synchronized void restart() {
    boolean _equals = Objects.equal(ComponentLookup.instance, null);
    if (_equals) {
      return;
    }
    CommonConstants.LOG.debug("ComponentLookup restart.");
    Set<Class<? extends Component>> _keySet = ComponentLookup.instance.componentMap.keySet();
    final Function1<Class<? extends Component>, Boolean> _function = new Function1<Class<? extends Component>, Boolean>() {
      @Override
      public Boolean apply(final Class<? extends Component> it) {
        boolean _isAssignableFrom = Reusable.class.isAssignableFrom(it);
        return Boolean.valueOf((!_isAssignableFrom));
      }
    };
    Iterable<Class<? extends Component>> _filter = IterableExtensions.<Class<? extends Component>>filter(_keySet, _function);
    final Class<? extends Component>[] removes = ((Class<? extends Component>[])Conversions.unwrapArray(_filter, Class.class)).clone();
    for (final Class<? extends Component> remove : removes) {
      {
        final Component component = ComponentLookup.instance.componentMap.remove(remove);
        boolean _isDisposed = component.isDisposed();
        boolean _not = (!_isDisposed);
        if (_not) {
          component.dispose();
        }
        ComponentLookup.instance.firstComponentRegistrations.remove(remove);
      }
    }
  }
}
