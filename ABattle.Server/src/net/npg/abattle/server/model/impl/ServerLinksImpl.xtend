package net.npg.abattle.server.model.impl

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import java.util.Collection
import net.npg.abattle.common.model.impl.IDElementImpl
import net.npg.abattle.server.model.ServerCell
import net.npg.abattle.server.model.ServerLink
import net.npg.abattle.server.model.ServerLinks
import net.npg.abattle.server.model.ServerPlayer
import java.util.List

/**
 *
 */
class ServerLinksImpl extends IDElementImpl implements ServerLinks {

	private final Multimap<ServerCell, ServerLink> outgoingCellLinks

	new() {
		super()
		outgoingCellLinks = HashMultimap.create(1024, 16)
	}

	synchronized override public Collection<ServerLink> getLinks() {
		outgoingCellLinks.values.toList
	}

	synchronized override public Collection<ServerLink> getLinks(ServerPlayer player) {
		outgoingCellLinks.values.filter[it.player == player].toList
	}

	synchronized override public List<ServerCell> getOutgoingLinks(ServerPlayer player, ServerCell cell) {
		outgoingCellLinks.get(cell).filter[it.player == player].map[it.destinationCell].toList
	}

	synchronized override public void toggleOutgoingLink(ServerCell startCell, ServerCell endCell, ServerPlayer player) {
		val oldLink = getLink(startCell, endCell, player)
		if (oldLink != null) {
			outgoingCellLinks.remove(startCell, oldLink)
		} else {
			outgoingCellLinks.put(startCell, new ServerLinkImpl(startCell, endCell, player))
		}
	}

	synchronized override public boolean hasLink(ServerCell startCell, ServerCell endCell, ServerPlayer player) {
		getLink(startCell, endCell, player) != null
	}

	private def ServerLink getLink(ServerCell startCell, ServerCell endCell, ServerPlayer player) {
		outgoingCellLinks.get(startCell).findFirst[it.player == player && it.destinationCell == endCell]
	}

}
