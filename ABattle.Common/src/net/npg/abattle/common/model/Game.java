/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model;

import java.util.Collection;

import net.npg.abattle.common.error.BaseException;

/**
 * The Interface Game.
 *
 * @param <PLAYERTYPE> the generic type
 * @param <CELLTYPE> the generic type
 * @author cymric
 */
public interface Game<PLAYERTYPE extends Player, CELLTYPE extends Cell, LINKTYPE extends Links<CELLTYPE>> extends ModelElement, VisitableModelElement {

	/**
	 * Gets the board.
	 *
	 * @return the board
	 */
	Board<PLAYERTYPE, CELLTYPE, LINKTYPE> getBoard();

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	Collection<PLAYERTYPE> getPlayers();

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	GameStatus getStatus();

	void stopGame();

	/**
	 * get the game configuration
	 *
	 * @return
	 */
	GameConfiguration getGameConfiguration();

	void addPlayer(PLAYERTYPE player) throws BaseException;

	/**
	 *
	 * @param player
	 * @return false if cannot remove
	 */
	boolean removePlayer(PLAYERTYPE player);
}
