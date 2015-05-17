package net.npg.abattle.server.game.impl;

import java.util.Collection;
import java.util.List;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.commands.GameUpdateCommand;
import net.npg.abattle.communication.command.data.StatisticsData;
import net.npg.abattle.server.game.GameEnvironment;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerPlayer;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class StatisticsSender {
  private GameEnvironment game;
  
  private CommandQueueServer commandQueue;
  
  public StatisticsSender(final GameEnvironment game, final CommandQueueServer commandQueue) {
    Validate.notNull(game);
    Validate.notNull(commandQueue);
    this.game = game;
    this.commandQueue = commandQueue;
  }
  
  public void sendGameData() {
    ServerGame _serverGame = this.game.getServerGame();
    Collection<ServerPlayer> _players = _serverGame.getPlayers();
    final Function1<ServerPlayer, StatisticsData> _function = new Function1<ServerPlayer, StatisticsData>() {
      @Override
      public StatisticsData apply(final ServerPlayer it) {
        int _id = it.getId();
        int _strength = it.getStrength();
        return new StatisticsData(_id, _strength);
      }
    };
    Iterable<StatisticsData> _map = IterableExtensions.<ServerPlayer, StatisticsData>map(_players, _function);
    final List<StatisticsData> statistics = IterableExtensions.<StatisticsData>toList(_map);
    ServerGame _serverGame_1 = this.game.getServerGame();
    Collection<ServerPlayer> _players_1 = _serverGame_1.getPlayers();
    final Function1<ServerPlayer, Integer> _function_1 = new Function1<ServerPlayer, Integer>() {
      @Override
      public Integer apply(final ServerPlayer it) {
        return Integer.valueOf(it.getId());
      }
    };
    Iterable<Integer> _map_1 = IterableExtensions.<ServerPlayer, Integer>map(_players_1, _function_1);
    final List<Integer> destinations = IterableExtensions.<Integer>toList(_map_1);
    int _id = this.game.getId();
    final GameUpdateCommand data = new GameUpdateCommand(((StatisticsData[])Conversions.unwrapArray(statistics, StatisticsData.class)), true, _id);
    this.commandQueue.addCommand(data, CommandType.TOCLIENT, destinations);
  }
}
