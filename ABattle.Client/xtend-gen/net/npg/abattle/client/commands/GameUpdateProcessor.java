package net.npg.abattle.client.commands;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import java.util.Collection;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandProcessor;
import net.npg.abattle.communication.command.ErrorCommand;
import net.npg.abattle.communication.command.commands.GameUpdateCommand;
import net.npg.abattle.communication.command.data.StatisticsData;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class GameUpdateProcessor implements CommandProcessor<GameUpdateCommand> {
  private ClientGame game;
  
  public GameUpdateProcessor(final ClientGame game) {
    Validate.notNull(game);
    this.game = game;
  }
  
  @Override
  public Optional<ErrorCommand> execute(final GameUpdateCommand command, final int destination) {
    Optional<ErrorCommand> _xblockexpression = null;
    {
      Validate.notNull(command);
      for (final StatisticsData statistic : command.statistics) {
        {
          Collection<ClientPlayer> _players = this.game.getPlayers();
          final Function1<ClientPlayer, Boolean> _function = new Function1<ClientPlayer, Boolean>() {
            @Override
            public Boolean apply(final ClientPlayer it) {
              int _id = it.getId();
              return Boolean.valueOf((_id == statistic.playerId));
            }
          };
          final ClientPlayer player = IterableExtensions.<ClientPlayer>findFirst(_players, _function);
          boolean _equals = Objects.equal(player, null);
          if (_equals) {
            throw new IllegalArgumentException(("Server send unknown player:" + Integer.valueOf(statistic.playerId)));
          }
          player.setStrength(statistic.strength);
        }
      }
      _xblockexpression = Optional.<ErrorCommand>absent();
    }
    return _xblockexpression;
  }
}
