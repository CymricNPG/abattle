package net.npg.abattle.common.hex;

import net.npg.abattle.common.utils.IntPoint;

public enum Directions {
	N(0, -1, -1, 1), NE(1, 0, -1, 2), NW(-1, 0, -1, -3), S(0, 1, 1, -1), SE(1, 1, 0, 3), SW(-1, 1, 0, -2);
	private int ordinal;
	private final int xAdd;
	private final int yAddEven;
	private final int yAddOdd;

	public final static Directions[] cachedValues = values();

	private Directions(final int xAdd, final int yAddOdd, final int yAddEven, final int ordinal) {
		this.xAdd = xAdd;
		this.yAddOdd = yAddOdd;
		this.yAddEven = yAddEven;
		this.ordinal = ordinal;
	}

	public int getXadd() {
		return xAdd;
	}

	public int getYadd(final int x) {
		return x % 2 == 0 ? yAddEven : yAddOdd;
	}

	public Directions opposite() {
		final int oppositeOrdinal = -ordinal;
		for (final Directions testDir : Directions.values()) {
			if (testDir.ordinal == oppositeOrdinal) {
				return testDir;
			}
		}
		throw new IllegalArgumentException(this.toString() + " is defined wrong.");
	}

	public IntPoint getAdjacentCoordinateTo(final IntPoint coordinate) {
		final int newX = coordinate.x + getXadd();
		final int newY = coordinate.y + getYadd(coordinate.x);
		return IntPoint.from(newX, newY);
	}
}