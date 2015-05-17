package net.npg.abattle.common.configuration;

import com.badlogic.gdx.Preferences;
import java.util.Map;
import java.util.Properties;
import net.npg.abattle.common.configuration.DirtyAccessors;
import net.npg.abattle.common.configuration.PropertyName;
import net.npg.abattle.common.configuration.PropertyStored;
import net.npg.abattle.common.configuration.SaveableData;
import org.eclipse.xtext.xbase.lib.Pure;

@PropertyStored
@SuppressWarnings("all")
public class GraphicsConfigurationData implements SaveableData {
  @PropertyName(name = "graphics.maxUnitSize", defaultValue = "0.8")
  @DirtyAccessors
  private float maxUnitSize;
  
  @PropertyName(name = "graphics.minUnitSize", defaultValue = "0.1")
  @DirtyAccessors
  private float minUnitSize;
  
  @PropertyName(name = "graphics.structureSize", defaultValue = "0.8")
  @DirtyAccessors
  private float structureSize;
  
  @PropertyName(name = "graphics.fight.radius", defaultValue = "0.8")
  @DirtyAccessors
  private float fightRadius;
  
  @PropertyName(name = "graphics.fight.cooldown", defaultValue = "20")
  @DirtyAccessors
  private int cooldownFrames;
  
  @PropertyName(name = "graphics.fight.bombAddTime", defaultValue = "200")
  @DirtyAccessors
  private int bombAddTime;
  
  @PropertyName(name = "graphics.fight.bombGrowTime", defaultValue = "40")
  @DirtyAccessors
  private int bombGrowTime;
  
  @PropertyName(name = "graphics.cellShading", defaultValue = "false")
  @DirtyAccessors
  private boolean cellShading;
  
  @PropertyName(name = "graphics.debugCell", defaultValue = "")
  @DirtyAccessors
  private String debugCell;
  
  public GraphicsConfigurationData(final Preferences properties) {
    maxUnitSize = net.npg.abattle.common.configuration.Helper.getfloat(properties,"graphics.maxUnitSize","0.8");
    minUnitSize = net.npg.abattle.common.configuration.Helper.getfloat(properties,"graphics.minUnitSize","0.1");
    structureSize = net.npg.abattle.common.configuration.Helper.getfloat(properties,"graphics.structureSize","0.8");
    fightRadius = net.npg.abattle.common.configuration.Helper.getfloat(properties,"graphics.fight.radius","0.8");
    cooldownFrames = net.npg.abattle.common.configuration.Helper.getint(properties,"graphics.fight.cooldown","20");
    bombAddTime = net.npg.abattle.common.configuration.Helper.getint(properties,"graphics.fight.bombAddTime","200");
    bombGrowTime = net.npg.abattle.common.configuration.Helper.getint(properties,"graphics.fight.bombGrowTime","40");
    cellShading = net.npg.abattle.common.configuration.Helper.getboolean(properties,"graphics.cellShading","false");
    debugCell = net.npg.abattle.common.configuration.Helper.getString(properties,"graphics.debugCell","");
    dirty = false;
  }
  
  public GraphicsConfigurationData() {
  }
  
  public GraphicsConfigurationData(final Properties properties) {
    maxUnitSize = net.npg.abattle.common.configuration.Helper.getfloat(properties,"graphics.maxUnitSize","0.8");
    minUnitSize = net.npg.abattle.common.configuration.Helper.getfloat(properties,"graphics.minUnitSize","0.1");
    structureSize = net.npg.abattle.common.configuration.Helper.getfloat(properties,"graphics.structureSize","0.8");
    fightRadius = net.npg.abattle.common.configuration.Helper.getfloat(properties,"graphics.fight.radius","0.8");
    cooldownFrames = net.npg.abattle.common.configuration.Helper.getint(properties,"graphics.fight.cooldown","20");
    bombAddTime = net.npg.abattle.common.configuration.Helper.getint(properties,"graphics.fight.bombAddTime","200");
    bombGrowTime = net.npg.abattle.common.configuration.Helper.getint(properties,"graphics.fight.bombGrowTime","40");
    cellShading = net.npg.abattle.common.configuration.Helper.getboolean(properties,"graphics.cellShading","false");
    debugCell = net.npg.abattle.common.configuration.Helper.getString(properties,"graphics.debugCell","");
    dirty = false;
  }
  
