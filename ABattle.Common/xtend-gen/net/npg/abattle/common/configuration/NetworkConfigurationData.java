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
public class NetworkConfigurationData implements SaveableData {
  @PropertyName(name = "network.searchTimeout", defaultValue = "2000")
  @DirtyAccessors
  private int searchTimeout;
  
  @PropertyName(name = "network.connectTimeout", defaultValue = "5000")
  @DirtyAccessors
  private int connectTimeout;
  
  @PropertyName(name = "network.port", defaultValue = "54666")
  @DirtyAccessors
  private int port;
  
  public NetworkConfigurationData(final Preferences properties) {
    searchTimeout = net.npg.abattle.common.configuration.Helper.getint(properties,"network.searchTimeout","2000");
    connectTimeout = net.npg.abattle.common.configuration.Helper.getint(properties,"network.connectTimeout","5000");
    port = net.npg.abattle.common.configuration.Helper.getint(properties,"network.port","54666");
    dirty = false;
  }
  
  public NetworkConfigurationData() {
  }
  
  public NetworkConfigurationData(final Properties properties) {
    searchTimeout = net.npg.abattle.common.configuration.Helper.getint(properties,"network.searchTimeout","2000");
    connectTimeout = net.npg.abattle.common.configuration.Helper.getint(properties,"network.connectTimeout","5000");
    port = net.npg.abattle.common.configuration.Helper.getint(properties,"network.port","54666");
    dirty = false;
  }
  
  public void save(final Map map) {
       map.put("network.searchTimeout",searchTimeout);
       map.put("network.connectTimeout",connectTimeout);
       map.put("network.port",port);
    dirty = false;
  }
  
  public void reset() {
    searchTimeout = net.npg.abattle.common.configuration.Helper.getint("2000");
    connectTimeout = net.npg.abattle.common.configuration.Helper.getint("5000");
    port = net.npg.abattle.common.configuration.Helper.getint("54666");
    dirty = false;
  }
  
  private boolean dirty;
  
  public boolean isDirty() {
    return dirty;
  }
  
  @Pure
  public int getSearchTimeout() {
    return this.searchTimeout;
  }
  
  public void setSearchTimeout(final int searchTimeout) {
    if(this.searchTimeout==searchTimeout) return;
    this.dirty = true;
    this.searchTimeout = searchTimeout;
  }
  
  @Pure
  public int getConnectTimeout() {
    return this.connectTimeout;
  }
  
  public void setConnectTimeout(final int connectTimeout) {
    if(this.connectTimeout==connectTimeout) return;
    this.dirty = true;
    this.connectTimeout = connectTimeout;
  }
  
  @Pure
  public int getPort() {
    return this.port;
  }
  
  public void setPort(final int port) {
    if(this.port==port) return;
    this.dirty = true;
    this.port = port;
  }
}
