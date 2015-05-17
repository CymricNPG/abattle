/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.impl;

import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.hex.Directions;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.Cell;
import net.npg.abattle.common.model.Links;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.MyHashMap;
import net.npg.abattle.common.utils.MyMap;
import net.npg.abattle.common.utils.Validate;

/**
 * @author cymric
 * 
 */
public abstract class BoardImpl<PLAYERTYPE extends Player, CELLTYPE extends Cell, LINKTYPE extends Links<CELLTYPE>> extends IDElementImpl implements
		Board<PLAYERTYPE, CELLTYPE, LINKTYPE> {

	private final CELLTYPE[][] cells;
	private final int xsize;

	private final int ysize;

	private final MyMap<Integer, CELLTYPE> cellById;

	@SuppressWarnings("unchecked")
	public BoardImpl(final int xsize, final int ysize) {
		super();
		Validate.inclusiveBetween(CommonConstants.MIN_BOARD_SIZE, CommonConstants.MAX_BOARD_SIZE, xsize);
		Validate.inclusiveBetween(CommonConstants.MIN_BOARD_SIZE, CommonConstants.MAX_BOARD_SIZE, ysize);
		this.xsize = xsize;
		this.ysize = ysize;
		cells = (CELLTYPE[][]) new Cell[xsize][ysize]; // weak, but ok
		cellById = new MyHashMap<Integer, CELLTYPE>(xsize * ysize * 2);
	}

	@SuppressWarnings("unchecked")
	public BoardImpl(final int id, final int xsize, final int ysize) {
		super(id);
		Validate.inclusiveBetween(CommonConstants.MIN_BOARD_SIZE, CommonConstants.MAX_BOARD_SIZE, xsize);
		Validate.inclusiveBetween(CommonConstants.MIN_BOARD_SIZE, CommonConstants.MAX_BOARD_SIZE, ysize);
		this.xsize = xsize;
		this.ysize = ysize;
		cells = (CELLTYPE[][]) new Cell[xsize][ysize]; // weak, but ok
		cellById = new MyHashMap<Integer, CELLTYPE>(xsize * ysize * 2);
	}

	@Override
	public void accept(final ModelVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public CELLTYPE getAdjacentCell(final CELLTYPE cell, final Directions direction) {
		Validate.notNulls(cell, direction);
		final IntPoint newCoordinate = direction.getAdjacentCoordinateTo(cell.getBoardCoordinate());
		if (isOutsideBoard(newCoordinate.x, newCoordinate.y)) {
			return null;
		} else {
			return cells[newCoordinate.x][newCoordinate.y];
		}
	}

	@Override
	public CELLTYPE getCellAt(final int x, final int y) {
		assert cells != null;
		if (isOutsideBoard(x, y)) {
			throw new ArrayIndexOutOfBoundsException("X:" + x + " y:" + y + " xsize:" + xsize + " ysize:" + ysize);
		}
		final CELLTYPE cell = cells[x][y];
		Validate.notNull(cell);
		return cell;
	}

	@Override
	public CELLTYPE getCellAt(final IntPoint boardCoordinate) {
		return getCellAt(boardCoordinate.x, boardCoordinate.y);
	}

	@Override
	public int getXSize() {
		return xsize;
	}

	@Override
	public int getYSize() {
		return ysize;
	}

	@Override
	public boolean isInside(final IntPoint coordinate) {
		Validate.notNull(coordinate);
		return !isOutsideBoard(coordinate.x, coordinate.y);
	}

	private boolean isOutsideBoard(final int newX, final int newY) {
		return newX < 0 || newY < 0 || newX >= xsize || newY >= ysize;
	}

	@Override
	public void setCellAt(final CELLTYPE cell) {
		Validate.notNull(cell);
		assert cellById != null;
		assert cells != null;
		final int x = cell.getBoardCoordinate().x;
		final int y = cell.getBoardCoordinate().y;

		if (isOutsideBoard(x, y)) {
			throw new ArrayIndexOutOfBoundsException("X:" + x + " y:" + y + " xsize:" + xsize + " ysize:" + ysize);
		}
		Validate.isNull(cells[x][y]);
		cells[x][y] = cell;
		cellById.put(cell.getId(), cell);
	}

	public CELLTYPE getCell(final int cellId) {
		assert cellById != null;
		final CELLTYPE cell = cellById.get(cellId);
		if (cell == null) {
			throw new IllegalArgumentException("Unknown cellid " + cellId);
		}
		return cell;
	}

	@Override
	public boolean isCellInitialized(final int x, final int y) {
		return cells[x][y] != null;
	}
}
