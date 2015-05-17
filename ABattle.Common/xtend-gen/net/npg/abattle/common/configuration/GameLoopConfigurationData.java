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
public class GameLoopConfigurationData implements SaveableData {
  @PropertyName(name = "loop.logicUpdatesPerSecond", defaultValue = "15")
  @DirtyAccessors
  private long logicUpdatesPerSecond;
  
  @PropertyName(name = "loop.sentUpdatesPerSecond", defaultValue = "15")
  @DirtyAccessors
  private long sentUpdatesPerSecond;
  
  @PropertyName(name = "loop.processCommandsPerSecond", defaultValue = "20")
  @DirtyAccessors
  private long processCommandsPerSecond;
  
  public GameLoopConfigurationData(final Preferences properties) {
    logicUpdatesPerSecond = net.npg.abattle.common.configuration.Helper.getlong(properties,"loop.logicUpdatesPerSecond","15");
    sentUpdatesPerSecond = net.npg.abattle.common.configuration.Helper.getlong(properties,"loop.sentUpdatesPerSecond","15");
    processCommandsPerSecond = net.npg.abattle.common.configuration.Helper.getlong(properties,"loop.processCommandsPerSecond","20");
    dirty = false;
  }
  
  public GameLoopConfigurationData() {
  }
  
  public GameLoopConfigurationData(final Properties properties) {
    logicUpdatesPerSecond = net.npg.abattle.common.configuration.Helper.getlong(properties,"loop.logicUpdatesPerSecond","15");
    sentUpdatesPerSecond = net.npg.abattle.common.configuration.Helper.getlong(properties,"loop.sentUpdatesPerSecond","15");
    processCommandsPerSecond = net.npg.abattle.common.configuration.Helper.getlong(properties,"loop.processCommandsPerSecond","20");
    dirty = false;
  }
  
  public void save(final Map map) {
       map.put("loop.logicUpdatesPerSecond",logicUpdatesPerSecond);
       map.put("loop.sentUpdatesPerSecond",sentUpdatesPerSecond);
       map.put("loop.processCommandsPerSecond",processCommandsPerSecond);
    dirty = false;
  }
  
  public void reset() {
    logicUpdatesPerSecond = net.npg.abattle.common.configuration.Helper.getlong("15");
    sentUpdatesPerSecond = net.npg.abattle.common.configuration.Helper.getlong("15");
    processCommandsPerSecond = net.npg.abattle.common.configuration.Helper.getlong("20");
    dirty = false;
  }
  
  private boolean dirty;
  
  public boolean isDirty() {
    return dirty;
  }
  
  @Pure
  public long getLogicUpdatesPerSecond() {
    return this.logicUpdatesPerSecond;
  }
  
  public void setLogicUpdatesPerSecond(final long logicUpdatesPerSecond) {
    if(this.logicUpdatesPerSecond==logicUpdatesPerSecond) return;
    this.dirty = true;
    this.logicUpdatesPerSecond = logicUpdatesPerSecond;
  }
  
  @Pure
  public long getSentUpdatesPerSecond() {
    return this.sentUpdatesPerSecond;
  }
  
  public void setSentUpdatesPerSecond(final long sentUpdatesPerSecond) {
    if(this.sentUpdatesPerSecond==sentUpdatesPerSecond) return;
    this.dirty = true;
    this.sentUpdatesPerSecond = sentUpdatesPerSecond;
  }
  
  @Pure
  public long getProcessCommandsPerSecond() {
    return this.processCommandsPerSecond;
  }
  
  public void setProcessCommandsPerSecond(final long processCommandsPerSecond) {
    if(this.processCommandsPerSecond==processCommandsPerSecond) return;
    this.dirty = true;
    this.processCommandsPerSecond = processCommandsPerSecond;
  }
}
