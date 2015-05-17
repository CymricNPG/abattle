/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.model.clientfacade;

import net.npg.abattle.common.error.NotAvailableException;
import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.impl.CellImpl;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerPlayer;

import com.google.common.base.Optional;

/**
 * @author Cymric
 * 
 */
public class ClientCellFacade extends CellImpl implements ClientCell {

	private final ServerCell cell;
	private final PlayersFacade playersFacade;

	public ClientCellFacade(final ServerCell cell, final CheckModelElement checker, final PlayersFacade playersFacade) {
		super(cell.getId(), cell.getBoardCoordinate(), cell.getHeight(), cell.getCellType(), checker);
		Validate.notNulls(cell, playersFacade);
		this.cell = cell;
		this.playersFacade = playersFacade;
	}

	@Override
	public void accept(final ModelVisitor visitor) {
		Validate.notNull(visitor);
		visitor.visit(this);
	}

	@Override
	public IntPoint getBoardCoordinate() {
		assert (cell != null);
		return cell.getBoardCoordinate();
	}

	@Override
	public CellTypes getCellType() {
		assert (cell != null);
		return cell.getCellType();
	}

	@Override
	public int getHeight() {
		assert (cell != null);
		return cell.getHeight();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<ClientPlayer> getOwner() {
		assert (cell != null);
		final Optional<ServerPlayer> owner = cell.getOwner();
		if (owner == null || !owner.isPresent()) {
			return Optional.absent();
		}
		return playersFacade.getPlayer(owner.get());
	}

	@Override
	public int getStrength() {
		assert (cell != null);
		return cell.getStrength();
	}

	@Override
	public boolean hasBattle() {
		assert (cell != null);
		return cell.hasBattle();
	}

	@Override
	public boolean hasStructure() {
		assert (cell != null);
		return cell.hasStructure();
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public void setBattle(final boolean hasBattle) {
		throw new NotAvailableException();
	}

	@Override
	public void setHeight(final int height) {
		throw new NotAvailableException();

	}

	@Override
	public <PLAYERTYPE extends Player> void setOwner(final PLAYERTYPE player) {
		throw new NotAvailableException();
	}

	@Override
	public void setStrength(final int newStrength) {
		throw new NotAvailableException();
	}

	@Override
	public void setVisible(final boolean b) {
		throw new NotAvailableException();

	}

	@Override
	public <PLAYERTYPE extends Player> boolean isOwner(final PLAYERTYPE player) {
		return getOwner().isPresent() && getOwner().get().equals(player);
	}

}
