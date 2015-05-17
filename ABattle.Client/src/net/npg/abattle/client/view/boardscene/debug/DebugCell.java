/**
 *
 */
package net.npg.abattle.client.view.boardscene.debug;

import net.npg.abattle.client.view.boardscene.CellShape;
import net.npg.abattle.common.configuration.impl.NamedClass;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

/**
 * @author cymric
 *
 */
public interface DebugCell extends NamedClass {
	Function1<CellShape, String> getTextCreator();
}
