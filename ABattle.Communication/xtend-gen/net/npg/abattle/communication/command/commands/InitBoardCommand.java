package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.commands.CommandImpl;
import net.npg.abattle.communication.command.data.InitBoardData;

@TransferData
@SuppressWarnings("all")
public class InitBoardCommand extends CommandImpl {
  public InitBoardData initBoard;
  
  public InitBoardCommand(final InitBoardData initBoard, final boolean dropable, final int game) {
    this.initBoard = initBoard;
    this.dropable = dropable;
    this.game = game;
  }
  
  public InitBoardCommand() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("initBoard",initBoard)
    .add("dropable",dropable)
    .add("game",game)
    .addValue(super.toString())
    .toString();
  }
}
