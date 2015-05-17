package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.GameUpdateCommand;
import net.npg.abattle.communication.command.data.StatisticsData;

@SuppressWarnings("all")
public class GameUpdateCommandBuilder {
  private StatisticsData[] statistics;
  
  public GameUpdateCommandBuilder statistics(final StatisticsData[] statistics) {
    this.statistics=statistics;
    return this;
  }
  
  private boolean dropable;
  
  public GameUpdateCommandBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public GameUpdateCommandBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public GameUpdateCommand build() {
    return new GameUpdateCommand(
    statistics,dropable,game
    );
  }
}
