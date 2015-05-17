/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.client.view.selection.twocells.Cursor;
import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.hex.HexBase;

/**
 * @author spatzenegger
 * 
 */
public class CursorHexShape extends Shape {

	private final Cursor cursor;
	private final HexBase hexBase;

	public CursorHexShape(final Cursor cursor, final HexBase hexBase) {
		super();
		this.cursor = cursor;
		this.hexBase = hexBase;
	}

	@Override
	public void accept(final SceneRenderer visitor) {
		visitor.visit(this);
	}

	@Override
	public Hex getHex() {
		return new Hex(cursor.getBoardCoordinate(), hexBase);

	}

	@Override
	public boolean isDrawable() {
		return cursor.isViewable();

	}

}
