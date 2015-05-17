package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.commands.CommandImpl;

@TransferData
@SuppressWarnings("all")
public class OriginatedPlayerCommand extends CommandImpl {
  public int originatedPlayer;
  
  public OriginatedPlayerCommand(final int originatedPlayer, final boolean dropable, final int game) {
    this.originatedPlayer = originatedPlayer;
    this.dropable = dropable;
    this.game = game;
  }
  
  public OriginatedPlayerCommand() {
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
