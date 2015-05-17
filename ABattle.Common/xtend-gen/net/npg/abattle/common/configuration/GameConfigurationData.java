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
public class GameConfigurationData implements SaveableData {
  @PropertyName(name = "game.baseGrowthPerTick", defaultValue = "200")
  @DirtyAccessors
  private int baseGrowthPerTick;
  
  @PropertyName(name = "game.townGrowthPerTick", defaultValue = "100")
  @DirtyAccessors
  private int townGrowthPerTick;
  
  @PropertyName(name = "game.maxCellStrength", defaultValue = "10000")
  @DirtyAccessors
  private int maxCellStrength;
  
  @PropertyName(name = "game.maxCellHeight", defaultValue = "5")
  @DirtyAccessors
  private int maxCellHeight;
  
  @PropertyName(name = "game.maxMovement", defaultValue = "800")
  @DirtyAccessors
  private int maxMovement;
  
  @PropertyName(name = "game.winCondition", defaultValue = "95")
  @DirtyAccessors
  private int winCondition;
  
  @PropertyName(name = "game.logic", defaultValue = "")
  @DirtyAccessors
  private String logic;
  
  @PropertyName(name = "game.randomBases", defaultValue = "10")
  @DirtyAccessors
  private int randomBases;
  
  @PropertyName(name = "game.terrainInfluence", defaultValue = "70")
  @DirtyAccessors
  private int terrainInfluence;
  
  @PropertyName(name = "game.peakCount", defaultValue = "10")
  @DirtyAccessors
  private int peakCount;
  
  @PropertyName(name = "game.fog", defaultValue = "")
  @DirtyAccessors
  private String fog;
  
  @PropertyName(name = "game.terrainCreator", defaultValue = "")
  @DirtyAccessors
  private String terrainCreator;
  
  @PropertyName(name = "game.xsize", defaultValue = "8")
  @DirtyAccessors
  private int xsize;
  
  @PropertyName(name = "game.ysize", defaultValue = "8")
  @DirtyAccessors
  private int ysize;
  
  @PropertyName(name = "game.playerCount", defaultValue = "2")
  @DirtyAccessors
  private int playerCount;
  
  public GameConfigurationData(final Preferences properties) {
    baseGrowthPerTick = net.npg.abattle.common.configuration.Helper.getint(properties,"game.baseGrowthPerTick","200");
    townGrowthPerTick = net.npg.abattle.common.configuration.Helper.getint(properties,"game.townGrowthPerTick","100");
    maxCellStrength = net.npg.abattle.common.configuration.Helper.getint(properties,"game.maxCellStrength","10000");
    maxCellHeight = net.npg.abattle.common.configuration.Helper.getint(properties,"game.maxCellHeight","5");
    maxMovement = net.npg.abattle.common.configuration.Helper.getint(properties,"game.maxMovement","800");
    winCondition = net.npg.abattle.common.configuration.Helper.getint(properties,"game.winCondition","95");
    logic = net.npg.abattle.common.configuration.Helper.getString(properties,"game.logic","");
    randomBases = net.npg.abattle.common.configuration.Helper.getint(properties,"game.randomBases","10");
    terrainInfluence = net.npg.abattle.common.configuration.Helper.getint(properties,"game.terrainInfluence","70");
    peakCount = net.npg.abattle.common.configuration.Helper.getint(properties,"game.peakCount","10");
    fog = net.npg.abattle.common.configuration.Helper.getString(properties,"game.fog","");
    terrainCreator = net.npg.abattle.common.configuration.Helper.getString(properties,"game.terrainCreator","");
    xsize = net.npg.abattle.common.configuration.Helper.getint(properties,"game.xsize","8");
    ysize = net.npg.abattle.common.configuration.Helper.getint(properties,"game.ysize","8");
    playerCount = net.npg.abattle.common.configuration.Helper.getint(properties,"game.playerCount","2");
    dirty = false;
  }
  
  public GameConfigurationData() {
  }
  
