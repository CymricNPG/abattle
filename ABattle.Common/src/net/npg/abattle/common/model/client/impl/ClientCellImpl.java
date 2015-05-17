/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.client.impl;

import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.impl.CellImpl;
import net.npg.abattle.common.utils.IntPoint;

/**
 * @author cymric
 * 
 */
public class ClientCellImpl extends CellImpl implements ClientCell {

	private boolean visible;

	public ClientCellImpl(final int id, final IntPoint boardCoordinate, final int height, final CellTypes cellType, final CheckModelElement check) {
		super(id, boardCoordinate, height, cellType, check);
	}

	@Override
	public void accept(final ModelVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ClientCellImpl other = (ClientCellImpl) obj;
		if (getId() != other.getId()) {
			return false;
		}
		if (!getBoardCoordinate().equals(other.getBoardCoordinate())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void setVisible(final boolean visible) {
		this.visible = visible;
	}
}
