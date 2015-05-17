package net.npg.abattle.client.view.selection.impl

import net.npg.abattle.client.view.selection.SelectionComponent
import net.npg.abattle.client.view.selection.cone.ConeSelectionModelImpl
import net.npg.abattle.client.view.selection.twocells.SelectionModelImpl
import net.npg.abattle.common.component.Component
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.hex.HexBase
import net.npg.abattle.common.model.client.ClientBoard
import net.npg.abattle.common.model.client.ClientPlayer
import net.npg.abattle.common.utils.impl.DisposeableImpl

class SelectionComponentImpl extends DisposeableImpl implements Component, SelectionComponent {

	override getInterface() {
		SelectionComponent
	}

	override createSelectionModel(ClientBoard board, HexBase hexBase, ClientPlayer localPlayer) {
		val config = ComponentLookup.instance.getComponent(ConfigurationComponent).globalOptions.coneSelection
		if(config) {
			new ConeSelectionModelImpl(board, hexBase, localPlayer)
		} else {
			new SelectionModelImpl(board, hexBase, localPlayer)
		}
	}
}