  public void save(final Map map) {
       map.put("graphics.maxUnitSize",maxUnitSize);
       map.put("graphics.minUnitSize",minUnitSize);
       map.put("graphics.structureSize",structureSize);
       map.put("graphics.fight.radius",fightRadius);
       map.put("graphics.fight.cooldown",cooldownFrames);
       map.put("graphics.fight.bombAddTime",bombAddTime);
       map.put("graphics.fight.bombGrowTime",bombGrowTime);
       map.put("graphics.cellShading",cellShading);
       map.put("graphics.debugCell",debugCell);
    dirty = false;
  }
  
  public void reset() {
    maxUnitSize = net.npg.abattle.common.configuration.Helper.getfloat("0.8");
    minUnitSize = net.npg.abattle.common.configuration.Helper.getfloat("0.1");
    structureSize = net.npg.abattle.common.configuration.Helper.getfloat("0.8");
    fightRadius = net.npg.abattle.common.configuration.Helper.getfloat("0.8");
    cooldownFrames = net.npg.abattle.common.configuration.Helper.getint("20");
    bombAddTime = net.npg.abattle.common.configuration.Helper.getint("200");
    bombGrowTime = net.npg.abattle.common.configuration.Helper.getint("40");
    cellShading = net.npg.abattle.common.configuration.Helper.getboolean("false");
    debugCell = net.npg.abattle.common.configuration.Helper.getString("");
    dirty = false;
  }
  
  private boolean dirty;
  
  public boolean isDirty() {
    return dirty;
  }
  
  @Pure
  public float getMaxUnitSize() {
    return this.maxUnitSize;
  }
  
  public void setMaxUnitSize(final float maxUnitSize) {
    if(this.maxUnitSize==maxUnitSize) return;
    this.dirty = true;
    this.maxUnitSize = maxUnitSize;
  }
  
  @Pure
  public float getMinUnitSize() {
    return this.minUnitSize;
  }
  
  public void setMinUnitSize(final float minUnitSize) {
    if(this.minUnitSize==minUnitSize) return;
    this.dirty = true;
    this.minUnitSize = minUnitSize;
  }
  
  @Pure
  public float getStructureSize() {
    return this.structureSize;
  }
  
  public void setStructureSize(final float structureSize) {
    if(this.structureSize==structureSize) return;
    this.dirty = true;
    this.structureSize = structureSize;
  }
  
  @Pure
  public float getFightRadius() {
    return this.fightRadius;
  }
  
  public void setFightRadius(final float fightRadius) {
    if(this.fightRadius==fightRadius) return;
    this.dirty = true;
    this.fightRadius = fightRadius;
  }
  
  @Pure
  public int getCooldownFrames() {
    return this.cooldownFrames;
  }
  
  public void setCooldownFrames(final int cooldownFrames) {
    if(this.cooldownFrames==cooldownFrames) return;
    this.dirty = true;
    this.cooldownFrames = cooldownFrames;
  }
  
  @Pure
  public int getBombAddTime() {
    return this.bombAddTime;
  }
  
  public void setBombAddTime(final int bombAddTime) {
    if(this.bombAddTime==bombAddTime) return;
    this.dirty = true;
    this.bombAddTime = bombAddTime;
  }
  
  @Pure
  public int getBombGrowTime() {
    return this.bombGrowTime;
  }
  
  public void setBombGrowTime(final int bombGrowTime) {
    if(this.bombGrowTime==bombGrowTime) return;
    this.dirty = true;
    this.bombGrowTime = bombGrowTime;
  }
  
  @Pure
  public boolean isCellShading() {
    return this.cellShading;
  }
  
  public void setCellShading(final boolean cellShading) {
    if(this.cellShading==cellShading) return;
    this.dirty = true;
    this.cellShading = cellShading;
  }
  
  @Pure
  public String getDebugCell() {
    return this.debugCell;
  }
  
  public void setDebugCell(final String debugCell) {
    if(this.debugCell.equals(debugCell)) return;
    this.dirty = true;
    this.debugCell = debugCell;
  }
}
