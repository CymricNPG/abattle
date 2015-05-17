package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.CommandImpl;

@SuppressWarnings("all")
public class CommandImplBuilder {
  private boolean dropable;
  
  public CommandImplBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public CommandImplBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public CommandImpl build() {
    return new CommandImpl(
    dropable,game
    );
  }
}
