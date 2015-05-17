package net.npg.abattle.common.utils;

import static net.npg.abattle.common.hex.Directions.N;
import static net.npg.abattle.common.hex.Directions.NE;
import static net.npg.abattle.common.hex.Directions.NW;
import static net.npg.abattle.common.hex.Directions.S;
import static net.npg.abattle.common.hex.Directions.SE;
import static net.npg.abattle.common.hex.Directions.SW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.npg.abattle.common.hex.Directions;
import net.npg.abattle.common.hex.HexBase;

import org.junit.Test;

public class HexBaseTest {

	private void assertCoordinates(final HexBase hexBase, final int x, final int y, final double x2, final double y2) {
		final FloatPoint screen = new FloatPoint((float) x2, (float) y2);
		final IntPoint board = hexBase.getCellByPoint(screen);
		assertEquals(x, board.x);
		assertEquals(y, board.y);
	}

	@Test
	public void testDirections() {
		final HexBase hexBase = new HexBase(2f);
		assertAngle(0, hexBase, 3, 3, 3, 3, NE);
		assertAngle(135, hexBase, 3, 3, 1, 1, NW);
		assertAngle(116, hexBase, 3, 3, 2, 1, N);
		assertAngle(90, hexBase, 3, 3, 3, 1, N);
		assertAngle(63, hexBase, 3, 3, 4, 1, N);
		assertAngle(45, hexBase, 3, 3, 5, 1, NE);
		assertAngle(26, hexBase, 3, 3, 5, 2, NE);
		assertAngle(0, hexBase, 3, 3, 5, 3, NE);
		assertAngle(360 - 26, hexBase, 3, 3, 5, 4, SE);
		assertAngle(360 - 45, hexBase, 3, 3, 5, 5, SE);
		assertAngle(360 - 63, hexBase, 3, 3, 4, 5, S);
		assertAngle(360 - 90, hexBase, 3, 3, 3, 5, S);
		assertAngle(360 - 116, hexBase, 3, 3, 2, 5, S);
		assertAngle(360 - 135, hexBase, 3, 3, 1, 5, SW);
		assertAngle(360 - 153, hexBase, 3, 3, 1, 4, SW);
		assertAngle(360 - 180, hexBase, 3, 3, 1, 3, SW);
		assertAngle(153, hexBase, 3, 3, 1, 2, NW);
	}

	private void assertAngle(final int expected, final HexBase hexBase, final int startx, final int starty, final int endx, final int endy,
			final Directions direction) {
		assertEquals(expected, hexBase.getAngle(new FloatPoint(startx, starty), new FloatPoint(endx, endy)), 1.0);
		assertEquals(direction, hexBase.getDirections(new FloatPoint(startx, starty), new FloatPoint(endx, endy)));
	}

	@Test
	public void testGetCellByPoint() {
		final HexBase hexBase = new HexBase(1f);
		assertNotNull(hexBase);

		assertCoordinates(hexBase, 6, 2, 10.24375, 3.631894);
		assertCoordinates(hexBase, 7, 1, 10.896875, 3.4532766);
		assertCoordinates(hexBase, 7, 2, 11.275, 4.822679);
		assertCoordinates(hexBase, 7, 4, 11.20625, 7.7996416);
		assertCoordinates(hexBase, 5, 4, 9.246875, 8.752269);
		assertCoordinates(hexBase, 4, 5, 7.253125, 9.407201);
		assertCoordinates(hexBase, 3, 4, 5.671875, 9.169044);
		assertCoordinates(hexBase, 2, 5, 3.8843746, 10.181211);
		assertCoordinates(hexBase, 2, 5, 3.50625, 8.69273);
		assertCoordinates(hexBase, 2, 4, 4.19375, 7.2042494);
		assertCoordinates(hexBase, 4, 3, 6.428125, 6.5493174);
		assertCoordinates(hexBase, 4, 3, 6.4625, 6.5493174);
		assertCoordinates(hexBase, 4, 4, 7.2875, 7.8591805);
		assertCoordinates(hexBase, 4, 4, 7.321875, 7.8591805);
		assertCoordinates(hexBase, 4, 4, 7.390625, 7.91872);
		assertCoordinates(hexBase, 5, 5, 9.14375, 10.181211);
		assertCoordinates(hexBase, 6, 5, 9.384376, 9.823976);
		assertCoordinates(hexBase, 6, 5, 9.384376, 9.764436);
		assertCoordinates(hexBase, 6, 5, 9.040625, 9.46674);
		assertCoordinates(hexBase, 5, 4, 9.040625, 9.407201);
		assertCoordinates(hexBase, 6, 5, 9.14375, 9.288123);
		assertCoordinates(hexBase, 6, 4, 9.453125, 8.454573);
		assertCoordinates(hexBase, 7, 4, 10.621876, 8.514112);
		assertCoordinates(hexBase, 7, 4, 10.621876, 8.573651);
		assertCoordinates(hexBase, 7, 3, 10.931251, 7.4424057);
		assertCoordinates(hexBase, 7, 3, 10.690625, 6.9660916);
		assertCoordinates(hexBase, 6, 3, 10.553125, 6.727935);
		assertCoordinates(hexBase, 6, 3, 10.553125, 6.7874737);
		assertCoordinates(hexBase, 6, 3, 10.8625, 6.013464);
		assertCoordinates(hexBase, 6, 3, 10.8625, 6.073003);
		assertCoordinates(hexBase, 6, 3, 10.896875, 6.073003);
		assertCoordinates(hexBase, 7, 2, 11.103125, 5.7753067);
		assertCoordinates(hexBase, 7, 2, 10.931251, 5.2989926);
		assertCoordinates(hexBase, 7, 1, 11.446876, 4.048669);
		assertCoordinates(hexBase, 7, 1, 11.446876, 4.1082077);
		assertCoordinates(hexBase, 7, 2, 11.515625, 4.4654436);

	}

}
