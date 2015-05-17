package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.commands.OriginatedPlayerCommand;

@TransferData
@SuppressWarnings("all")
public class LeaveCommand extends OriginatedPlayerCommand {
  public LeaveCommand(final int originatedPlayer, final boolean dropable, final int game) {
    this.originatedPlayer = originatedPlayer;
    this.dropable = dropable;
    this.game = game;
  }
  
  public LeaveCommand() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("originatedPlayer",originatedPlayer)
    .add("dropable",dropable)
    .add("game",game)
    .addValue(super.toString())
    .toString();
  }
}
