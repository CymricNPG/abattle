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
import net.npg.abattle.common.model.impl.CellImpl;
import net.npg.abattle.common.utils.IntPoint;

/**
 * @author Cymric
 * 
 */
public class InvisibleClientCell extends CellImpl implements ClientCell {

	/**
	 * Instantiates a new invisible client cell.
	 */
	public InvisibleClientCell(final int id, final IntPoint boardCoordinate, final CheckModelElement checker) {
		super(id, boardCoordinate, 0, CellTypes.PLAIN, checker);
	}

	@Override
	public void accept(final ModelVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public boolean isVisible() {
		return false;
	}

	@Override
	public void setHeight(final int height) {
		throw new NotAvailableException();
	}

	@Override
	public void setVisible(final boolean b) {
		throw new NotAvailableException();

	}

	@Override
	public <PLAYERTYPE extends Player> boolean isOwner(final PLAYERTYPE player) {
		return false;
	}
}
