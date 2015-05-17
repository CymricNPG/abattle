/**
 *
 */
package net.npg.abattle.common.utils;

/**
 * @author Cymric
 */
public class MutableFloatPoint {

	public float x;

	public float y;

	public MutableFloatPoint(final float x, final float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public MutableFloatPoint(final double x, final double y) {
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
		if (!(obj instanceof MutableFloatPoint)) {
			return false;
		}
		final MutableFloatPoint other = (MutableFloatPoint) obj;
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

	public void set(final float x, final float y) {
		this.x = x;
		this.y = y;
	}

}
