package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.commands.CommandImpl;

@TransferData
@SuppressWarnings("all")
public class GameFinishedCommand extends CommandImpl {
  public GameFinishedCommand(final boolean dropable, final int game) {
    this.dropable = dropable;
    this.game = game;
  }
  
  public GameFinishedCommand() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("dropable",dropable)
    .add("game",game)
    .addValue(super.toString())
    .toString();
  }
}
