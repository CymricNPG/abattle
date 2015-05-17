/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.common.configuration.GraphicsConfigurationData;
import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.utils.Validate;

/**
 * @author cymric
 * 
 */
public class FilledCircleShape extends CellShape {

	private final Float radius;
	private final GraphicsConfigurationData graphicsConfiguration;
	private final GameConfiguration gameConfiguration;

	protected FilledCircleShape(final ClientCell cell, final Hex hex, final GraphicsConfigurationData graphicsConfiguration,
			final GameConfiguration gameConfiguration) {
		super(cell, hex);
		this.radius = hex.getRadius();
		this.graphicsConfiguration = graphicsConfiguration;
		this.gameConfiguration = gameConfiguration;
	}

	@Override
	public void accept(final SceneRenderer visitor) {
		Validate.notNull(visitor);
		visitor.visit(this);
	}

	public float getRadius() {
		final float minRadius = radius * graphicsConfiguration.getMinUnitSize();
		final float maxRadius = (radius * graphicsConfiguration.getMaxUnitSize() * getCell().getStrength())
				/ gameConfiguration.getConfiguration().getMaxCellStrength();
		return Math.max(minRadius, maxRadius);
	}

	public boolean isFilled() {
		return getCell().getStrength() > 0;
	}

	@Override
	public boolean isDrawable() {
		return getCell().getOwner().isPresent() && getCell().isVisible();

	}

}
