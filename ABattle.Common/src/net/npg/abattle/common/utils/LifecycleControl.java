/**
 *
 */
package net.npg.abattle.common.utils;

import java.util.HashSet;
import java.util.Set;

import net.npg.abattle.common.CommonConstants;

/**
 * controls the threads in the application, can pause, resume and stop threads, all threads implementinmg MyRunnable
 * must call lifeCycle() (better would be strategy pattern: one shoot execute, endless execute ..)
 * 
 * @author Cymric TODO connect with Disposeable and ComponentLookup
 */
public class LifecycleControl {
	private static LifecycleControl control = new LifecycleControl();

	private final Set<StopListener> stopListeners;
	private volatile boolean stopped = false;
	private volatile boolean paused = false;
	private volatile boolean suppressPaused = false;

	private LifecycleControl() {
		stopListeners = new HashSet<StopListener>();
	}

	public static LifecycleControl getControl() {
		return control;
	}

	public void setSuppressPaused(final boolean suppressPaused) {
		this.suppressPaused = suppressPaused;
	}

	public synchronized void pauseApplication() {
		if (!suppressPaused) {
			paused = true;
		}
	}

	public synchronized void addStopListener(final StopListener listener) {
		stopListeners.add(listener);
	}

	public synchronized void continueApplication() {
		paused = false;
		notifyAll();
	}

	public synchronized void stopApplication() {
		stopped = true;
		continueApplication();
	}

	public synchronized void lifeCycle() throws InterruptedException {
		while (paused) {
			wait();
		}
		testStop();
	}

	private void testStop() throws InterruptedException {
		if (!stopped) {
			return;
		}
		for (final StopListener listener : stopListeners) {
			try {
				listener.applicationStopped();
			} catch (final Exception e) {
				CommonConstants.LOG.error(e.getMessage(), e);
			}
		}
		throw new StopException();

	}

	public boolean isPaused() {
		return paused;
	}

	public static void recycle() {
		control = new LifecycleControl();
	}
}
