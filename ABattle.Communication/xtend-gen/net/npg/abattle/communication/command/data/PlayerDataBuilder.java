package net.npg.abattle.communication.command.data;

import net.npg.abattle.communication.command.data.PlayerData;

@SuppressWarnings("all")
public class PlayerDataBuilder {
  private int playerId;
  
  public PlayerDataBuilder playerId(final int playerId) {
    this.playerId=playerId;
    return this;
  }
  
  private String playerName;
  
  public PlayerDataBuilder playerName(final String playerName) {
    this.playerName=playerName;
    return this;
  }
  
  private int r;
  
  public PlayerDataBuilder r(final int r) {
    this.r=r;
    return this;
  }
  
  private int g;
  
  public PlayerDataBuilder g(final int g) {
    this.g=g;
    return this;
  }
  
  private int b;
  
  public PlayerDataBuilder b(final int b) {
    this.b=b;
    return this;
  }
  
  public PlayerData build() {
    return new PlayerData(
    playerId,playerName,r,g,b
    );
  }
}
