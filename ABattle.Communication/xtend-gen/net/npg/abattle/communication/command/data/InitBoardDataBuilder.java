package net.npg.abattle.communication.command.data;

import net.npg.abattle.communication.command.data.CellData;
import net.npg.abattle.communication.command.data.InitBoardData;

@SuppressWarnings("all")
public class InitBoardDataBuilder {
  private CellData[][] cells;
  
  public InitBoardDataBuilder cells(final CellData[][] cells) {
    this.cells=cells;
    return this;
  }
  
  public InitBoardData build() {
    return new InitBoardData(
    cells
    );
  }
}
