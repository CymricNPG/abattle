package net.npg.abattle.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ServerConstants {

	private static final String LOG_NAME = "abattle.server";
	public static Logger LOG = LoggerFactory.getLogger(LOG_NAME);

	public static final int MAX_PLAYERS = 8;
	public static final int MAX_XSIZE = 1024;
	public static final int MAX_YSIZE = 1024;
	public static final int MIN_XSIZE = 7;
	public static final int MIN_YSIZE = 7;
}
