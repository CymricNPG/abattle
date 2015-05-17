/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.utils;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author spatzenegger
 * 
 */
public class PointTest {

	/**
	 * Test method for {@link net.npg.abattle.common.utils.Point#equals(net.npg.abattle.common.utils.Point)}.
	 */
	@Test
	public void testEqualsPointOfInteger() {
		final IntPoint point1 = IntPoint.from(42, 666);
		final IntPoint point2 = IntPoint.from(42, 666);
		final IntPoint point3 = IntPoint.from(44, 666);
		final IntPoint point4 = IntPoint.from(42, 665);
		assertNotSame(point1, point2);
		assertEquals(point1, point2);
		assertFalse(point1.equals("huhu"));
		assertFalse(point1.equals(point3));
		assertFalse(point1.equals(point4));
		assertFalse(point1.equals(null));
	}

	/**
	 * Test method for {@link net.npg.abattle.common.utils.Point#Point(java.lang.Number, java.lang.Number)}.
	 */
	@Test
	public void testPoint() {
		final IntPoint point = IntPoint.from(42, 666);
		assertEquals(42, point.x);
		assertEquals(666, point.y);
	}

	/**
	 * Test method for {@link net.npg.abattle.common.utils.Point#toString()}.
	 */
	@Test
	public void testToString() {
		final IntPoint point = IntPoint.from(42, 666);
		assertEquals("42/666", point.toString());
	}

}
