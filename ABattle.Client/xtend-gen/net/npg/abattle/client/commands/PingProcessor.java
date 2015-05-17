package net.npg.abattle.client.commands;

import com.google.common.base.Optional;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandProcessor;
import net.npg.abattle.communication.command.CommandUpdateNotifier;
import net.npg.abattle.communication.command.ErrorCommand;
import net.npg.abattle.communication.command.commands.PingCommand;

@SuppressWarnings("all")
public class PingProcessor implements CommandProcessor<PingCommand> {
  private ClientGame game;
  
  private CommandUpdateNotifier notifier;
  
  public PingProcessor(final ClientGame game, final CommandUpdateNotifier notifier) {
    Validate.notNulls(game, notifier);
    this.game = game;
    this.notifier = notifier;
  }
  
  @Override
  public Optional<ErrorCommand> execute(final PingCommand command, final int destination) {
    Optional<ErrorCommand> _xblockexpression = null;
    {
      Validate.notNull(command);
      _xblockexpression = Optional.<ErrorCommand>absent();
    }
    return _xblockexpression;
  }
}
