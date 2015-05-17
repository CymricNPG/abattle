/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.communication.command;

import java.util.List;

import net.npg.abattle.common.utils.Disposeable;

import com.google.common.base.Optional;

/**
 * @author spatzenegger
 * 
 */
public interface CommandQueue extends Disposeable {

	/**
	 * add a command to the queue
	 * 
	 * @param command the command
	 * @param commandType to which component should the command be send
	 * @param destination list of client ids to send the command
	 */
	void addCommand(final GameCommand command, CommandType commandType, List<Integer> destination);

	/**
	 * 
	 * @param processor a class which can handle the specified Command in processedClass and with the given destination
	 *            type
	 * @param processedClass can be null if only the commandType matched (wildcard match) used e.g. for sending all over
	 *            the netwok
	 * @param processedType
	 */
	<T extends GameCommand> void registerCommandProcessor(final CommandProcessor<T> processor, Optional<Class<T>> processedClass, CommandType processedType);

	CommandType getHandledType();

	void pause();

	void resume();

	void flush();

}
