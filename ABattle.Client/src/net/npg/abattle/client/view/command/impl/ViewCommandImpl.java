/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.command.impl;

import java.util.concurrent.atomic.AtomicInteger;

import net.npg.abattle.client.ClientException;
import net.npg.abattle.client.view.command.ViewCommand;

/**
 * @author spatzenegger
 * 
 */
public abstract class ViewCommandImpl implements ViewCommand, Runnable {

	private final static AtomicInteger count = new AtomicInteger(0);

	protected ViewCommandImpl() {
	}

	@Override
	final public void execute() {
		new Thread(this, "Command:" + this.getClass().getSimpleName() + "/" + count.incrementAndGet()).start();
	}

	protected abstract void executeInternal() throws ClientException;

	@Override
	public void run() {
		try {
			this.executeInternal();
		} catch (final ClientException e) {
			// TODO error handling
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
