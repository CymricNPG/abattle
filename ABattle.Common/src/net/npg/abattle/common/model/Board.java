/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model;

import net.npg.abattle.common.hex.Directions;
import net.npg.abattle.common.utils.IntPoint;

/**
 * @author cymric
 * 
 */
public interface Board<PLAYERTYPE extends Player, CELLTYPE extends Cell, LINKTYPE extends Links<CELLTYPE>> extends ModelElement, VisitableModelElement {

	/**
	 * 
	 * @param cell
	 * @param direction
	 * @return null if cell is outside the board!
	 */
	public CELLTYPE getAdjacentCell(CELLTYPE cell, Directions direction);

	/**
	 * Gets the cell at.
	 * 
	 * @param x the x
	 * @param y the y
	 * @return the cell at
	 * @Deprecated
	 */
	public CELLTYPE getCellAt(int x, int y);

	/**
	 * Gets the cell at a specified board coordinate.
	 * 
	 * @param boardCoordinate the board coordinate
	 * @return the cell at
	 */
	public CELLTYPE getCellAt(IntPoint boardCoordinate);

	public LINKTYPE getLinks();

	public int getXSize();

	public int getYSize();

	public boolean isInside(IntPoint coordinate);

	public boolean isCellInitialized(int x, int y);

	void setCellAt(CELLTYPE cell);

}
