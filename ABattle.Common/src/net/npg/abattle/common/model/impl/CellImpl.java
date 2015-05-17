/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.impl;

import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.model.Cell;
import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;

import com.google.common.base.Optional;

/**
 * @author cymric
 * 
 */
public abstract class CellImpl extends IDElementImpl implements Cell {

	private boolean battle;
	private final IntPoint boardCoordinate;
	private CellTypes cellType;
	private int height;
	private Optional<Player> owner;

	private int strength;
	private final CheckModelElement check;

	public CellImpl(final int id, final IntPoint boardCoordinate, final int height, final CellTypes cellType, final CheckModelElement check) {
		super(id);
		Validate.notNulls(boardCoordinate, cellType, check);
		check.checkHeight(height);
		check.checkCoordinate(boardCoordinate);
		this.boardCoordinate = boardCoordinate;
		this.height = height;
		this.cellType = cellType;
		this.battle = false;
		this.check = check;
		this.owner = Optional.absent();
	}

	@Override
	public IntPoint getBoardCoordinate() {
		assert boardCoordinate != null;
		return boardCoordinate;
	}

	@Override
	public CellTypes getCellType() {
		assert cellType != null;
		return cellType;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <PLAYERTYPE extends Player> Optional<PLAYERTYPE> getOwner() {
		return (Optional<PLAYERTYPE>) owner;
	}

	@Override
	public int getStrength() {
		return strength;
	}

	@Override
	public boolean hasBattle() {
		return battle;
	}

	@Override
	public boolean hasStructure() {
		return cellType.isStructure();
	}

	@Override
	public boolean isAdjacentTo(final Cell testCell) {
		return Hex.areHexsAdjacent(this.getBoardCoordinate(), testCell.getBoardCoordinate());
	}

	@Override
	public void setBattle(final boolean battle) {
		this.battle = battle;
	}

	public void setCellType(final CellTypes newCellType) {
		Validate.notNull(newCellType);
		cellType = newCellType;
	}

	public void setHeight(final int height) {
		this.height = height;
	}

	@Override
	public <PLAYERTYPE extends Player> void setOwner(final PLAYERTYPE owner) {
		this.owner = Optional.<Player> fromNullable(owner);
	}

	@Override
	public void setStrength(final int strength) {
		check.checkStrength(strength);
		this.strength = strength;
	}

	@Override
	public String toString() {
		return getId() + ":" + getBoardCoordinate().toString();
	}

	@Override
	public <PLAYERTYPE extends Player> boolean isOwner(final PLAYERTYPE player) {
		return owner.isPresent() && owner.get().equals(player);
	}

}
