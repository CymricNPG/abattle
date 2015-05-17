/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.model.impl

import net.npg.abattle.common.model.impl.BoardImpl
import net.npg.abattle.server.model.ServerBoard
import net.npg.abattle.server.model.ServerCell
import net.npg.abattle.server.model.ServerLinks
import net.npg.abattle.server.model.ServerPlayer

/**
 * @author cymric
 * 
 */
class ServerBoardImpl extends BoardImpl<ServerPlayer, ServerCell, ServerLinks> implements ServerBoard {

	ServerLinksImpl links
	
	new(int xsize, int ysize) {
		super(xsize, ysize);
		links = new ServerLinksImpl()
	}

	override getLinks() {
		return links
	}

}
