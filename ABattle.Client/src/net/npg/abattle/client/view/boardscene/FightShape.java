/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import java.util.Iterator;
import java.util.LinkedList;

import net.npg.abattle.common.configuration.GraphicsConfigurationData;
import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.utils.FloatPoint;
import net.npg.abattle.common.utils.MutableFloatPoint3;
import net.npg.abattle.common.utils.Validate;

/**
 * @author cymric
 * 
 */
public class FightShape extends CellShape {

	private int cooldown = 0;
	private final LinkedList<MutableFloatPoint3> bombs;
	private long lastBombAdd = System.currentTimeMillis();
	private long lastBombGrow = System.currentTimeMillis();
	private final GraphicsConfigurationData configuration;

	/**
	 * @param cell
	 * @param boardConfiguration
	 */
	public FightShape(final ClientCell cell, final Hex hex, final GraphicsConfigurationData configuration) {
		super(cell, hex);
		Validate.notNull(configuration);
		bombs = new LinkedList<MutableFloatPoint3>();
		this.configuration = configuration;
	}

	@Override
	public void accept(final SceneRenderer visitor) {
		Validate.notNull(visitor);
		visitor.visit(this);
	}

	public void grow() {
		if (isTimeForNewBomb()) {
			addBomb();
		}
		if (isTimeForGrowth()) {
			doBombGroth();
		}
	}

	private void doBombGroth() {
		final Iterator<MutableFloatPoint3> it = bombs.iterator();
		while (it.hasNext()) {
			final MutableFloatPoint3 bomb = it.next();
			bomb.z--;
			if (bomb.z <= 0) {
				it.remove();
			}
		}
	}

	private boolean isTimeForGrowth() {
		if (lastBombGrow + configuration.getBombGrowTime() - System.currentTimeMillis() < 0L) {
			lastBombGrow = System.currentTimeMillis();
			return true;
		} else {
			return false;
		}
	}

	private boolean isTimeForNewBomb() {
		if ((lastBombAdd + configuration.getBombAddTime() - System.currentTimeMillis() < 0L || bombs.isEmpty()) && bombs.size() < 10 && !isInCooldown()) {
			lastBombAdd = System.currentTimeMillis();
			return true;
		} else {
			return false;
		}
	}

	private void addBomb() {
		final FloatPoint center = getHex().getCenter();
		final float centerRadius = getHex().getRadius();
		final float radius = configuration.getFightRadius();
		final float x = (float) Math.random() * radius * 2 - radius;
		final float y = (float) Math.random() * radius * 2 - radius;
		final float z = 20;
		bombs.add(new MutableFloatPoint3(x * centerRadius + center.x, y * centerRadius + center.y, z));
	}

	@Override
	public boolean isDrawable() {
		if (!super.isDrawable()) {
			cooldown = 0;
			return false;
		} else if (isInCooldown()) {
			cooldown--;
			return true;
		} else if (cooldown == 0 && getCell().hasBattle()) {
			bombs.clear();
			cooldown = configuration.getCooldownFrames();
			return true;
		} else if (getCell().hasBattle()) {
			cooldown = configuration.getCooldownFrames();
			return true;
		} else {
			return false;
		}
	}

	private boolean isInCooldown() {
		return cooldown > 0 && !getCell().hasBattle();
	}

	/**
	 * @return the bombs
	 */
	protected LinkedList<MutableFloatPoint3> getBombs() {
		return bombs;
	}
}
