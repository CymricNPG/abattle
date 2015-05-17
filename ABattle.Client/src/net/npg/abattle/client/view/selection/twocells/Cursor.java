/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.selection.twocells;

import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.utils.FloatPoint;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;

/**
 * @author spatzenegger
 * 
 */
public class Cursor {

	private final ClientBoard board;
	private IntPoint boardCoordinate;
	private FloatPoint worldCoordinate;
	private final HexBase hexBase;
	private boolean viewable;

	public Cursor(final HexBase hexBase, final ClientBoard board) {
		Validate.notNulls(hexBase, board);
		this.hexBase = hexBase;
		this.board = board;
		viewable = false;
		boardCoordinate = IntPoint.from(0, 0);
	}

	public IntPoint getBoardCoordinate() {
		assert boardCoordinate != null;
		return boardCoordinate;
	}

	public ClientCell getCellAtCursor() {
		return board.getCellAt(boardCoordinate);
	}

	public void hide() {
		viewable = false;
	}

	public boolean inSameHex(final Cursor otherCursor) {
		assert boardCoordinate != null;
		return boardCoordinate.equals(otherCursor.getBoardCoordinate());
	}

	public boolean isViewable() {
		return viewable;
	}

	public void show() {
		viewable = true;
	}

	/**
	 * @return the worldCoordinate
	 */
	public FloatPoint getWorldCoordinate() {
		return worldCoordinate;
	}

	/**
	 * @param worldCoordinate the worldCoordinate to set
	 */
	public void setWorldCoordinate(final FloatPoint worldCoordinate) {
		assert hexBase != null;
		final IntPoint coordinate = hexBase.getCellByPoint(worldCoordinate);
		if (board.isInside(coordinate)) {
			boardCoordinate = coordinate;
		}
		this.worldCoordinate = worldCoordinate;
	}

	public boolean setBoardCoordinate(final IntPoint boardCoordinate) {
		this.boardCoordinate = boardCoordinate;
		return board.isInside(boardCoordinate);
	}

}
