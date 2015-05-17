package net.npg.abattle.server.game.impl.fog

import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.configuration.impl.SelectableClassList

class Fogs extends SelectableClassList<Fog> {

	final static public  SelectableClassList<Fog> fogList = new Fogs()

	new() {
		super(
		[|ComponentLookup.getInstance.getComponent(ConfigurationComponent).gameConfiguration.fog], SimpleFog.NAME)
		addSectableClass(new SimpleFog())
		addSectableClass(new NoFog())
	//	addSectableClass(new TerrainFog())
	}
}