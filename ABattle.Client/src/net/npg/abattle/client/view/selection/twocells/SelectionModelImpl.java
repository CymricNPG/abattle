/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.selection.twocells;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.npg.abattle.client.view.selection.SelectionModel;
import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.utils.FloatPoint;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;

/**
 * @author spatzenegger
 * 
 */
public class SelectionModelImpl implements SelectionModel {

	private final Cursor endCursor;
	private boolean inSelectionMode;
	private final Cursor startCursor;
	private final List<Cursor> cursors;
	private final ClientBoard board;
	private final HexBase hexBase;
	private final ClientPlayer localPlayer;

	public SelectionModelImpl(final ClientBoard board, final HexBase hexBase, final ClientPlayer localPlayer) {
		Validate.notNulls(board, hexBase);
		endCursor = new Cursor(hexBase, board);
		startCursor = new Cursor(hexBase, board);
		this.hexBase = hexBase;
		inSelectionMode = false;
		final List<Cursor> tmpCursors = new ArrayList<Cursor>();
		tmpCursors.add(startCursor);
		tmpCursors.add(endCursor);
		this.localPlayer = localPlayer;
		cursors = Collections.unmodifiableList(tmpCursors);
		this.board = board;
	}

	@Override
	public synchronized void dragSelection(final FloatPoint coordinate) {
		if (!inSelectionMode) {
			return;
		}
		endCursor.setWorldCoordinate(coordinate);
		if (Hex.areHexsAdjacent(startCursor.getBoardCoordinate(), endCursor.getBoardCoordinate())) {
			endCursor.show();
		} else {
			endCursor.hide();
		}
	}

	@Override
	public synchronized void endSelection(final FloatPoint coordinate) {
		assert inSelectionMode;
		dragSelection(coordinate);
		resetSelection();
	}

	@Override
	public List<Cursor> getCursors() {
		return cursors;
	}

	@Override
	public Cursor getEndCursor() {
		assert endCursor != null;
		return endCursor;
	}

	@Override
	public Cursor getStartCursor() {
		assert startCursor != null;
		return startCursor;

	}

	@Override
	public void resetSelection() {
		inSelectionMode = false;
		startCursor.hide();
		endCursor.hide();
	}

	@Override
	public synchronized void startSelection(final FloatPoint floatPoint) {
		assert !inSelectionMode;

		if (!checkIfValidStart(floatPoint)) {
			return;
		}

		inSelectionMode = true;
		startCursor.setWorldCoordinate(floatPoint);
		startCursor.show();
		endCursor.hide();
	}

	private boolean checkIfValidStart(final FloatPoint floatPoint) {
		final IntPoint coordinate = hexBase.getCellByPoint(floatPoint);
		if (!board.isInside(coordinate)) {
			return false;
		}
		final ClientCell cell = board.getCellAt(coordinate);
		return cell.isVisible() && cell.isOwner(localPlayer);
	}

	@Override
	public boolean inSelectionMode() {
		return inSelectionMode;
	}

	@Override
	public boolean isValid() {
		return board.isInside(startCursor.getBoardCoordinate()) && board.isInside(endCursor.getBoardCoordinate())
				&& !startCursor.getBoardCoordinate().equals(endCursor.getBoardCoordinate());
	}

}