  public GameConfigurationData(final Properties properties) {
    baseGrowthPerTick = net.npg.abattle.common.configuration.Helper.getint(properties,"game.baseGrowthPerTick","200");
    townGrowthPerTick = net.npg.abattle.common.configuration.Helper.getint(properties,"game.townGrowthPerTick","100");
    maxCellStrength = net.npg.abattle.common.configuration.Helper.getint(properties,"game.maxCellStrength","10000");
    maxCellHeight = net.npg.abattle.common.configuration.Helper.getint(properties,"game.maxCellHeight","5");
    maxMovement = net.npg.abattle.common.configuration.Helper.getint(properties,"game.maxMovement","800");
    winCondition = net.npg.abattle.common.configuration.Helper.getint(properties,"game.winCondition","95");
    logic = net.npg.abattle.common.configuration.Helper.getString(properties,"game.logic","");
    randomBases = net.npg.abattle.common.configuration.Helper.getint(properties,"game.randomBases","10");
    terrainInfluence = net.npg.abattle.common.configuration.Helper.getint(properties,"game.terrainInfluence","70");
    peakCount = net.npg.abattle.common.configuration.Helper.getint(properties,"game.peakCount","10");
    fog = net.npg.abattle.common.configuration.Helper.getString(properties,"game.fog","");
    terrainCreator = net.npg.abattle.common.configuration.Helper.getString(properties,"game.terrainCreator","");
    xsize = net.npg.abattle.common.configuration.Helper.getint(properties,"game.xsize","8");
    ysize = net.npg.abattle.common.configuration.Helper.getint(properties,"game.ysize","8");
    playerCount = net.npg.abattle.common.configuration.Helper.getint(properties,"game.playerCount","2");
    dirty = false;
  }
  
  public void save(final Map map) {
       map.put("game.baseGrowthPerTick",baseGrowthPerTick);
       map.put("game.townGrowthPerTick",townGrowthPerTick);
       map.put("game.maxCellStrength",maxCellStrength);
       map.put("game.maxCellHeight",maxCellHeight);
       map.put("game.maxMovement",maxMovement);
       map.put("game.winCondition",winCondition);
       map.put("game.logic",logic);
       map.put("game.randomBases",randomBases);
       map.put("game.terrainInfluence",terrainInfluence);
       map.put("game.peakCount",peakCount);
       map.put("game.fog",fog);
       map.put("game.terrainCreator",terrainCreator);
       map.put("game.xsize",xsize);
       map.put("game.ysize",ysize);
       map.put("game.playerCount",playerCount);
    dirty = false;
  }
  
  public void reset() {
    baseGrowthPerTick = net.npg.abattle.common.configuration.Helper.getint("200");
    townGrowthPerTick = net.npg.abattle.common.configuration.Helper.getint("100");
    maxCellStrength = net.npg.abattle.common.configuration.Helper.getint("10000");
    maxCellHeight = net.npg.abattle.common.configuration.Helper.getint("5");
    maxMovement = net.npg.abattle.common.configuration.Helper.getint("800");
    winCondition = net.npg.abattle.common.configuration.Helper.getint("95");
    logic = net.npg.abattle.common.configuration.Helper.getString("");
    randomBases = net.npg.abattle.common.configuration.Helper.getint("10");
    terrainInfluence = net.npg.abattle.common.configuration.Helper.getint("70");
    peakCount = net.npg.abattle.common.configuration.Helper.getint("10");
    fog = net.npg.abattle.common.configuration.Helper.getString("");
    terrainCreator = net.npg.abattle.common.configuration.Helper.getString("");
    xsize = net.npg.abattle.common.configuration.Helper.getint("8");
    ysize = net.npg.abattle.common.configuration.Helper.getint("8");
    playerCount = net.npg.abattle.common.configuration.Helper.getint("2");
    dirty = false;
  }
  
  private boolean dirty;
  
  public boolean isDirty() {
    return dirty;
  }
  
  @Pure
  public int getBaseGrowthPerTick() {
    return this.baseGrowthPerTick;
  }
  
