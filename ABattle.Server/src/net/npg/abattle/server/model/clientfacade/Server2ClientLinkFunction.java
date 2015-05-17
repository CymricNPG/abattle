package net.npg.abattle.server.model.clientfacade;

import net.npg.abattle.common.model.Link;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.impl.LinkImpl;
import net.npg.abattle.server.model.ServerCell;

public class Server2ClientLinkFunction {

	private final ClientBoard clientBoard;

	public Server2ClientLinkFunction(final ClientBoard clientBoard) {
		this.clientBoard = clientBoard;
	}

	public Link<ClientCell> apply(final Link<ServerCell> link) {
		final ClientCell sourceCell = clientBoard.getCellAt(link.getSourceCell().getBoardCoordinate());
		final ClientCell destinationCell = clientBoard.getCellAt(link.getDestinationCell().getBoardCoordinate());
		return new LinkImpl<ClientCell>(link.getId(), sourceCell, destinationCell);
	}

}
