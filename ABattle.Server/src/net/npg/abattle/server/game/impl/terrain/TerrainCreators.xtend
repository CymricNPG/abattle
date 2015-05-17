package net.npg.abattle.server.game.impl.terrain

import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.configuration.impl.SelectableClassList
import net.npg.abattle.server.game.TerrainCreator

class TerrainCreators extends SelectableClassList<TerrainCreator> {
	public static  SelectableClassList<TerrainCreator>terrainMap = new TerrainCreators()

	new() {
		super(
			[|ComponentLookup.getInstance.getComponent(ConfigurationComponent).gameConfiguration.terrainCreator],
			XBattleTerrain.NAME)
					addSectableClass(new XBattleTerrain())
		addSectableClass(new RandomTerrain())
		addSectableClass(new PeakTerrain())
	}

}