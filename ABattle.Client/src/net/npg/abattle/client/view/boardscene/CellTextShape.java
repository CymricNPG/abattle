/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.model.client.ClientCell;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

/**
 * @author spatzenegger
 *
 */
public class CellTextShape extends CellShape {

	private final Fonts font;
	private final Function1<CellShape, String> textCreator;

	public CellTextShape(final ClientCell cell, final Hex hex, final Fonts font, final Function1<CellShape, String> textCreator) {
		super(cell, hex);
		this.font = font;
		this.textCreator = textCreator;
	}

	@Override
	public void accept(final SceneRenderer visitor) {
		visitor.visit(this);

	}

	public Fonts getFont() {
		return font;
	}

	public String getText() {
		return textCreator.apply(this);
	}

	@Override
	public boolean isDrawable() {
		return textCreator != null && !getText().isEmpty();
	}

}
