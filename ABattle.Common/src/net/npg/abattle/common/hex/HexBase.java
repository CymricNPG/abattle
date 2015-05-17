package net.npg.abattle.common.hex;

import net.npg.abattle.common.utils.FloatPoint;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;

/**
 * standard mathematics for flat orientation hex!
 * 
 * <pre>
 *    0    1
 *   /
 * 5/        2
 *  \
 *  4\     3
 * </pre>
 * 
 * @author cymric
 * 
 */
public class HexBase {
	public final float[] cornersDX; // array of horizontal offsets of the cell's corners
	public final float[] cornersDY; // array of vertical offsets of the cell's corners
	public final Directions[][] cornerCells; // for each corner the adjacent cells

	/**
	 * Cell height,y-length from 0->4
	 */
	public final float height;

	/**
	 * Cell radius (distance from center to one of the corners)
	 */
	public final float radius;
	/**
	 * x-length from 5->1
	 */
	public final float side;
	/**
	 * Cell width,x-length from 5->2
	 */
	public final float width;

	public HexBase(final float radius) {
		this.radius = radius;
		this.width = radius * 2;
		height = (float) (radius * Math.sqrt(3));
		side = radius * 3 / 2;

		cornersDX = new float[] { radius / 2, side, width, side, radius / 2, 0 };
		cornersDY = new float[] { 0, 0, height / 2, height, height, height / 2 };
		cornerCells = new Directions[][] { { Directions.NW, Directions.N },//
				{ Directions.N, Directions.NE },//
				{ Directions.NE, Directions.SE },//
				{ Directions.SE, Directions.S },//
				{ Directions.S, Directions.SW },//
				{ Directions.SW, Directions.NW } //
		};
	}

	public IntPoint getCellByPoint(final FloatPoint screenCoordinate) {
		Validate.notNull(screenCoordinate);
		final float ci = (float) Math.floor(screenCoordinate.x / side);
		final float cx = screenCoordinate.x - side * ci;

		final float ty = (screenCoordinate.y - (ci % 2) * height / 2);
		final float cj = (float) Math.floor(ty / height);
		final float cy = (ty - height * cj);

		if (cx > Math.abs(radius / 2 - radius * cy / height)) {
			return IntPoint.from((int) ci, (int) cj);
		} else {
			return IntPoint.from((int) ci - 1, (int) (cj + (ci % 2) - ((cy < height / 2) ? 1 : 0)));
		}
	}

	public Directions getDirections(final FloatPoint start, final FloatPoint end) {
		final double angle = getAngle(start, end);
		if (angle < 60.0) {
			return Directions.NE;
		}
		if (angle < 120.0) {
			return Directions.N;
		}
		if (angle < 180.0) {
			return Directions.NW;
		}
		if (angle < 240.0) {
			return Directions.SW;
		}
		if (angle < 300.0) {
			return Directions.S;
		}
		return Directions.SE;
	}

	/**
	 * Fetches angle relative to screen centre point (start)
	 * 
	 * @return angle in degress from 0-360.
	 */
	public double getAngle(final FloatPoint start, final FloatPoint end) {
		final double dx = end.x - start.x;
		final double dy = -(end.y - start.y);

		final double inRads = Math.atan2(dy, dx);

		final double degrees = Math.toDegrees(inRads);
		if (degrees < 0) {
			return 360 + degrees;
		} else {
			return degrees;
		}
	}
}