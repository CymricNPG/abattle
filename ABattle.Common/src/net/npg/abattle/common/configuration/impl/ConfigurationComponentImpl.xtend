package net.npg.abattle.common.configuration.impl

import java.util.Map
import java.util.Set
import net.npg.abattle.common.component.ComponentType
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.configuration.GameConfigurationData
import net.npg.abattle.common.configuration.GameLoopConfigurationData
import net.npg.abattle.common.configuration.GlobalOptionsData
import net.npg.abattle.common.configuration.GraphicsConfigurationData
import net.npg.abattle.common.configuration.NetworkConfigurationData
import net.npg.abattle.common.configuration.SaveableData
import net.npg.abattle.common.utils.impl.DisposeableImpl
import org.eclipse.xtend.lib.annotations.AccessorType
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.xtend.lib.annotations.AccessorType.*
import com.badlogic.gdx.Gdx
import static net.npg.abattle.common.utils.Controls.*
import net.npg.abattle.common.CommonConstants

@ComponentType
public class ConfigurationComponentImpl extends DisposeableImpl implements ConfigurationComponent {

	@Accessors GraphicsConfigurationData graphicsConfiguration

	@Accessors GameConfigurationData gameConfiguration

	@Accessors GameLoopConfigurationData gameLoopConfiguration

	@Accessors NetworkConfigurationData networkConfiguration

	@Accessors GlobalOptionsData globalOptions

	@Accessors(AccessorType.PUBLIC_GETTER) Set<SaveableData> configurations;

	new() {
		configurations = newHashSet
		val properties = loadProperties
		graphicsConfiguration = addConfiguration(new GraphicsConfigurationData(properties))
		gameConfiguration = addConfiguration(new GameConfigurationData(properties))
		gameLoopConfiguration = addConfiguration(new GameLoopConfigurationData(properties))
		networkConfiguration = addConfiguration(new NetworkConfigurationData(properties))
		globalOptions = addConfiguration(new GlobalOptionsData(properties))
		fixConfiguration
	}

	def fixConfiguration() {
		returnif(CommonConstants.VERSION.equals(globalOptions.version))
		reset
		globalOptions.version = CommonConstants.VERSION
		save
	}

	private def <T extends SaveableData> T addConfiguration(T configuration) {
		configurations.add(configuration)
		configuration
	}

	override save() {
		returnifNot(configurations.exists[it.isDirty])
		val Map<String, Object> keyValues = newHashMap
		for (configuration : configurations) {
			configuration.save(keyValues)
		}
		val properties = loadProperties
		for (keyValue : keyValues.entrySet) {
			val value = keyValue.value
			if (value instanceof Integer) {
				properties.putInteger(keyValue.key, value)
			} else if (value instanceof Long) {
				properties.putLong(keyValue.key, value)
			} else if (value instanceof String) {
				properties.putString(keyValue.key, value)
			} else if (value instanceof Boolean) {
				properties.putBoolean(keyValue.key, value)
			}
		}
		properties.flush
	}

	def loadProperties() {
		Gdx.app.getPreferences(ConfigurationComponent.canonicalName)
	}

	override reset() {
		CommonConstants.LOG.info("Reset Configuration")
		configurations.forEach[it.reset]
	}

}
