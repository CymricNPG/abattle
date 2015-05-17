package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.commands.CommandImpl;
import net.npg.abattle.communication.command.data.CellUpdateData;
import net.npg.abattle.communication.command.data.PlayerData;

@TransferData
@SuppressWarnings("all")
public class GameStartCommand extends CommandImpl {
  public PlayerData[] players;
  
  public CellUpdateData[][] cellUpdates;
  
  public GameStartCommand(final PlayerData[] players, final CellUpdateData[][] cellUpdates, final boolean dropable, final int game) {
    this.players = players;
    this.cellUpdates = cellUpdates;
    this.dropable = dropable;
    this.game = game;
  }
  
  public GameStartCommand() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("players",players)
    .add("cellUpdates",cellUpdates)
    .add("dropable",dropable)
    .add("game",game)
    .addValue(super.toString())
    .toString();
  }
}
