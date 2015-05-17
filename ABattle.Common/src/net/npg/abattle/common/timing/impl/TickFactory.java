/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.timing.impl;

import java.util.concurrent.atomic.AtomicInteger;

import net.npg.abattle.common.timing.Tick;

/**
 * @author spatzenegger
 * 
 */
public class TickFactory {

	private static final AtomicInteger count = new AtomicInteger(0);

	public static final Tick createAndStartTick(final int ticksPerSecond) {
		final Tick tick = new TickImpl(ticksPerSecond);
		new Thread(tick, "Tick:" + ticksPerSecond + "/" + count.incrementAndGet()).start();
		return tick;
	}
}
