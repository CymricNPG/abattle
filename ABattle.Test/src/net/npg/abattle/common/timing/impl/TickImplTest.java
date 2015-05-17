/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.timing.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.npg.abattle.common.timing.Tick;
import net.npg.abattle.common.timing.TickObserver;

import org.junit.Test;

/**
 * @author spatzenegger
 * 
 */
public class TickImplTest implements TickObserver {

	public class ThreadTickObserver implements TickObserver {
		volatile boolean StickReceived = false;
		volatile int SticksReceived = 0;

		@Override
		public void update() {
			this.StickReceived = true;
			this.SticksReceived++;
		}
	}

	private boolean tickReceived;

	/**
	 * Test method for
	 * {@link net.npg.abattle.common.timing.impl.TickImpl#addObserver(net.npg.abattle.common.timing.TickObserver)}.
	 */
	@Test
	public void testAddObserver() {
		final Tick tick = new TickImpl(10);
		tick.addObserver(this);
	}

	/**
	 * Test method for {@link net.npg.abattle.common.timing.impl.TickImpl#nextTick()}.
	 */
	@Test
	public void testNextTick() {
		tickReceived = false;
		final TickImpl tick = new TickImpl(10);
		tick.addObserver(this);
		tick.nextTick();
		assertTrue(tickReceived);
	}

	/**
	 * Test method for
	 * {@link net.npg.abattle.common.timing.impl.TickImpl#removeObserver(net.npg.abattle.common.timing.TickObserver)}.
	 */
	@Test
	public void testRemoveObserver() {
		tickReceived = false;
		final TickImpl tick = new TickImpl(10);
		tick.addObserver(this);
		tick.removeObserver(this);
		tick.nextTick();
		assertFalse(tickReceived);
	}

	@Test
	public void testTicksThread() throws Exception {

		final Tick tick = TickFactory.createAndStartTick(10);
		final ThreadTickObserver tickObserver = new ThreadTickObserver();
		tick.addObserver(tickObserver);
		Thread.sleep(1000);
		tick.stop();
		assertTrue(tickObserver.StickReceived);
		final int currentTicks = tickObserver.SticksReceived;
		assertTrue("Ticks:" + currentTicks, currentTicks >= 9);
		Thread.sleep(1100);
		assertEquals(currentTicks + 1, tickObserver.SticksReceived);
	}

	@Override
	public void update() {
		assertFalse(tickReceived);
		this.tickReceived = true;
	}
}
