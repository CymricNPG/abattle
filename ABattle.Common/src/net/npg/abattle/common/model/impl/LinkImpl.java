/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.impl;

import net.npg.abattle.common.model.Cell;
import net.npg.abattle.common.model.Link;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.utils.Validate;

/**
 * @author Cymric
 * 
 */
public class LinkImpl<T extends Cell> extends IDElementImpl implements Link<T> {

	private final T destinationCell;
	private final T sourceCell;

	public LinkImpl(final int id, final T sourceCell, final T destinationCell) {
		super(id);
		Validate.notNulls(sourceCell, destinationCell);
		this.destinationCell = destinationCell;
		this.sourceCell = sourceCell;
	}

	protected LinkImpl(final T sourceCell, final T destinationCell) {
		super();
		Validate.notNulls(sourceCell, destinationCell);
		this.destinationCell = destinationCell;
		this.sourceCell = sourceCell;
	}

	@Override
	public void accept(final ModelVisitor visitor) {
		Validate.notNulls(visitor);
		visitor.visit(this);
	}

	@Override
	public T getDestinationCell() {
		assert destinationCell != null;
		return destinationCell;
	}

	@Override
	public T getSourceCell() {
		assert sourceCell != null;
		return sourceCell;
	}

	@Override
	public String toString() {
		assert destinationCell != null;
		assert sourceCell != null;
		return "LinkImpl [destinationCell=" + destinationCell.getId() + ", sourceCell=" + sourceCell.getId() + "]";
	}

}
