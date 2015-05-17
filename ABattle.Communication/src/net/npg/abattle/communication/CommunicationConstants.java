package net.npg.abattle.communication;

import net.npg.abattle.common.error.ExceptionCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CommunicationConstants {

	private static final String LOG_NAME = "abattle.comm";

	public final static boolean DEBUG_ENABLED = false;

	public final static Logger LOG = LoggerFactory.getLogger(CommunicationConstants.LOG_NAME);

	public static final ExceptionCode NETWORK_SERVER_UNREACHABLE = new ExceptionCode(101, "Server unreachable.");

	public static final ExceptionCode GAME_CREATION_FAILED = new ExceptionCode(102, "Cannot create game.");

	public static final ExceptionCode GAME_JOINED_FAILED = new ExceptionCode(103, "Cannot joind game.");
}
