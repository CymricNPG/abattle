package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.GameCountdownCommand;
import net.npg.abattle.communication.command.data.PlayerData;

@SuppressWarnings("all")
public class GameCountdownCommandBuilder {
  private PlayerData[] players;
  
  public GameCountdownCommandBuilder players(final PlayerData[] players) {
    this.players=players;
    return this;
  }
  
  private int remainingCount;
  
  public GameCountdownCommandBuilder remainingCount(final int remainingCount) {
    this.remainingCount=remainingCount;
    return this;
  }
  
  private boolean dropable;
  
  public GameCountdownCommandBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public GameCountdownCommandBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public GameCountdownCommand build() {
    return new GameCountdownCommand(
    players,remainingCount,dropable,game
    );
  }
}
