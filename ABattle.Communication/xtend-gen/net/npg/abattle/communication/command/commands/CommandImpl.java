package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.GameCommand;

@TransferData
@SuppressWarnings("all")
public class CommandImpl implements GameCommand {
  public boolean dropable;
  
  public int game;
  
  @Override
  public int getGame() {
    return this.game;
  }
  
  @Override
  public boolean isDropable() {
    return this.dropable;
  }
  
  public CommandImpl(final boolean dropable, final int game) {
    this.dropable = dropable;
    this.game = game;
  }
  
  public CommandImpl() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("dropable",dropable)
    .add("game",game)
    .addValue(super.toString())
    .toString();
  }
}
