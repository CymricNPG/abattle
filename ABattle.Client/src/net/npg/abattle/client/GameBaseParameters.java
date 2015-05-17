package net.npg.abattle.client;

/**
 * The Interface GameBaseParameters.
 */
public interface GameBaseParameters {

	/**
	 * Gets the number players.
	 * 
	 * @return the number players
	 */
	int getHumanPlayers();

	int getAIPlayers();

	/**
	 * Gets the x size.
	 * 
	 * @return the x size
	 */
	int getXSize();

	/**
	 * Gets the y size.
	 * 
	 * @return the y size
	 */
	int getYSize();

	String getName();

}