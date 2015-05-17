package net.npg.abattle.server.service.impl;

import java.util.Collection;
import java.util.List;
import net.npg.abattle.common.utils.MyRunnable;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.commands.GameCountdownCommand;
import net.npg.abattle.communication.command.data.PlayerData;
import net.npg.abattle.server.ServerConstants;
import net.npg.abattle.server.game.GameEnvironment;
import net.npg.abattle.server.game.impl.DataHelper;
import net.npg.abattle.server.game.impl.InitGameSender;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerPlayer;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * @author Cymric
 */
@SuppressWarnings("all")
public class StartGameThread extends MyRunnable {
  private final GameEnvironment game;
  
  private CommandQueueServer commandQueue;
  
  public StartGameThread(final GameEnvironment game, final CommandQueueServer commandQueue) {
    super("StartGameThread");
    Validate.notNulls(game, commandQueue);
    this.game = game;
    this.commandQueue = commandQueue;
    game.attachThread(this);
  }
  
  @Override
  public void execute() throws Exception {
    try {
      for (int i = 3; (i > 0); i--) {
        {
          this.sendCountdown(i);
          for (int t = 0; (t < 10); t++) {
            {
              boolean _isStopped = this.isStopped();
              if(_isStopped) return;;
              Thread.sleep(100);
            }
          }
        }
      }
      ServerGame _serverGame = this.game.getServerGame();
      InitGameSender _initGameSender = new InitGameSender(this.game, this.commandQueue);
      _serverGame.startGame(_initGameSender);
      this.game.startGame(this.commandQueue);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        String _message = e.getMessage();
        ServerConstants.LOG.error(_message, e);
        ServerGame _serverGame_1 = this.game.getServerGame();
        _serverGame_1.stopGame();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  private void sendCountdown(final int i) {
    final PlayerData[] playersData = DataHelper.createPlayerData(this.game);
    int _id = this.game.getId();
    final GameCountdownCommand command = new GameCountdownCommand(playersData, i, true, _id);
    ServerGame _serverGame = this.game.getServerGame();
    Collection<ServerPlayer> _players = _serverGame.getPlayers();
    final Function1<ServerPlayer, Integer> _function = new Function1<ServerPlayer, Integer>() {
      @Override
      public Integer apply(final ServerPlayer it) {
        return Integer.valueOf(it.getId());
      }
    };
    Iterable<Integer> _map = IterableExtensions.<ServerPlayer, Integer>map(_players, _function);
    final List<Integer> destinations = IterableExtensions.<Integer>toList(_map);
    this.commandQueue.addCommand(command, CommandType.TOCLIENT, destinations);
  }
}
