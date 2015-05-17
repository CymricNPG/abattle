/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.utils.Validate;

import com.badlogic.gdx.graphics.Color;

/**
 * @author spatzenegger
 * 
 */
public abstract class CellShape extends Shape {
	private final ClientCell cell;
	private final Hex hex;

	public CellShape(final ClientCell cell, final Hex hex) {
		super();
		Validate.notNulls(cell);
		Validate.notNulls(hex);
		this.cell = cell;
		this.hex = hex;
	}

	public ClientCell getCell() {
		assert cell != null;
		return cell;
	}

	public Color getColor() {
		assert cell != null;
		return RendererUtils.getOwnerColor(cell);
	}

	@Override
	public Hex getHex() {
		return hex;
	}

	@Override
	public boolean isDrawable() {
		assert cell != null;
		return cell.isVisible();
	}
}
