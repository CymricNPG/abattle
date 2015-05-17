package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.commands.CommandImpl;
import net.npg.abattle.communication.command.data.PlayerData;

@TransferData
@SuppressWarnings("all")
public class PlayerJoinedCommand extends CommandImpl {
  public PlayerData[] players;
  
  public PlayerJoinedCommand(final PlayerData[] players, final boolean dropable, final int game) {
    this.players = players;
    this.dropable = dropable;
    this.game = game;
  }
  
  public PlayerJoinedCommand() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("players",players)
    .add("dropable",dropable)
    .add("game",game)
    .addValue(super.toString())
    .toString();
  }
}
