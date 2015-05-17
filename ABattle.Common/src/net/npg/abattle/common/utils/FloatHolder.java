/**
 * 
 */
package net.npg.abattle.common.utils;

/**
 * @author Cymric
 * 
 */
public class FloatHolder {

	public float value = 0.0f;

	/**
	 * 
	 * @param newValue
	 * @return old value
	 */
	public float replace(final float newValue) {
		final float returnValue = value;
		value = newValue;
		return returnValue;
	}

	public float add(final float addValue) {
		value += addValue;
		return value;
	}
}
