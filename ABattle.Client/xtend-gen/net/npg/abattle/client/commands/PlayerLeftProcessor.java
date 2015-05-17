package net.npg.abattle.client.commands;

import com.google.common.base.Optional;
import net.npg.abattle.client.commands.UpdatePlayerList;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandProcessor;
import net.npg.abattle.communication.command.ErrorCommand;
import net.npg.abattle.communication.command.commands.PlayerLeftCommand;

@SuppressWarnings("all")
public class PlayerLeftProcessor implements CommandProcessor<PlayerLeftCommand> {
  private ClientGame game;
  
  public PlayerLeftProcessor(final ClientGame game) {
    Validate.notNulls(game);
    this.game = game;
  }
  
  @Override
  public Optional<ErrorCommand> execute(final PlayerLeftCommand command, final int destination) {
    Optional<ErrorCommand> _xblockexpression = null;
    {
      Validate.notNull(command);
      UpdatePlayerList.remove(command.player, this.game);
      _xblockexpression = Optional.<ErrorCommand>absent();
    }
    return _xblockexpression;
  }
}
