package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.ChangeLinkCommand;
import net.npg.abattle.communication.service.common.MutableIntPoint;

@SuppressWarnings("all")
public class ChangeLinkCommandBuilder {
  private MutableIntPoint endCell;
  
  public ChangeLinkCommandBuilder endCell(final MutableIntPoint endCell) {
    this.endCell=endCell;
    return this;
  }
  
  private MutableIntPoint startCell;
  
  public ChangeLinkCommandBuilder startCell(final MutableIntPoint startCell) {
    this.startCell=startCell;
    return this;
  }
  
  private boolean create;
  
  public ChangeLinkCommandBuilder create(final boolean create) {
    this.create=create;
    return this;
  }
  
  private int originatedPlayer;
  
  public ChangeLinkCommandBuilder originatedPlayer(final int originatedPlayer) {
    this.originatedPlayer=originatedPlayer;
    return this;
  }
  
  private boolean dropable;
  
  public ChangeLinkCommandBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public ChangeLinkCommandBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public ChangeLinkCommand build() {
    return new ChangeLinkCommand(
    endCell,startCell,create,originatedPlayer,dropable,game
    );
  }
}
