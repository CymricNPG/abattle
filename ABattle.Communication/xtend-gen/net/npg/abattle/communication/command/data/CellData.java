package net.npg.abattle.communication.command.data;

import net.npg.abattle.common.utils.TransferData;

@TransferData
@SuppressWarnings("all")
public class CellData {
  public int id;
  
  public int cellType;
  
  public int height;
  
  public CellData(final int id, final int cellType, final int height) {
    this.id = id;
    this.cellType = cellType;
    this.height = height;
  }
  
  public CellData() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("id",id)
    .add("cellType",cellType)
    .add("height",height)
    .addValue(super.toString())
    .toString();
  }
}
