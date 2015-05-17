package net.npg.abattle.communication.command.data;

import net.npg.abattle.common.utils.TransferData;

@TransferData
@SuppressWarnings("all")
public class CellUpdateData {
  public int id;
  
  public int owner;
  
  public boolean battle;
  
  public int strength;
  
  public CellUpdateData(final int id, final int owner, final boolean battle, final int strength) {
    this.id = id;
    this.owner = owner;
    this.battle = battle;
    this.strength = strength;
  }
  
  public CellUpdateData() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("id",id)
    .add("owner",owner)
    .add("battle",battle)
    .add("strength",strength)
    .addValue(super.toString())
    .toString();
  }
}
