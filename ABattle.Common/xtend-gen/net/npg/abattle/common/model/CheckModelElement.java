package net.npg.abattle.common.model;

import net.npg.abattle.common.utils.IntPoint;

@SuppressWarnings("all")
public interface CheckModelElement {
  public abstract void checkCoordinate(final IntPoint coordinate);
  
  public abstract void checkStrength(final int strength);
  
  public abstract void checkHeight(final int height);
}
