package net.npg.abattle.server.model.impl

import net.npg.abattle.common.model.impl.LinkImpl
import net.npg.abattle.server.model.ServerLink
import net.npg.abattle.server.model.ServerCell
import net.npg.abattle.server.model.ServerPlayer
import org.eclipse.xtend.lib.annotations.Accessors

class ServerLinkImpl extends LinkImpl<ServerCell> implements ServerLink {

	@Accessors ServerPlayer Player

	new(ServerCell sourceCell, ServerCell destinationCell, ServerPlayer player) {
		super( sourceCell, destinationCell)
		this.player = player
	}

}