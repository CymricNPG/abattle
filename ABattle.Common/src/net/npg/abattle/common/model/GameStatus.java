/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model;

/**
 * @author spatzenegger
 * 
 */
public enum GameStatus {

	PENDING, PENDING_CLIENT, RUNNING, FINISHED;

	public static boolean isPending(final GameStatus status) {
		return PENDING.equals(status) || PENDING_CLIENT.equals(status);
	}

	public boolean isRunning() {
		return RUNNING.equals(this);
	}
}
