/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author spatzenegger
 * 
 */
public final class IDGenerator {
	private static AtomicInteger id = new AtomicInteger(0);

	public static int generateId() {
		return id.incrementAndGet();
	}
}
