/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.common.model.CellVisitor;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientPlayer;

/**
 * @author spatzenegger
 * 
 */
public interface ClientCellVisitor extends CellVisitor<ClientPlayer, ClientCell> {

}
