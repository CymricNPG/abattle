package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.LeaveCommand;

@SuppressWarnings("all")
public class LeaveCommandBuilder {
  private int originatedPlayer;
  
  public LeaveCommandBuilder originatedPlayer(final int originatedPlayer) {
    this.originatedPlayer=originatedPlayer;
    return this;
  }
  
  private boolean dropable;
  
  public LeaveCommandBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public LeaveCommandBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public LeaveCommand build() {
    return new LeaveCommand(
    originatedPlayer,dropable,game
    );
  }
}
