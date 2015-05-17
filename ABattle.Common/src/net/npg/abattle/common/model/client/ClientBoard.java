/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.client;

import net.npg.abattle.common.model.Board;

/**
 * @author spatzenegger
 * 
 */
public interface ClientBoard extends Board<ClientPlayer, ClientCell, ClientLinks> {
	public ClientCell getCell(int cellId);

}
