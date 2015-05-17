/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.hex;

import net.npg.abattle.common.utils.FloatPoint;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;

/**
 * standard mathematics for flat orientation hex on screen and board!
 * 
 * <pre>
 *    0    1
 *   /
 * 5/        2
 *  \
 *  4\     3
 * </pre>
 * 
 * .
 * 
 * @author cymric
 */
public class Hex {

	private static final int NUMBER_OF_CORNERS = 6;

	private final HexBase base;

	/**
	 * position on board
	 */
	private final IntPoint boardCoordinate;

	/**
	 * position on screen
	 */
	private final FloatPoint screenCoordinate;

	private final FloatPoint center;

	private final FloatPoint[] corners;

	/**
	 * Instantiates a new hex.
	 * 
	 * @param boardCoordinate the board coordinate
	 * @param base the base
	 */
	public Hex(final IntPoint boardCoordinate, final HexBase base) {
		Validate.notNull(boardCoordinate);
		Validate.notNull(base);
		this.base = base;
		this.boardCoordinate = boardCoordinate;
		screenCoordinate = new FloatPoint(calculateX(boardCoordinate), calculateY(boardCoordinate));

		center = new FloatPoint(getCenterX(), getCenterY());

		corners = new FloatPoint[NUMBER_OF_CORNERS];
		for (int corner = 0; corner < NUMBER_OF_CORNERS; corner++) {
			corners[corner] = new FloatPoint(screenCoordinate.x + base.cornersDX[corner], screenCoordinate.y + base.cornersDY[corner]);
		}

	}

	public static boolean areHexsAdjacent(final IntPoint coor1, final IntPoint coor2) {
		return calculateDistance(coor1, coor2) == 1;
	}

	/**
	 * Calculate distance between two hexes.
	 * 
	 * @param start the start hex
	 * @param end the end hex
	 * @return the distance
	 */
	public static int calculateDistance(final IntPoint start, final IntPoint end) {

		final int xs1 = start.y - start.x / 2;
		final int ys1 = start.x;
		final int zs1 = -(xs1 + ys1);

		final int xs2 = end.y - end.x / 2;
		final int ys2 = end.x;
		final int zs2 = -(xs2 + ys2);

		final int dx = Math.abs(xs2 - xs1);
		final int dy = Math.abs(ys2 - ys1);
		final int dz = Math.abs(zs2 - zs1);

		return Math.max(Math.max(dx, dy), dz);
	}

	public float getRadius() {
		return base.radius;
	}

	/**
	 * Calculate x.
	 * 
	 * @param boardCoordinate the board coordinate
	 * @return the float
	 */
	private float calculateX(final IntPoint boardCoordinate) {
		assert boardCoordinate != null;
		assert base != null;
		return boardCoordinate.x * base.side;
	}

	/**
	 * Calculate y.
	 * 
	 * @param boardCoordinate the board coordinate
	 * @return the float
	 */
	private float calculateY(final IntPoint boardCoordinate) {
		assert boardCoordinate != null;
		assert base != null;
		return base.height * (2 * boardCoordinate.y + (boardCoordinate.x % 2)) / 2;
	}

	/**
	 * Computes X and Y coordinates for all of the cell's 6 corners on the screen, clockwise, starting from the top
	 * left.
	 * 
	 * @param cornersX Array to fill in with X coordinates of the cell's corners
	 * @param cornersX Array to fill in with Y coordinates of the cell's corners
	 */
	public FloatPoint[] computeCorners() {
		assert corners != null;
		return corners;
	}

	/**
	 * Gets the board coordinate.
	 * 
	 * @return the board coordinate
	 */
	public IntPoint getBoardCoordinate() {
		return boardCoordinate;
	}

	/**
	 * Gets the center.
	 * 
	 * @return the center
	 */
	public FloatPoint getCenter() {
		assert center != null;
		return center;
	}

	/**
	 * @return X coordinate of the cell's center
	 */
	public float getCenterX() {
		assert screenCoordinate != null;
		assert base != null;
		return screenCoordinate.x + base.radius;
	}

	/**
	 * @return Y coordinate of the cell's center
	 */
	public float getCenterY() {
		assert screenCoordinate != null;
		assert base != null;
		return screenCoordinate.y + base.height / 2;
	}

	/**
	 * @return X coordinate of the cell's top left corner.
	 */
	public float getLeft() {
		assert screenCoordinate != null;
		return screenCoordinate.x;
	}

	/**
	 * Gets the screen coordinate.
	 * 
	 * @return the screen coordinate
	 */
	public FloatPoint getScreenCoordinate() {
		assert screenCoordinate != null;
		return screenCoordinate;
	}

	/**
	 * @return Y coordinate of the cell's top left corner.
	 */
	public float getTop() {
		assert screenCoordinate != null;
		assert base != null;
		return screenCoordinate.y;
	}

	/**
	 * @return Y coordinate of the cell's bottom left corner.
	 */
	public float getBottom() {
		assert screenCoordinate != null;
		assert base != null;
		return screenCoordinate.y + base.height;
	}

	/**
	 * @return the base
	 */
	public HexBase getBase() {
		return base;
	}
}
