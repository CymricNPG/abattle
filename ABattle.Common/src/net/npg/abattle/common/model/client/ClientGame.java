/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.client;

import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.Game;

/**
 * @author spatzenegger
 * 
 */
public interface ClientGame extends Game<ClientPlayer, ClientCell, ClientLinks> {

	ClientPlayer getLocalPlayer();

	void initGame() throws BaseException;
}
