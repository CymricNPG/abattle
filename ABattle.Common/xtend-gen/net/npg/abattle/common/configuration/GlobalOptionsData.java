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
public class GlobalOptionsData implements SaveableData {
  @PropertyName(name = "global.name", defaultValue = "", noReset = true)
  @DirtyAccessors
  private String name;
  
  @PropertyName(name = "global.gui.coneSelection", defaultValue = "true")
  @DirtyAccessors
  private boolean coneSelection;
  
  @PropertyName(name = "global.version", defaultValue = "")
  @DirtyAccessors
  private String version;
  
  public GlobalOptionsData(final Preferences properties) {
    name = net.npg.abattle.common.configuration.Helper.getString(properties,"global.name","");
    coneSelection = net.npg.abattle.common.configuration.Helper.getboolean(properties,"global.gui.coneSelection","true");
    version = net.npg.abattle.common.configuration.Helper.getString(properties,"global.version","");
    dirty = false;
  }
  
  public GlobalOptionsData() {
  }
  
  public GlobalOptionsData(final Properties properties) {
    name = net.npg.abattle.common.configuration.Helper.getString(properties,"global.name","");
    coneSelection = net.npg.abattle.common.configuration.Helper.getboolean(properties,"global.gui.coneSelection","true");
    version = net.npg.abattle.common.configuration.Helper.getString(properties,"global.version","");
    dirty = false;
  }
  
  public void save(final Map map) {
       map.put("global.name",name);
       map.put("global.gui.coneSelection",coneSelection);
       map.put("global.version",version);
    dirty = false;
  }
  
  public void reset() {
    coneSelection = net.npg.abattle.common.configuration.Helper.getboolean("true");
    version = net.npg.abattle.common.configuration.Helper.getString("");
    dirty = false;
  }
  
  private boolean dirty;
  
  public boolean isDirty() {
    return dirty;
  }
  
  @Pure
  public String getName() {
    return this.name;
  }
  
  public void setName(final String name) {
    if(this.name.equals(name)) return;
    this.dirty = true;
    this.name = name;
  }
  
  @Pure
  public boolean isConeSelection() {
    return this.coneSelection;
  }
  
  public void setConeSelection(final boolean coneSelection) {
    if(this.coneSelection==coneSelection) return;
    this.dirty = true;
    this.coneSelection = coneSelection;
  }
  
  @Pure
  public String getVersion() {
    return this.version;
  }
  
  public void setVersion(final String version) {
    if(this.version.equals(version)) return;
    this.dirty = true;
    this.version = version;
  }
}
