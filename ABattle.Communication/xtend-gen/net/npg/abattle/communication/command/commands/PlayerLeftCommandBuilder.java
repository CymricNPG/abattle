package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.PlayerLeftCommand;
import net.npg.abattle.communication.command.data.PlayerData;

@SuppressWarnings("all")
public class PlayerLeftCommandBuilder {
  private PlayerData player;
  
  public PlayerLeftCommandBuilder player(final PlayerData player) {
    this.player=player;
    return this;
  }
  
  private boolean dropable;
  
  public PlayerLeftCommandBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public PlayerLeftCommandBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public PlayerLeftCommand build() {
    return new PlayerLeftCommand(
    player,dropable,game
    );
  }
}
