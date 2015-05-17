/**
 *
 */
package net.npg.abattle.common.utils;

/**
 * @author Cymric
 */
public class FloatPoint {

	public final float x;

	public final float y;

	public FloatPoint(final float x, final float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public FloatPoint(final double x, final double y) {
		this((float) x, (float) y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FloatPoint)) {
			return false;
		}
		final FloatPoint other = (FloatPoint) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) {
			return false;
		}
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return x + "/" + y;
	}

}
