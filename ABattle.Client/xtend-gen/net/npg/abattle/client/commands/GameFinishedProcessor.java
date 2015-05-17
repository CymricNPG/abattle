package net.npg.abattle.client.commands;

import com.google.common.base.Optional;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandProcessor;
import net.npg.abattle.communication.command.ErrorCommand;
import net.npg.abattle.communication.command.commands.GameFinishedCommand;

@SuppressWarnings("all")
public class GameFinishedProcessor implements CommandProcessor<GameFinishedCommand> {
  private ClientGame game;
  
  public GameFinishedProcessor(final ClientGame game) {
    Validate.notNull(game);
    this.game = game;
  }
  
  @Override
  public Optional<ErrorCommand> execute(final GameFinishedCommand command, final int destination) {
    Optional<ErrorCommand> _xblockexpression = null;
    {
      Validate.notNull(command);
      this.game.stopGame();
      _xblockexpression = Optional.<ErrorCommand>absent();
    }
    return _xblockexpression;
  }
}
