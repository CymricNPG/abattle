/**
 *
 */
package net.npg.abattle.server.logic;

import net.npg.abattle.common.configuration.impl.NamedClass;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.server.model.ServerBoard;

/**
 * @author cymric
 * 
 */
public interface Logic extends Runnable, NamedClass {

	Logic getInstance(ServerBoard board, GameConfiguration configuration);

}
