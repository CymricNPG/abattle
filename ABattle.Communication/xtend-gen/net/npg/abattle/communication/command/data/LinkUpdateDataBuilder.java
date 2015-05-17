package net.npg.abattle.communication.command.data;

import net.npg.abattle.communication.command.data.LinkUpdateData;

@SuppressWarnings("all")
public class LinkUpdateDataBuilder {
  private int id;
  
  public LinkUpdateDataBuilder id(final int id) {
    this.id=id;
    return this;
  }
  
  private int startCellId;
  
  public LinkUpdateDataBuilder startCellId(final int startCellId) {
    this.startCellId=startCellId;
    return this;
  }
  
  private int endCellId;
  
  public LinkUpdateDataBuilder endCellId(final int endCellId) {
    this.endCellId=endCellId;
    return this;
  }
  
  public LinkUpdateData build() {
    return new LinkUpdateData(
    id,startCellId,endCellId
    );
  }
}
