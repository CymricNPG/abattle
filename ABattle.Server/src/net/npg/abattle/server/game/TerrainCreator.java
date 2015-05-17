/**
 *
 */
package net.npg.abattle.server.game;

import net.npg.abattle.common.configuration.impl.NamedClass;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.server.model.impl.ServerBoardImpl;

/**
 * @author cymric
 * 
 */
public interface TerrainCreator extends NamedClass {
	void createBoard(ServerBoardImpl board, IntPoint size, CheckModelElement checker);
}
