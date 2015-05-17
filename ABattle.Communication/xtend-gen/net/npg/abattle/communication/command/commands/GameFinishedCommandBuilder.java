package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.GameFinishedCommand;

@SuppressWarnings("all")
public class GameFinishedCommandBuilder {
  private boolean dropable;
  
  public GameFinishedCommandBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public GameFinishedCommandBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public GameFinishedCommand build() {
    return new GameFinishedCommand(
    dropable,game
    );
  }
}
