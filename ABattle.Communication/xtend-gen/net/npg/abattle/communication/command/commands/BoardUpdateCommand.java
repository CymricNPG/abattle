package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.commands.CommandImpl;
import net.npg.abattle.communication.command.data.BoardUpdateData;

@TransferData
@SuppressWarnings("all")
public class BoardUpdateCommand extends CommandImpl {
  public BoardUpdateData boardUpdate;
  
  public BoardUpdateCommand(final BoardUpdateData boardUpdate, final boolean dropable, final int game) {
    this.boardUpdate = boardUpdate;
    this.dropable = dropable;
    this.game = game;
  }
  
  public BoardUpdateCommand() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("boardUpdate",boardUpdate)
    .add("dropable",dropable)
    .add("game",game)
    .addValue(super.toString())
    .toString();
  }
}
