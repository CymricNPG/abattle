/**
 *
 */
package net.npg.abattle.common.utils;

/**
 * @author cymric
 * 
 */
public class IntHolder {

	public int value = 0;

	public void add(final int addValue) {
		value += addValue;
	}

	public IntHolder(final int newValue) {
		this.value = newValue;
	}

}
