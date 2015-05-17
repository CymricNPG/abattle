/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.client;

import net.npg.abattle.common.model.Cell;
import net.npg.abattle.common.model.CellTypes;

/**
 * @author spatzenegger
 * 
 */
public interface ClientCell extends Cell {

	public boolean isVisible();

	public void setCellType(CellTypes type);

	public void setHeight(int height);

	public void setVisible(boolean visible);
}
