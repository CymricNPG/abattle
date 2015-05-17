/**
 *
 */
package net.npg.abattle.common.utils;

/**
 * @author Cymric
 * 
 */
public class IntPoint {

	private static IntPoint[][] precalculated;

	public final int x;

	public final int y;

	private final int hashcode;

	private IntPoint(final int x, final int y) {
		super();
		this.x = x;
		this.y = y;
		int result = 1;
		result = 31 * result + x;
		result = 31 * result + y;
		hashcode = result;
	}

	@Override
	public int hashCode() {
		return hashcode;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IntPoint)) {
			return false;
		}
		final IntPoint other = (IntPoint) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return x + "/" + y;
	}

	public static IntPoint from(final int x, final int y) {
		if (x < 128 && x >= 0 && y < 128 && y >= 0) {
			if (precalculated == null) {
				precalculated = new IntPoint[128][128];
				FieldLoop.visitAllFields(128, 128, new FieldVisitor() {

					@Override
					public void visit(final int x, final int y) {
						precalculated[x][y] = new IntPoint(x, y);
					}
				});
			}
			return precalculated[x][y];
		} else {
			return new IntPoint(x, y);
		}
	}
}
