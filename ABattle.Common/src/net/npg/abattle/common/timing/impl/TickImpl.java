/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.timing.impl;

import net.npg.abattle.common.timing.Tick;
import net.npg.abattle.common.timing.TickObserver;
import net.npg.abattle.common.utils.MyConcurrentHashMap;
import net.npg.abattle.common.utils.MyMap;
import net.npg.abattle.common.utils.MyRunnable;

/**
 * @author spatzenegger
 * 
 */
public class TickImpl extends MyRunnable implements Tick {

	private static final long MILLISECONDS_PER_SECOND = 1000L;

	private final MyMap<TickObserver, TickObserver> observers;
	private volatile boolean running;

	private final int ticksPerSecond;

	public TickImpl(final int ticksPerSecond) {
		super("Tick");
		if (ticksPerSecond > 60 && ticksPerSecond < 1) {
			throw new IllegalArgumentException(ticksPerSecond + " not allowed.");
		}
		this.ticksPerSecond = ticksPerSecond;
		this.observers = new MyConcurrentHashMap<TickObserver, TickObserver>();
		running = false;
	}

	@Override
	public void addObserver(final TickObserver observer) {
		this.observers.put(observer, observer);
	}

	void nextTick() {
		for (final TickObserver observer : observers.keySet()) {
			observer.update();
		}
	}

	private long now() {
		return System.currentTimeMillis();
	}

	@Override
	public void removeObserver(final TickObserver observer) {
		this.observers.remove(observer);
	}

	@Override
	public void execute() throws InterruptedException {
		assert !running;
		running = true;
		long timeLastNextTick = now();
		while (running) {
			timeLastNextTick = waitForTick(timeLastNextTick);
			nextTick();
			lifeCycleWait();
		}
		running = false;
	}

	@Override
	public void stop() {
		this.running = false;
	}

	private long waitForTick(final long timeLastNextTick) throws InterruptedException {
		final long timeForUpdate = now() - timeLastNextTick;
		final long waitTime = MILLISECONDS_PER_SECOND / ticksPerSecond - timeForUpdate;
		if (waitTime > 0) {
			Thread.sleep(waitTime);
		}
		return now();
	}
}
