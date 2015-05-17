package net.npg.abattle.common.utils;

import org.eclipse.xtext.xbase.lib.Inline;

public class MyArrayLiterals {

	@Inline("new $3[$1]")
	public static <T> T[] createArray(final int size) {
		throw new UnsupportedOperationException();
	}

	@Inline("new $4[$1][$2]")
	public static <T> T[][] createArray(final int outerSize, final int innerSize) {
		throw new UnsupportedOperationException();
	}

	@Inline("new int[$1][$2]")
	public static int[][] intArray(final int outerSize, final int innerSize) {
		throw new UnsupportedOperationException();
	}

	@Inline("new float[$1]")
	public static float[] floatArray(final int outerSize) {
		throw new UnsupportedOperationException();
	}
}
