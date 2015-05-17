/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model;

import java.util.Collection;

import net.npg.abattle.common.error.BaseException;

/**
 * @author Cymric
 * 
 */
public interface BoardCreator<T extends Board<?, ?, ?>> {

	T getBoard();

	void run(final Collection<? extends Player> players) throws BaseException;
}
