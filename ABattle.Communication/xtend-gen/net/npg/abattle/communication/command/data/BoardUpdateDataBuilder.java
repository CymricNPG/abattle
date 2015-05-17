package net.npg.abattle.communication.command.data;

import net.npg.abattle.communication.command.data.BoardUpdateData;
import net.npg.abattle.communication.command.data.CellUpdateData;
import net.npg.abattle.communication.command.data.LinkUpdateData;

@SuppressWarnings("all")
public class BoardUpdateDataBuilder {
  private CellUpdateData[][] cellUpdates;
  
  public BoardUpdateDataBuilder cellUpdates(final CellUpdateData[][] cellUpdates) {
    this.cellUpdates=cellUpdates;
    return this;
  }
  
  private LinkUpdateData[] linkUpdates;
  
  public BoardUpdateDataBuilder linkUpdates(final LinkUpdateData[] linkUpdates) {
    this.linkUpdates=linkUpdates;
    return this;
  }
  
  public BoardUpdateData build() {
    return new BoardUpdateData(
    cellUpdates,linkUpdates
    );
  }
}
