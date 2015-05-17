package net.npg.abattle.common.utils

class IntPoint3 {

	public final int x;

	public final int y;

	public final int z;

	new(int x, int y, int z) {
		super();
		this.x = x
		this.y = y
		this.z = z
	}

	override def String toString() {
		return x + "/" + y + "/" + z;
	}

}
