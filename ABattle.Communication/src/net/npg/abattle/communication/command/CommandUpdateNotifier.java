/**
 * 
 */
package net.npg.abattle.communication.command;


/**
 * @author Cymric
 * 
 */
public interface CommandUpdateNotifier {
	void receivedCommand(GameCommand command);
}
