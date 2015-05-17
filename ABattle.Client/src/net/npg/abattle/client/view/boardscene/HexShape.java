/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.utils.Validate;

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
public class HexShape extends CellShape {

	/**
	 * 
	 * @param cell the referenced cell
	 * @param boardConfiguration the boardConfiguration
	 */
	public HexShape(final ClientCell cell, final Hex hex) {
		super(cell, hex);
	}

	@Override
	public void accept(final SceneRenderer visitor) {
		Validate.notNull(visitor);
		visitor.visit(this);
	}
}