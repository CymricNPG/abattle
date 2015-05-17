package net.npg.abattle.client.view.boardscene.debug

import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.configuration.impl.SelectableClassList

class DebugCells extends SelectableClassList<DebugCell> {
	public final static  SelectableClassList<DebugCell> debugList = new DebugCells()

	new() {
		super(
			[|ComponentLookup.getInstance.getComponent(ConfigurationComponent).graphicsConfiguration.debugCell],
			NoDebug.NAME
		)
		addSectableClass(new NoDebug())
		addSectableClass(new HeightDebug())
		addSectableClass(new StrengthDebug())
	}
}
