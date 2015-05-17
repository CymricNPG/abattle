package net.npg.abattle.communication.command.data;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.data.CellData;

@TransferData
@SuppressWarnings("all")
public class InitBoardData {
  public CellData[][] cells;
  
  public InitBoardData(final CellData[][] cells) {
    this.cells = cells;
  }
  
  public InitBoardData() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("cells",cells)
    .addValue(super.toString())
    .toString();
  }
}
