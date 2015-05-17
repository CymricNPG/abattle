/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.client.impl

import net.npg.abattle.common.model.client.ClientBoard
import net.npg.abattle.common.model.client.ClientCell
import net.npg.abattle.common.model.client.ClientLinks
import net.npg.abattle.common.model.client.ClientPlayer
import net.npg.abattle.common.model.impl.BoardImpl

import static net.npg.abattle.common.utils.Asserts.*

class ClientBoardImpl extends BoardImpl<ClientPlayer, ClientCell, ClientLinks> implements ClientBoard {

	final ClientLinks links;

	new(int xsize, int ysize) {
		super(xsize, ysize)
		links = new ClientLinksImpl
	}

	new(int id, int xsize, int ysize) {
		super(id, xsize, ysize)
		links = new ClientLinksImpl
	}

	override ClientLinks getLinks() {
		assertIt(links != null);
		return links;
	}
}
