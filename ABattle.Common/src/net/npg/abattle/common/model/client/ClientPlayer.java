/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.client;

import net.npg.abattle.common.model.Color;
import net.npg.abattle.common.model.Player;

/**
 * @author spatzenegger
 * 
 */
public interface ClientPlayer extends Player {

	public void setStrength(int strength);

	public void setColor(Color color);
}