  public void setBaseGrowthPerTick(final int baseGrowthPerTick) {
    if(this.baseGrowthPerTick==baseGrowthPerTick) return;
    this.dirty = true;
    this.baseGrowthPerTick = baseGrowthPerTick;
  }
  
  @Pure
  public int getTownGrowthPerTick() {
    return this.townGrowthPerTick;
  }
  
  public void setTownGrowthPerTick(final int townGrowthPerTick) {
    if(this.townGrowthPerTick==townGrowthPerTick) return;
    this.dirty = true;
    this.townGrowthPerTick = townGrowthPerTick;
  }
  
  @Pure
  public int getMaxCellStrength() {
    return this.maxCellStrength;
  }
  
  public void setMaxCellStrength(final int maxCellStrength) {
    if(this.maxCellStrength==maxCellStrength) return;
    this.dirty = true;
    this.maxCellStrength = maxCellStrength;
  }
  
  @Pure
  public int getMaxCellHeight() {
    return this.maxCellHeight;
  }
  
  public void setMaxCellHeight(final int maxCellHeight) {
    if(this.maxCellHeight==maxCellHeight) return;
    this.dirty = true;
    this.maxCellHeight = maxCellHeight;
  }
  
  @Pure
  public int getMaxMovement() {
    return this.maxMovement;
  }
  
  public void setMaxMovement(final int maxMovement) {
    if(this.maxMovement==maxMovement) return;
    this.dirty = true;
    this.maxMovement = maxMovement;
  }
  
  @Pure
  public int getWinCondition() {
    return this.winCondition;
  }
  
  public void setWinCondition(final int winCondition) {
    if(this.winCondition==winCondition) return;
    this.dirty = true;
    this.winCondition = winCondition;
  }
  
  @Pure
  public String getLogic() {
    return this.logic;
  }
  
  public void setLogic(final String logic) {
    if(this.logic.equals(logic)) return;
    this.dirty = true;
    this.logic = logic;
  }
  
  @Pure
  public int getRandomBases() {
    return this.randomBases;
  }
  
  public void setRandomBases(final int randomBases) {
    if(this.randomBases==randomBases) return;
    this.dirty = true;
    this.randomBases = randomBases;
  }
  
  @Pure
  public int getTerrainInfluence() {
    return this.terrainInfluence;
  }
  
  public void setTerrainInfluence(final int terrainInfluence) {
    if(this.terrainInfluence==terrainInfluence) return;
    this.dirty = true;
    this.terrainInfluence = terrainInfluence;
  }
  
  @Pure
  public int getPeakCount() {
    return this.peakCount;
  }
  
  public void setPeakCount(final int peakCount) {
    if(this.peakCount==peakCount) return;
    this.dirty = true;
    this.peakCount = peakCount;
  }
  
  @Pure
  public String getFog() {
    return this.fog;
  }
  
  public void setFog(final String fog) {
    if(this.fog.equals(fog)) return;
    this.dirty = true;
    this.fog = fog;
  }
  
  @Pure
  public String getTerrainCreator() {
    return this.terrainCreator;
  }
  
  public void setTerrainCreator(final String terrainCreator) {
    if(this.terrainCreator.equals(terrainCreator)) return;
    this.dirty = true;
    this.terrainCreator = terrainCreator;
  }
  
  @Pure
  public int getXsize() {
    return this.xsize;
  }
  
  public void setXsize(final int xsize) {
    if(this.xsize==xsize) return;
    this.dirty = true;
    this.xsize = xsize;
  }
  
  @Pure
  public int getYsize() {
    return this.ysize;
  }
  
  public void setYsize(final int ysize) {
    if(this.ysize==ysize) return;
    this.dirty = true;
    this.ysize = ysize;
  }
  
  @Pure
  public int getPlayerCount() {
    return this.playerCount;
  }
  
  public void setPlayerCount(final int playerCount) {
    if(this.playerCount==playerCount) return;
    this.dirty = true;
    this.playerCount = playerCount;
  }
}
