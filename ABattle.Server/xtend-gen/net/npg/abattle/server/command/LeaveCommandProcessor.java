package net.npg.abattle.server.command;

import com.google.common.base.Optional;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandProcessor;
import net.npg.abattle.communication.command.ErrorCommand;
import net.npg.abattle.communication.command.commands.LeaveCommand;
import net.npg.abattle.communication.service.ServerService;

@SuppressWarnings("all")
public class LeaveCommandProcessor implements CommandProcessor<LeaveCommand> {
  private final ServerService service;
  
  public LeaveCommandProcessor(final ServerService service) {
    Validate.notNull(service);
    this.service = service;
  }
  
  @Override
  public Optional<ErrorCommand> execute(final LeaveCommand command, final int destination) {
    Validate.notNull(command);
    this.service.leaveGame(command.game, command.originatedPlayer);
    return Optional.<ErrorCommand>absent();
  }
}
