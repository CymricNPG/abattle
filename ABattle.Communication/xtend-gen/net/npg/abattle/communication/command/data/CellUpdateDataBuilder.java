package net.npg.abattle.communication.command.data;

import net.npg.abattle.communication.command.data.CellUpdateData;

@SuppressWarnings("all")
public class CellUpdateDataBuilder {
  private int id;
  
  public CellUpdateDataBuilder id(final int id) {
    this.id=id;
    return this;
  }
  
  private int owner;
  
  public CellUpdateDataBuilder owner(final int owner) {
    this.owner=owner;
    return this;
  }
  
  private boolean battle;
  
  public CellUpdateDataBuilder battle(final boolean battle) {
    this.battle=battle;
    return this;
  }
  
  private int strength;
  
  public CellUpdateDataBuilder strength(final int strength) {
    this.strength=strength;
    return this;
  }
  
  public CellUpdateData build() {
    return new CellUpdateData(
    id,owner,battle,strength
    );
  }
}
