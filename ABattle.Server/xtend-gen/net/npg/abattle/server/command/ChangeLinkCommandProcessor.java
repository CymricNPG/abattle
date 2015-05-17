package net.npg.abattle.server.command;

import com.google.common.base.Optional;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandProcessor;
import net.npg.abattle.communication.command.ErrorCommand;
import net.npg.abattle.communication.command.commands.ChangeLinkCommand;
import net.npg.abattle.communication.command.commands.ServerReplyCommand;
import net.npg.abattle.communication.service.ServerService;
import net.npg.abattle.communication.service.common.BooleanResult;

@SuppressWarnings("all")
public class ChangeLinkCommandProcessor implements CommandProcessor<ChangeLinkCommand> {
  private final ServerService service;
  
  public ChangeLinkCommandProcessor(final ServerService service) {
    Validate.notNull(service);
    this.service = service;
  }
  
  @Override
  public Optional<ErrorCommand> execute(final ChangeLinkCommand command, final int destination) {
    Validate.notNull(command);
    final BooleanResult result = this.service.link(command.game, command.originatedPlayer, command.startCell, command.endCell, command.create);
    if ((!result.success)) {
      final ServerReplyCommand reply = new ServerReplyCommand(result, command.originatedPlayer, true, command.game);
      return Optional.<ErrorCommand>of(reply);
    } else {
      return Optional.<ErrorCommand>absent();
    }
  }
}
