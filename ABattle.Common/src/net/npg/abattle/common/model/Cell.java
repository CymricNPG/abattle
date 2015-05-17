/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model;

import net.npg.abattle.common.utils.IntPoint;

import com.google.common.base.Optional;

/**
 * The Interface Cell.
 * 
 * @author cymric
 */
public interface Cell extends ModelElement, VisitableModelElement {

	/**
	 * Gets the board coordinate.
	 * 
	 * @return the board coordinate
	 */
	IntPoint getBoardCoordinate();

	/**
	 * Gets the cell type.
	 * 
	 * @return the cell type
	 */
	CellTypes getCellType();

	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	int getHeight();

	/**
	 * Gets the owner.
	 * 
	 * @param <PLAYERTYPE> the generic type
	 * @return the owner or null if no owner
	 */
	<PLAYERTYPE extends Player> Optional<PLAYERTYPE> getOwner();

	<PLAYERTYPE extends Player> boolean isOwner(PLAYERTYPE player);

	/**
	 * Gets the strength.
	 * 
	 * @return the strength
	 */
	int getStrength();

	/**
	 * Checks for battle.
	 * 
	 * @return true, if successful
	 */
	boolean hasBattle();

	/**
	 * Checks for structure.
	 * 
	 * @return true, if successful
	 */
	boolean hasStructure();

	/**
	 * Checks if is adjacent to.
	 * 
	 * @param <CELLTYPE> the generic type
	 * @param testCell the test cell
	 * @return true, if is adjacent to
	 */
	<CELLTYPE extends Cell> boolean isAdjacentTo(CELLTYPE testCell);

	/**
	 * Sets the battle.
	 * 
	 * @param hasBattle the new battle
	 */
	void setBattle(boolean hasBattle);

	/**
	 * Sets the owner.
	 * 
	 * @param <PLAYERTYPE> the generic type
	 * @param player the new owner
	 */
	<PLAYERTYPE extends Player> void setOwner(PLAYERTYPE player);

	/**
	 * Sets the strength.
	 * 
	 * @param newStrength the new strength
	 */
	void setStrength(int newStrength);

}
