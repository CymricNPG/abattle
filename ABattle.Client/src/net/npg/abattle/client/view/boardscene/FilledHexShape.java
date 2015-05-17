/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.model.Cell;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.utils.Validate;

import com.badlogic.gdx.graphics.Color;

/**
 * flat orientation hex!
 * 
 * <pre>
 *    0    1
 *   /
 * 5/        2
 *  \
 *  4\     3
 * </pre>
 * 
 * @author cymric
 * 
 */
public class FilledHexShape extends CellShape {

	private final Color lowColor = new Color(0, 1, 0, 1);
	private final Color highColor = new Color(0.25f, 0.4f, 0, 1);

	private final ClientBoard board;
	private final GameConfiguration gameConfiguration;

	/**
	 * 
	 * @param cell the referenced cell
	 * @param gameConfiguration
	 * @param boardConfiguration the boardConfiguration
	 */
	public FilledHexShape(final ClientBoard board, final ClientCell cell, final Hex hex, final GameConfiguration gameConfiguration) {
		super(cell, hex);
		this.board = board;
		this.gameConfiguration = gameConfiguration;
	}

	@Override
	public void accept(final SceneRenderer visitor) {
		Validate.notNull(visitor);
		visitor.visit(this);

	}

	public Color[] getCornerColors() {
		final Color[] colors = new Color[6];
		final int height0 = getCell().getHeight();
		for (int i = 0; i < 6; i++) {
			final Cell cell1 = board.getAdjacentCell(getCell(), getHex().getBase().cornerCells[i][0]);
			final Cell cell2 = board.getAdjacentCell(getCell(), getHex().getBase().cornerCells[i][1]);
			final float height1 = getHeight(cell1, height0);
			final float height2 = getHeight(cell2, height0);
			colors[i] = getColor((height0 + height1 + height2) / 3.0f);
		}
		return colors;
	}

	private Color getColor(final float height) {
		final float div = Math.max(gameConfiguration.getConfiguration().getMaxCellHeight() - 1.0f, 1.0f);
		return lowColor.cpy().lerp(highColor, height / div);
	}

	@Override
	public Color getColor() {
		final int height = getHeight(getCell(), 0);
		return getColor(height);
	}

	private int getHeight(final Cell cell, final int defaultHeight) {
		if (cell == null) {
			return defaultHeight;
		}
		return cell.getHeight();
	}
}