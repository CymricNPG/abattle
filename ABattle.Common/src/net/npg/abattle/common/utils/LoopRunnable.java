/**
 * 
 */
package net.npg.abattle.common.utils;

/**
 * @author Cymric
 * 
 */
public abstract class LoopRunnable extends MyRunnable {

	private volatile boolean stopped;

	// private volatile Thread thread;

	public LoopRunnable(final String name) {
		super(name);
		stopped = false;
		// thread = null;
	}

	@Override
	public void execute() throws Exception {
		// this.thread = Thread.currentThread();
		while (!stopped) {
			innerLoop();
		}
	}

	public abstract void innerLoop() throws Exception;

	/**
	 * stop the thread
	 */
	public void stop() {
		this.stopped = true;
		// try {
		// Thread.sleep(250);
		// } catch (InterruptedException e) {
		// //ignore
		// }
		// if(thread != null) {
		// thread.interrupt();
		// }
	}

}
