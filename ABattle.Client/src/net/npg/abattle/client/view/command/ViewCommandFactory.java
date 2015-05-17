/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.command;

import net.npg.abattle.client.view.selection.SelectionModel;

/**
 * @author spatzenegger
 * 
 */
public interface ViewCommandFactory {

	public void createLinkCommand(final SelectionModel selectionModel);
}
