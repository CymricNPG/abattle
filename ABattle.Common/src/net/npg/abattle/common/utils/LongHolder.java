package net.npg.abattle.common.utils;

public class LongHolder {

	public long value = 0;

	public void add(final long addValue) {
		value += addValue;
	}

	public float toFloat() {
		return value;
	}
}
