package net.npg.abattle.communication.network.impl;

import com.esotericsoftware.kryonet.Connection;
import java.util.Collections;
import java.util.List;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandQueue;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.GameCommand;
import net.npg.abattle.communication.command.ReceiveHandler;

@SuppressWarnings("all")
public class GameCommandHandler implements ReceiveHandler<GameCommand> {
  private CommandQueue queue;
  
  public GameCommandHandler(final CommandQueue queue) {
    Validate.notNulls(queue);
    this.queue = queue;
  }
  
  @Override
  public void handle(final GameCommand command, final Connection connection) {
    Validate.notNulls(command);
    CommandType _handledType = this.queue.getHandledType();
    List<Integer> _emptyList = Collections.<Integer>emptyList();
    this.queue.addCommand(command, _handledType, _emptyList);
  }
  
  @Override
  public boolean canHandle(final Object object) {
    return (object instanceof GameCommand);
  }
}
