/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model;

import net.npg.abattle.common.error.ExceptionCode;

/**
 * @author Cymric
 * 
 */
public final class ModelExceptionCode {

	public static final ExceptionCode GAME_ALREADY_RUNNING = new ExceptionCode(1002, "Game is already running.");
	public static final ExceptionCode PLAYER_ALREADY_ADDED = new ExceptionCode(1001, "Player already added to game.");
	public static final ExceptionCode CLIENT_NOT_LOOGEDIN = new ExceptionCode(1003, "Client was not logged in!");
	public static final ExceptionCode LOGIN_TIMEOUT = new ExceptionCode(1004, "Client Login timed out!");
	public static final ExceptionCode LOGIN_FAILED = new ExceptionCode(1005, "Client Login failed!");
}
