package net.npg.abattle.server.logic;

import net.npg.abattle.server.model.ServerCell;

@SuppressWarnings("all")
public class SimpleMoveDistribution {
  public ServerCell destinationCell;
  
  public boolean hasFight;
  
  public int maxMoveArmiesTo;
  
  public SimpleMoveDistribution(final int maxMoveArmiesTo, final ServerCell destinationCell, final boolean hasFight) {
    this.maxMoveArmiesTo = maxMoveArmiesTo;
    this.destinationCell = destinationCell;
    this.hasFight = hasFight;
  }
}
