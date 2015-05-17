package net.npg.abattle.server.game.impl;

import java.util.Collection;
import java.util.List;
import net.npg.abattle.common.utils.MyRunnable;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandQueue;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.commands.PingCommand;
import net.npg.abattle.server.game.GameEnvironment;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerPlayer;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class PreGameThread extends MyRunnable {
  private CommandQueue commandQueue;
  
  private GameEnvironment game;
  
  public PreGameThread(final CommandQueue commandQueue, final GameEnvironment game) {
    super("PreGameThread");
    Validate.notNulls(commandQueue, game);
    this.commandQueue = commandQueue;
    this.game = game;
  }
  
  @Override
  public void execute() throws Exception {
    while ((((!this.isStopped()) && (!this.game.isDisposed())) && (!this.game.getServerGame().getStatus().isRunning()))) {
      {
        this.sendPing();
        Thread.sleep(1000L);
        this.lifeCycleWait();
      }
    }
  }
  
  public void sendPing() {
    ServerGame _serverGame = this.game.getServerGame();
    Collection<ServerPlayer> _players = _serverGame.getPlayers();
    final Function1<ServerPlayer, Integer> _function = new Function1<ServerPlayer, Integer>() {
      @Override
      public Integer apply(final ServerPlayer it) {
        return Integer.valueOf(it.getId());
      }
    };
    Iterable<Integer> _map = IterableExtensions.<ServerPlayer, Integer>map(_players, _function);
    final List<Integer> destinationIds = IterableExtensions.<Integer>toList(_map);
    PingCommand _pingCommand = new PingCommand();
    this.commandQueue.addCommand(_pingCommand, CommandType.TOCLIENT, destinationIds);
  }
}
