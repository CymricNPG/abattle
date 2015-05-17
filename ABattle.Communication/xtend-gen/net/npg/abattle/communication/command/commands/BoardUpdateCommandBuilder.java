package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.BoardUpdateCommand;
import net.npg.abattle.communication.command.data.BoardUpdateData;

@SuppressWarnings("all")
public class BoardUpdateCommandBuilder {
  private BoardUpdateData boardUpdate;
  
  public BoardUpdateCommandBuilder boardUpdate(final BoardUpdateData boardUpdate) {
    this.boardUpdate=boardUpdate;
    return this;
  }
  
  private boolean dropable;
  
  public BoardUpdateCommandBuilder dropable(final boolean dropable) {
    this.dropable=dropable;
    return this;
  }
  
  private int game;
  
  public BoardUpdateCommandBuilder game(final int game) {
    this.game=game;
    return this;
  }
  
  public BoardUpdateCommand build() {
    return new BoardUpdateCommand(
    boardUpdate,dropable,game
    );
  }
}
