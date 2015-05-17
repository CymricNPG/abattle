package net.npg.abattle.communication.command.data;

import net.npg.abattle.communication.command.data.StatisticsData;

@SuppressWarnings("all")
public class StatisticsDataBuilder {
  private int playerId;
  
  public StatisticsDataBuilder playerId(final int playerId) {
    this.playerId=playerId;
    return this;
  }
  
  private int strength;
  
  public StatisticsDataBuilder strength(final int strength) {
    this.strength=strength;
    return this;
  }
  
  public StatisticsData build() {
    return new StatisticsData(
    playerId,strength
    );
  }
}
