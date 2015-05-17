package net.npg.abattle.client.view.selection;

import java.util.List;

import net.npg.abattle.client.view.selection.twocells.Cursor;
import net.npg.abattle.common.utils.FloatPoint;

public interface SelectionModel {

	/**
	 * coordinates in the world (unproject screen), not board
	 * 
	 * @param worldCoordinate
	 */
	void dragSelection(FloatPoint worldCoordinate);

	void endSelection(FloatPoint worldCoordinate);

	List<Cursor> getCursors();

	Cursor getEndCursor();

	Cursor getStartCursor();

	void resetSelection();

	void startSelection(FloatPoint worldCoordinate);

	boolean inSelectionMode();

	boolean isValid();

}