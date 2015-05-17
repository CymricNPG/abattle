/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.model;

import net.npg.abattle.common.model.Color;
import net.npg.abattle.common.model.Player;

/**
 * @author spatzenegger
 * 
 */
public interface ServerPlayer extends Player {

	boolean isComputer();

	void setColor(Color color);
	
	void setStrength(int strength);
}
