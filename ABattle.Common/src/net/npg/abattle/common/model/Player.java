/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model;

/**
 * @author cymric
 * 
 */
public interface Player extends ModelElement, Actor, VisitableModelElement {

	public Color getColor();

	@Override
	public String getName();

	public int getStrength();
}
