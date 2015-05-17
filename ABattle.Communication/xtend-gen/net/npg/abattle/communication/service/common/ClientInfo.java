package net.npg.abattle.communication.service.common;

import net.npg.abattle.common.utils.TransferData;

@TransferData
@SuppressWarnings("all")
public class ClientInfo {
  /**
   * The id.
   */
  public int id;
  
  /**
   * The name.
   */
  public String name;
  
  public ClientInfo(final int id, final String name) {
    this.id = id;
    this.name = name;
  }
  
  public ClientInfo() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("id",id)
    .add("name",name)
    .addValue(super.toString())
    .toString();
  }
}
