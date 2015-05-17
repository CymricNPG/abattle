/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server;

import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.error.ExceptionCode;

/**
 * The Enum ExceptionCode.
 * 
 * @author Cymric
 */
public final class ServerExceptionCode {

	public static final ExceptionCode CREATE_NETWORK_SERVER = new ExceptionCode(2, "Cannot create NetworkServer:");
	public static final ExceptionCode INCORRECT_NUMBER_OF_PLAYERS = new ExceptionCode(6, "Invalid number of players");
	public static final ExceptionCode INVALID_BOARD_SIZE = new ExceptionCode(5, "Invalid board size.");
	public static final ExceptionCode MAX_PLAYERS = new ExceptionCode(3, "Maximum number of players reached.");
	public static final ExceptionCode NO_PLAYERS = new ExceptionCode(0, "At least one player is expected.");
	public static final ExceptionCode TOO_MANY_PLAYERS = new ExceptionCode(1, "Too many Players, this game can handle only:" + CommonConstants.MAX_PLAYERS);
	public static final ExceptionCode UNKNOWN_GAME = new ExceptionCode(4, "Unknown game.");

}
