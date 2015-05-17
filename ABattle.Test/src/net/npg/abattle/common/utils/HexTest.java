/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.utils;

import static org.junit.Assert.*;
import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.hex.HexBase;

import org.junit.Test;

/**
 * @author spatzenegger
 * 
 */
public class HexTest {

	private Hex createTestHex() {
		final HexBase hexBase = new HexBase(10f);
		final IntPoint boardCoordinate = IntPoint.from(3, 4);
		final Hex hex = new Hex(boardCoordinate, hexBase);
		return hex;
	}

	/**
	 * Test method for {@link net.npg.abattle.common.hex.Hex#computeCorners()}.
	 */
	@Test
	public void testComputeCorners() {
		final Hex hex = createTestHex();
		final FloatPoint[] corners = hex.computeCorners();

		assertNotNull(corners);
		Validate.noNullElements(corners);
		assertEquals(6, corners.length);

		final float[] testX = new float[] { 50.0f, 60.0f, 65.0f, 60.0f, 50.0f, 45.0f };
		final float[] testY = new float[] { 77.94f, 77.94f, 86.60f, 95.26f, 95.26f, 86.60f };
		for (int i = 0; i < 6; i++) {
			assertEquals("X" + i, testX[i], corners[i].x, 0.01f);
			assertEquals("Y" + i, testY[i], corners[i].y, 0.01f);
		}

	}

	/**
	 * Test method for {@link net.npg.abattle.common.hex.Hex#getBoardCoordinate()}.
	 */
	@Test
	public void testGetBoardCoordinate() {
		final Hex hex = createTestHex();
		final IntPoint boardCoordinate = hex.getBoardCoordinate();
		assertEquals(3, boardCoordinate.x);
		assertEquals(4, boardCoordinate.y);
	}

	/**
	 * Test method for {@link net.npg.abattle.common.hex.Hex#getCenterX()}.
	 */
	@Test
	public void testGetCenterX() {
		final Hex hex = createTestHex();
		assertEquals(55f, hex.getCenterX(), 0.01f);
	}

	/**
	 * Test method for {@link net.npg.abattle.common.hex.Hex#getCenterY()}.
	 */
	@Test
	public void testGetCenterY() {
		final Hex hex = createTestHex();
		assertEquals(86.60f, hex.getCenterY(), 0.01f);
	}

	/**
	 * Test method for {@link net.npg.abattle.common.hex.Hex#getLeft()}.
	 */
	@Test
	public void testGetLeft() {
		final Hex hex = createTestHex();
		assertEquals(45f, hex.getLeft(), 0.01f);
	}

	/**
	 * Test method for {@link net.npg.abattle.common.hex.Hex#getScreenCoordinate()}.
	 */
	@Test
	public void testGetScreenCoordinate() {
		final Hex hex = createTestHex();
		final FloatPoint screenCoordinate = hex.getScreenCoordinate();
		assertEquals(45.0f, screenCoordinate.x, 0.01f);
		assertEquals(77.94f, screenCoordinate.y, 0.01f);
	}

	/**
	 * Test method for {@link net.npg.abattle.common.hex.Hex#getTop()}.
	 */
	@Test
	public void testGetTop() {
		final Hex hex = createTestHex();
		assertEquals(77.94f, hex.getTop(), 0.01f);
	}

	/**
	 * Test method for
	 * {@link net.npg.abattle.common.hex.Hex#Hex(net.npg.abattle.common.utils.Point, net.npg.abattle.common.hex.HexBase)}
	 * .
	 */
	@Test
	public void testHex() {
		final Hex hex = createTestHex();
		assertNotNull(hex);
	}

}
