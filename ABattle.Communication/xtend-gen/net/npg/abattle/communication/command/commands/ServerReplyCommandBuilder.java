package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.ServerReplyCommand;
import net.npg.abattle.communication.service.common.BooleanResult;

@SuppressWarnings("all")
public class ServerReplyCommandBuilder {
  private BooleanResult result;
  
  public ServerReplyCommandBuilder result(final BooleanResult result) {
    this.result=result;
    return this;
  }
  
  private int destinationId;
  
  public ServerReplyCommandBuilder destinationId(final int destinationId) {
    this.destinationId=destinationId;
    return this;
  }
  
  private boolean dropable;
  
  public ServerReplyCommandBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public ServerReplyCommandBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public ServerReplyCommand build() {
    return new ServerReplyCommand(
    result,destinationId,dropable,game
    );
  }
}
