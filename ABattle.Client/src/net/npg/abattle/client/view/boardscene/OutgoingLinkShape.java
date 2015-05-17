/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.utils.FloatPoint;
import net.npg.abattle.common.utils.Validate;

import com.badlogic.gdx.graphics.Color;

/**
 * @author cymric
 * 
 */
public class OutgoingLinkShape extends Shape {
	private final ClientBoard board;
	private final ClientCell endCell;
	private final Hex endHex;
	private final ClientCell startCell;
	private final Hex startHex;

	public OutgoingLinkShape(final ClientCell startCell, final ClientCell endCell, final ClientBoard board, final Hex startHex, final Hex endHex) {
		super();
		this.startCell = startCell;
		this.endCell = endCell;
		this.startHex = startHex;
		this.endHex = endHex;
		this.board = board;
	}

	@Override
	public void accept(final SceneRenderer visitor) {
		Validate.notNull(visitor);
		visitor.visit(this);
	}

	public Color getColor() {
		return RendererUtils.getOwnerColor(startCell);
	}

	public FloatPoint getEndCoordinate() {
		return endHex.getCenter();

	}

	@Override
	public Hex getHex() {
		return startHex;
	}

	public FloatPoint getStartCoordinate() {
		return startHex.getCenter();
	}

	@Override
	public boolean isDrawable() {
		return startCell.isVisible() && board.getLinks().doesLinkExists(startCell, endCell);
	}
}
