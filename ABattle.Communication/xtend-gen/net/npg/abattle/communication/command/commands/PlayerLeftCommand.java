package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.commands.CommandImpl;
import net.npg.abattle.communication.command.data.PlayerData;

@TransferData
@SuppressWarnings("all")
public class PlayerLeftCommand extends CommandImpl {
  public PlayerData player;
  
  public PlayerLeftCommand(final PlayerData player, final boolean dropable, final int game) {
    this.player = player;
    this.dropable = dropable;
    this.game = game;
  }
  
  public PlayerLeftCommand() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("player",player)
    .add("dropable",dropable)
    .add("game",game)
    .addValue(super.toString())
    .toString();
  }
}
