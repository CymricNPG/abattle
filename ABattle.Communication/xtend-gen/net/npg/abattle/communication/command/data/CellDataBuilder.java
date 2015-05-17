package net.npg.abattle.communication.command.data;

import net.npg.abattle.communication.command.data.CellData;

@SuppressWarnings("all")
public class CellDataBuilder {
  private int id;
  
  public CellDataBuilder id(final int id) {
    this.id=id;
    return this;
  }
  
  private int cellType;
  
  public CellDataBuilder cellType(final int cellType) {
    this.cellType=cellType;
    return this;
  }
  
  private int height;
  
  public CellDataBuilder height(final int height) {
    this.height=height;
    return this;
  }
  
  public CellData build() {
    return new CellData(
    id,cellType,height
    );
  }
}
