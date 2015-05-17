package net.npg.abattle.common.model;

public enum CellTypes {
	BASE(true), PLAIN(false), TOWN(true);

	private boolean structure;

	private CellTypes(final boolean structure) {
		this.structure = structure;
	}

	public boolean isStructure() {
		return structure;
	}
}