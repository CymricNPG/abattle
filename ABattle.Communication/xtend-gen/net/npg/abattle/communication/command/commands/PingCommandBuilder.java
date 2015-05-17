package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.PingCommand;

@SuppressWarnings("all")
public class PingCommandBuilder {
  private boolean dropable;
  
  public PingCommandBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public PingCommandBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public PingCommand build() {
    return new PingCommand(
    dropable,game
    );
  }
}
