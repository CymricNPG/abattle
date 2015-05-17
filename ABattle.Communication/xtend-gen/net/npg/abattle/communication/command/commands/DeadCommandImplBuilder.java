package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.DeadCommandImpl;

@SuppressWarnings("all")
public class DeadCommandImplBuilder {
  private String errorMessage;
  
  public DeadCommandImplBuilder errorMessage(final String errorMessage) {
    this.errorMessage=errorMessage;
    return this;
  }
  
  private boolean dropable;
  
  public DeadCommandImplBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public DeadCommandImplBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public DeadCommandImpl build() {
    return new DeadCommandImpl(
    errorMessage,dropable,game
    );
  }
}
