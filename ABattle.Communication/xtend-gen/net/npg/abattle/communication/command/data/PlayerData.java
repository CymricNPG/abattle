package net.npg.abattle.communication.command.data;

import net.npg.abattle.common.utils.TransferData;

@TransferData
@SuppressWarnings("all")
public class PlayerData {
  public int playerId;
  
  public String playerName;
  
  public int r;
  
  public int g;
  
  public int b;
  
  public PlayerData(final int playerId, final String playerName, final int r, final int g, final int b) {
    this.playerId = playerId;
    this.playerName = playerName;
    this.r = r;
    this.g = g;
    this.b = b;
  }
  
  public PlayerData() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("playerId",playerId)
    .add("playerName",playerName)
    .add("r",r)
    .add("g",g)
    .add("b",b)
    .addValue(super.toString())
    .toString();
  }
}
