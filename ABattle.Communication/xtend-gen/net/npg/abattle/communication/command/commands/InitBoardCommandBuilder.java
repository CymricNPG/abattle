package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.InitBoardCommand;
import net.npg.abattle.communication.command.data.InitBoardData;

@SuppressWarnings("all")
public class InitBoardCommandBuilder {
  private InitBoardData initBoard;
  
  public InitBoardCommandBuilder initBoard(final InitBoardData initBoard) {
    this.initBoard=initBoard;
    return this;
  }
  
  private boolean dropable;
  
  public InitBoardCommandBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public InitBoardCommandBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public InitBoardCommand build() {
    return new InitBoardCommand(
    initBoard,dropable,game
    );
  }
}
