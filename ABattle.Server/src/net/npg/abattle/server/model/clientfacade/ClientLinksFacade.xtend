package net.npg.abattle.server.model.clientfacade

import java.util.ArrayList
import java.util.List
import java.util.Set
import net.npg.abattle.common.error.NotAvailableException
import net.npg.abattle.common.model.Link
import net.npg.abattle.common.model.client.ClientBoard
import net.npg.abattle.common.model.client.ClientCell
import net.npg.abattle.common.model.client.ClientLinks
import net.npg.abattle.common.model.impl.IDElementImpl
import net.npg.abattle.server.model.ServerBoard
import net.npg.abattle.server.model.ServerCell
import net.npg.abattle.server.model.ServerPlayer

class ClientLinksFacade extends IDElementImpl implements ClientLinks {

	private Server2ClientLinkFunction server2ClientLinkFunction;

	ServerPlayer player

	ServerBoard board

	new(ServerBoard board, ServerPlayer player, ClientBoard clientBoard) {
		this.board = board
		this.player = player
		server2ClientLinkFunction = new Server2ClientLinkFunction(clientBoard);
	}

	override addLink(Link<ClientCell> link) {
		throw new NotAvailableException
	}

	override doesLinkExists(ClientCell startCell, ClientCell endCell) {
		throw new NotAvailableException
	}

	override hasLink(int linkId) {
		throw new NotAvailableException
	}

	override preserverLinks(Set<Integer> linkIds) {
		throw new NotAvailableException
	}

	override getLinks() {
		val List<Link<ClientCell>> links = new ArrayList<Link<ClientCell>>()
		for (Link<ServerCell> link : board.links.getLinks(player)) {
			links.add(server2ClientLinkFunction.apply(link));
		}
		return links;
	}

}
