package net.npg.abattle.communication.service.common;

import net.npg.abattle.communication.service.common.ClientInfo;
import net.npg.abattle.communication.service.common.GameInfo;
import net.npg.abattle.communication.service.common.GameParameterInfo;

@SuppressWarnings("all")
public class GameInfoBuilder {
  private int currentPlayers;
  
  public GameInfoBuilder currentPlayers(final int currentPlayers) {
    this.currentPlayers=currentPlayers;
    return this;
  }
  
  private int maxPlayers;
  
  public GameInfoBuilder maxPlayers(final int maxPlayers) {
    this.maxPlayers=maxPlayers;
    return this;
  }
  
  private ClientInfo[] players;
  
  public GameInfoBuilder players(final ClientInfo[] players) {
    this.players=players;
    return this;
  }
  
  private GameParameterInfo parameters;
  
  public GameInfoBuilder parameters(final GameParameterInfo parameters) {
    this.parameters=parameters;
    return this;
  }
  
  public GameInfo build() {
    return new GameInfo(
    currentPlayers,maxPlayers,players,parameters
    );
  }
}
