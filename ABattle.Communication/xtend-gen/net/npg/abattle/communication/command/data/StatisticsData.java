package net.npg.abattle.communication.command.data;

import net.npg.abattle.common.utils.TransferData;

@TransferData
@SuppressWarnings("all")
public class StatisticsData {
  public int playerId;
  
  public int strength;
  
  public StatisticsData(final int playerId, final int strength) {
    this.playerId = playerId;
    this.strength = strength;
  }
  
  public StatisticsData() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("playerId",playerId)
    .add("strength",strength)
    .addValue(super.toString())
    .toString();
  }
}
