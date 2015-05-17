/**
 *
 */
package net.npg.abattle.common.configuration;

import net.npg.abattle.common.component.Component;
import net.npg.abattle.common.component.Reusable;

/**
 * @author Cymric
 *
 */
public interface ConfigurationComponent extends Component, Reusable {

	GraphicsConfigurationData getGraphicsConfiguration();

	GameConfigurationData getGameConfiguration();

	GameLoopConfigurationData getGameLoopConfiguration();

	NetworkConfigurationData getNetworkConfiguration();

	GlobalOptionsData getGlobalOptions();

	void save();

	void reset();
}
