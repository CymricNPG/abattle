package net.npg.abattle.communication.service.common;

import net.npg.abattle.communication.service.common.MutableIntPoint;

@SuppressWarnings("all")
public class MutableIntPointBuilder {
  private int x;
  
  public MutableIntPointBuilder x(final int x) {
    this.x=x;
    return this;
  }
  
  private int y;
  
  public MutableIntPointBuilder y(final int y) {
    this.y=y;
    return this;
  }
  
  public MutableIntPoint build() {
    return new MutableIntPoint(
    x,y
    );
  }
}
