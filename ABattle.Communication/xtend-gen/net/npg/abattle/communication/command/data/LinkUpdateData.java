package net.npg.abattle.communication.command.data;

import net.npg.abattle.common.utils.TransferData;

@TransferData
@SuppressWarnings("all")
public class LinkUpdateData {
  public int id;
  
  public int startCellId;
  
  public int endCellId;
  
  public LinkUpdateData(final int id, final int startCellId, final int endCellId) {
    this.id = id;
    this.startCellId = startCellId;
    this.endCellId = endCellId;
  }
  
  public LinkUpdateData() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("id",id)
    .add("startCellId",startCellId)
    .add("endCellId",endCellId)
    .addValue(super.toString())
    .toString();
  }
}
