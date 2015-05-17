package net.npg.abattle.server.logic

import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.configuration.impl.SelectableClassList

class Logics extends SelectableClassList<Logic> {
	public final static  SelectableClassList<Logic> logicMap = new Logics()

	new() {
		super(
			[|ComponentLookup.getInstance.getComponent(ConfigurationComponent).gameConfiguration.logic],
			XBattleDistribute.NAME
		)
		addSectableClass(new SimpleDistributeArmies())
		addSectableClass(new XBattleDistribute())
	}

}
