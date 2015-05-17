package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.commands.CommandImpl;
import net.npg.abattle.communication.command.data.StatisticsData;

@TransferData
@SuppressWarnings("all")
public class GameUpdateCommand extends CommandImpl {
  public StatisticsData[] statistics;
  
  public GameUpdateCommand(final StatisticsData[] statistics, final boolean dropable, final int game) {
    this.statistics = statistics;
    this.dropable = dropable;
    this.game = game;
  }
  
  public GameUpdateCommand() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("statistics",statistics)
    .add("dropable",dropable)
    .add("game",game)
    .addValue(super.toString())
    .toString();
  }
}
