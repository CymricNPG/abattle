/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.common.configuration.GraphicsConfigurationData;
import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.utils.Validate;

/**
 * @author cymric
 *
 */
public class CircleShape extends CellShape {

	private final Float radius;
	private final GraphicsConfigurationData graphicsConfiguration;

	/**
	 * @param cell
	 * @param boardConfiguration
	 */
	public CircleShape(final ClientCell cell, final Hex hex, final GraphicsConfigurationData graphicsConfiguration) {
		super(cell, hex);
		this.radius = hex.getRadius();
		this.graphicsConfiguration = graphicsConfiguration;
	}

	@Override
	public void accept(final SceneRenderer visitor) {
		Validate.notNull(visitor);
		visitor.visit(this);
	}

	public float getRadius() {
		return radius * graphicsConfiguration.getStructureSize();
	}

	/**
	 * @return the bold
	 */
	public boolean isIrregular() {
		return CellTypes.BASE.equals(getCell().getCellType());
	}

	/**
	 * @return the bold
	 */
	public boolean isBold() {
		return true;
	}

	@Override
	public boolean isDrawable() {
		return getCell().hasStructure() && getCell().isVisible();
	}

}
