package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.commands.CommandImpl;
import net.npg.abattle.communication.command.data.PlayerData;

@TransferData
@SuppressWarnings("all")
public class GameCountdownCommand extends CommandImpl {
  public PlayerData[] players;
  
  public int remainingCount;
  
  public GameCountdownCommand(final PlayerData[] players, final int remainingCount, final boolean dropable, final int game) {
    this.players = players;
    this.remainingCount = remainingCount;
    this.dropable = dropable;
    this.game = game;
  }
  
  public GameCountdownCommand() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("players",players)
    .add("remainingCount",remainingCount)
    .add("dropable",dropable)
    .add("game",game)
    .addValue(super.toString())
    .toString();
  }
}
