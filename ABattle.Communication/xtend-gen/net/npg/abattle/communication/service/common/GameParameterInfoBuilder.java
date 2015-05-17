package net.npg.abattle.communication.service.common;

import net.npg.abattle.communication.service.common.GameParameterInfo;
import net.npg.abattle.communication.service.common.MutableIntPoint;

@SuppressWarnings("all")
public class GameParameterInfoBuilder {
  private int maxCellStrength;
  
  public GameParameterInfoBuilder maxCellStrength(final int maxCellStrength) {
    this.maxCellStrength=maxCellStrength;
    return this;
  }
  
  private int maxCellHeight;
  
  public GameParameterInfoBuilder maxCellHeight(final int maxCellHeight) {
    this.maxCellHeight=maxCellHeight;
    return this;
  }
  
  private int maxMovement;
  
  public GameParameterInfoBuilder maxMovement(final int maxMovement) {
    this.maxMovement=maxMovement;
    return this;
  }
  
  private MutableIntPoint size;
  
  public GameParameterInfoBuilder size(final MutableIntPoint size) {
    this.size=size;
    return this;
  }
  
  public GameParameterInfo build() {
    return new GameParameterInfo(
    maxCellStrength,maxCellHeight,maxMovement,size
    );
  }
}
