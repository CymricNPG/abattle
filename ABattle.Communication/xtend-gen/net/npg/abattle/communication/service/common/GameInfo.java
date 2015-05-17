package net.npg.abattle.communication.service.common;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.service.common.ClientInfo;
import net.npg.abattle.communication.service.common.GameParameterInfo;

@TransferData
@SuppressWarnings("all")
public class GameInfo {
  /**
   * The current players.
   */
  public int currentPlayers;
  
  /**
   * The max players.
   */
  public int maxPlayers;
  
  /**
   * The players.
   */
  public ClientInfo[] players;
  
  public GameParameterInfo parameters;
  
  public GameInfo(final int currentPlayers, final int maxPlayers, final ClientInfo[] players, final GameParameterInfo parameters) {
    this.currentPlayers = currentPlayers;
    this.maxPlayers = maxPlayers;
    this.players = players;
    this.parameters = parameters;
  }
  
  public GameInfo() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("currentPlayers",currentPlayers)
    .add("maxPlayers",maxPlayers)
    .add("players",players)
    .add("parameters",parameters)
    .addValue(super.toString())
    .toString();
  }
}
