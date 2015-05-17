package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.PlayerJoinedCommand;
import net.npg.abattle.communication.command.data.PlayerData;

@SuppressWarnings("all")
public class PlayerJoinedCommandBuilder {
  private PlayerData[] players;
  
  public PlayerJoinedCommandBuilder players(final PlayerData[] players) {
    this.players=players;
    return this;
  }
  
  private boolean dropable;
  
  public PlayerJoinedCommandBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public PlayerJoinedCommandBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public PlayerJoinedCommand build() {
    return new PlayerJoinedCommand(
    players,dropable,game
    );
  }
}
