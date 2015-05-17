/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.communication.command;

import com.google.common.base.Optional;

/**
 * @author Cymric
 * 
 */
public interface CommandProcessor<T extends GameCommand> {

	/**
	 * returns a command if the execution fails. This command is added to the queue
	 * 
	 * @param command
	 * @return
	 */
	public Optional<ErrorCommand> execute(T command, int destination);

}
