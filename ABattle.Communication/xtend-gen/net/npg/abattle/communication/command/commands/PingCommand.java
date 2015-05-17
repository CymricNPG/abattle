package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.commands.CommandImpl;

@TransferData
@SuppressWarnings("all")
public class PingCommand extends CommandImpl {
  public PingCommand(final boolean dropable, final int game) {
    this.dropable = dropable;
    this.game = game;
  }
  
  public PingCommand() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("dropable",dropable)
    .add("game",game)
    .addValue(super.toString())
    .toString();
  }
}
