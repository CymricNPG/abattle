/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.utils;

import java.util.concurrent.atomic.AtomicInteger;

import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.error.ErrorComponent;

/**
 * @author Cymric
 *
 */
public abstract class MyRunnable implements Runnable {

	private final LifecycleControl control;

	private boolean failed = false;

	private volatile boolean stop = false;
	private Throwable stoppedBy = null;
	private final String name;
	private static final AtomicInteger number = new AtomicInteger(0);

	private final ErrorComponent errorComponent;

	public MyRunnable(final String name) {
		this.control = LifecycleControl.getControl();
		this.name = name + ":" + number.addAndGet(1);
		errorComponent = ComponentLookup.getInstance().getComponent(ErrorComponent.class);
	}

	public abstract void execute() throws Exception;

	protected void lifeCycleWait() throws InterruptedException {
		if (stop) {
			throw new StopException(stoppedBy);
		}
		control.lifeCycle();
	}

	@Override
	final public void run() {
		setThreadName();
		try {
			execute();
		} catch (final StopException e) {
			CommonConstants.LOG.error(e.getMessage());
			failed = true;
		} catch (final Exception t) {
			CommonConstants.LOG.error(t.getMessage(), t);
			failed = true;
			errorComponent.addError(t);
			throw new RuntimeException(t.getMessage(), t);
		} finally {
			CommonConstants.LOG.info("Thread " + name + " finished!");

		}
	}

	private void setThreadName() {
		Thread.currentThread().setName(name);
		CommonConstants.LOG.info("Thread " + name + " started!");
	}

	public boolean isFailed() {
		return failed;
	}

	public boolean isStopped() {
		return stop;
	}

	public void stop() {
		stoppedBy = new Exception("Calling Stack.");
		stop = true;
	}
}
