package net.npg.abattle.communication.command.data;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.command.data.CellUpdateData;
import net.npg.abattle.communication.command.data.LinkUpdateData;

@TransferData
@SuppressWarnings("all")
public class BoardUpdateData {
  public CellUpdateData[][] cellUpdates;
  
  public LinkUpdateData[] linkUpdates;
  
  public BoardUpdateData(final CellUpdateData[][] cellUpdates, final LinkUpdateData[] linkUpdates) {
    this.cellUpdates = cellUpdates;
    this.linkUpdates = linkUpdates;
  }
  
  public BoardUpdateData() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("cellUpdates",cellUpdates)
    .add("linkUpdates",linkUpdates)
    .addValue(super.toString())
    .toString();
  }
}
