package net.npg.abattle.communication.service.common;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.service.common.MutableIntPoint;

@TransferData
@SuppressWarnings("all")
public class GameParameterInfo {
  public int maxCellStrength;
  
  public int maxCellHeight;
  
  public int maxMovement;
  
  public MutableIntPoint size;
  
  public GameParameterInfo(final int maxCellStrength, final int maxCellHeight, final int maxMovement, final MutableIntPoint size) {
    this.maxCellStrength = maxCellStrength;
    this.maxCellHeight = maxCellHeight;
    this.maxMovement = maxMovement;
    this.size = size;
  }
  
  public GameParameterInfo() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("maxCellStrength",maxCellStrength)
    .add("maxCellHeight",maxCellHeight)
    .add("maxMovement",maxMovement)
    .add("size",size)
    .addValue(super.toString())
    .toString();
  }
}
