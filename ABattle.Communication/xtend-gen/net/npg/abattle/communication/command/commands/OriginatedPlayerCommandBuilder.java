package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.OriginatedPlayerCommand;

@SuppressWarnings("all")
public class OriginatedPlayerCommandBuilder {
  private int originatedPlayer;
  
  public OriginatedPlayerCommandBuilder originatedPlayer(final int originatedPlayer) {
    this.originatedPlayer=originatedPlayer;
    return this;
  }
  
  private boolean dropable;
  
  public OriginatedPlayerCommandBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public OriginatedPlayerCommandBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public OriginatedPlayerCommand build() {
    return new OriginatedPlayerCommand(
    originatedPlayer,dropable,game
    );
  }
}
