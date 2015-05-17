package net.npg.abattle.client.commands;

import com.google.common.base.Optional;
import net.npg.abattle.client.ClientConstants;
import net.npg.abattle.communication.command.CommandProcessor;
import net.npg.abattle.communication.command.ErrorCommand;
import net.npg.abattle.communication.command.commands.ServerReplyCommand;

@SuppressWarnings("all")
public class ServerReplyProcessor implements CommandProcessor<ServerReplyCommand> {
  @Override
  public Optional<ErrorCommand> execute(final ServerReplyCommand command, final int destination) {
    Optional<ErrorCommand> _xblockexpression = null;
    {
      ClientConstants.LOG.info(("Message from server:" + command.result.errorMessage));
      _xblockexpression = Optional.<ErrorCommand>absent();
    }
    return _xblockexpression;
  }
}
