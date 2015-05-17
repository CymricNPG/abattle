/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.common.hex.Hex;

/**
 * @author cymric
 * 
 */
public abstract class Shape implements VisitableSceneElement {

	public Shape() {
		super();
	}

	public abstract Hex getHex();

	public abstract boolean isDrawable();

}
