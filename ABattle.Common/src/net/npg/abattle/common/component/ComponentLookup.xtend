
/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.component

import net.npg.abattle.common.CommonConstants
import net.npg.abattle.common.utils.MyHashMap
import net.npg.abattle.common.utils.MyMap
import net.npg.abattle.common.utils.Validate

import static net.npg.abattle.common.component.ComponentLookup.*
import net.npg.abattle.common.utils.StopException

/**
 * a hack to avoid spring ...
 *
 * @author spatzenegger
 *
 */
public final class ComponentLookup {

	/** The instance. */
	private static volatile ComponentLookup instance = null
	/** The component map. */
	private final MyMap<Class<? extends Component>, Component> componentMap

	private final MyMap<Class<? extends Component>, Exception> firstComponentRegistrations

	/**
	 * Creates the instance.
	 */
	public synchronized static def ComponentLookup createInstance() {
		Validate.isTrue(instance == null)
		instance = new ComponentLookup()
		Runtime.getRuntime().addShutdownHook(new Thread() {
			override void run() {
				ComponentLookup.shutdownInstance()
			}
		})

		CommonConstants.LOG.debug("ComponentLookup created.");
		return instance
	}

	/**
	 * Gets the single instance of ComponentLookup.
	 * TODO throw shutdown exception
	 * @return single instance of ComponentLookup
	 */
	public static def ComponentLookup getInstance() {
		if(instance == null) {
			throw new StopException();
		}
		return instance
	}

	/**
	 * Checks if is initialized.
	 *
	 * @return true, if is initialized
	 */
	public static def boolean isInitialized() {
		return instance != null
	}

	/**
	 * Shutdown instance.
	 */
	public static synchronized def void shutdownInstance() {
		if (instance == null) {
			return
		}
		CommonConstants.LOG.debug("ComponentLookup destroyed.");
		instance.componentMap.values.filter[!disposed].forEach[dispose]
		instance.componentMap.clear
		instance.firstComponentRegistrations.clear
		instance = null
	}

	/**
	 * Instantiates a new component lookup.
	 */
	private new() {
		super()
		this.componentMap = new MyHashMap<Class<? extends Component>, Component>()
		this. firstComponentRegistrations = new MyHashMap<Class<? extends Component>, Exception>()
	}

	/**
	 * Gets the implementation for a given interface.
	 *
	 * @param <T> the generic type
	 * @param componentInterface the component interface
	 * @return the component
	 */
	@SuppressWarnings("unchecked")
	public def  <T extends Component>  T getComponent( Class<T> componentInterface) {
		val component = componentMap.get(componentInterface);
		if (component == null) {
			throw new IllegalArgumentException("Unknown component:" + componentInterface.getSimpleName());
		}
		return component as T
	}

	/**
	 * has the component be registered already?
	 *
	 * @param componentInterface
	 * @return yes = true
	 */
	public def boolean  hasComponent( Class<? extends Component> componentInterface) {
		return componentMap.containsKey(componentInterface)
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
	public def <T extends Component> T registerComponent( T component,  Class<? extends Component> interfaceOfComponent) {
		Validate.notNull(component);
		Validate.notNull(interfaceOfComponent);
		CommonConstants.LOG.info("Register:" + interfaceOfComponent.getCanonicalName());
		if (componentMap.containsKey(interfaceOfComponent)) {
			CommonConstants.LOG.warn("Already Registered:" + interfaceOfComponent.getCanonicalName(), new RuntimeException("Stacktrace"));
			CommonConstants.LOG.warn("First appearance:", firstComponentRegistrations.get(interfaceOfComponent))
			return  componentMap.get(interfaceOfComponent) as T
		} else {
			componentMap.put(interfaceOfComponent, component);
			firstComponentRegistrations.put(interfaceOfComponent, new RuntimeException("Stacktrace"));
			return component
		}
	}

	/**
	 * Register implementation of an interface, if already registered, return the old implementation.
	 *
	 * @param <T> the generic type
	 * @param component the component
	 * @return the component or the old already registered interface implementaion
	 */
	 def <T extends Component> T registerComponent( T component) {
		Validate.notNull(component);
		return registerComponent(component, component.getInterface());
	}

	def synchronized void restart() {
		if (instance == null) {
			return
		}
		CommonConstants.LOG.debug("ComponentLookup restart.");
		val removes = instance.componentMap.keySet.filter[!(Reusable.isAssignableFrom(it))].clone
		for(remove:removes) {
			val component = instance.componentMap.remove(remove)
			if(!component.disposed) {
				component.dispose
			}
			instance.firstComponentRegistrations.remove(remove)
		}
	}
}
