package net.npg.abattle.common.configuration.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.component.ComponentType;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.configuration.GameLoopConfigurationData;
import net.npg.abattle.common.configuration.GlobalOptionsData;
import net.npg.abattle.common.configuration.GraphicsConfigurationData;
import net.npg.abattle.common.configuration.NetworkConfigurationData;
import net.npg.abattle.common.configuration.SaveableData;
import net.npg.abattle.common.utils.impl.DisposeableImpl;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

@ComponentType
@SuppressWarnings("all")
public class ConfigurationComponentImpl extends DisposeableImpl implements ConfigurationComponent {
  @Accessors
  private GraphicsConfigurationData graphicsConfiguration;
  
  @Accessors
  private GameConfigurationData gameConfiguration;
  
  @Accessors
  private GameLoopConfigurationData gameLoopConfiguration;
  
  @Accessors
  private NetworkConfigurationData networkConfiguration;
  
  @Accessors
  private GlobalOptionsData globalOptions;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private Set<SaveableData> configurations;
  
  public ConfigurationComponentImpl() {
    HashSet<SaveableData> _newHashSet = CollectionLiterals.<SaveableData>newHashSet();
    this.configurations = _newHashSet;
    final Preferences properties = this.loadProperties();
    GraphicsConfigurationData _graphicsConfigurationData = new GraphicsConfigurationData(properties);
    GraphicsConfigurationData _addConfiguration = this.<GraphicsConfigurationData>addConfiguration(_graphicsConfigurationData);
    this.graphicsConfiguration = _addConfiguration;
    GameConfigurationData _gameConfigurationData = new GameConfigurationData(properties);
    GameConfigurationData _addConfiguration_1 = this.<GameConfigurationData>addConfiguration(_gameConfigurationData);
    this.gameConfiguration = _addConfiguration_1;
    GameLoopConfigurationData _gameLoopConfigurationData = new GameLoopConfigurationData(properties);
    GameLoopConfigurationData _addConfiguration_2 = this.<GameLoopConfigurationData>addConfiguration(_gameLoopConfigurationData);
    this.gameLoopConfiguration = _addConfiguration_2;
    NetworkConfigurationData _networkConfigurationData = new NetworkConfigurationData(properties);
    NetworkConfigurationData _addConfiguration_3 = this.<NetworkConfigurationData>addConfiguration(_networkConfigurationData);
    this.networkConfiguration = _addConfiguration_3;
    GlobalOptionsData _globalOptionsData = new GlobalOptionsData(properties);
    GlobalOptionsData _addConfiguration_4 = this.<GlobalOptionsData>addConfiguration(_globalOptionsData);
    this.globalOptions = _addConfiguration_4;
    this.fixConfiguration();
  }
  
  public void fixConfiguration() {
    String _version = this.globalOptions.getVersion();
    boolean _equals = CommonConstants.VERSION.equals(_version);
    if(_equals) return;;
    this.reset();
    this.globalOptions.setVersion(CommonConstants.VERSION);
    this.save();
  }
  
  private <T extends SaveableData> T addConfiguration(final T configuration) {
    T _xblockexpression = null;
    {
      this.configurations.add(configuration);
      _xblockexpression = configuration;
    }
    return _xblockexpression;
  }
  
  @Override
  public void save() {
    final Function1<SaveableData, Boolean> _function = new Function1<SaveableData, Boolean>() {
      @Override
      public Boolean apply(final SaveableData it) {
        return Boolean.valueOf(it.isDirty());
      }
    };
    boolean _exists = IterableExtensions.<SaveableData>exists(this.configurations, _function);
    if(!_exists) return;;
    final Map<String, Object> keyValues = CollectionLiterals.<String, Object>newHashMap();
    for (final SaveableData configuration : this.configurations) {
      configuration.save(keyValues);
    }
    final Preferences properties = this.loadProperties();
    Set<Map.Entry<String, Object>> _entrySet = keyValues.entrySet();
    for (final Map.Entry<String, Object> keyValue : _entrySet) {
      {
        final Object value = keyValue.getValue();
        if ((value instanceof Integer)) {
          String _key = keyValue.getKey();
          properties.putInteger(_key, ((Integer) value).intValue());
        } else {
          if ((value instanceof Long)) {
            String _key_1 = keyValue.getKey();
            properties.putLong(_key_1, ((Long) value).longValue());
          } else {
            if ((value instanceof String)) {
              String _key_2 = keyValue.getKey();
              properties.putString(_key_2, ((String)value));
            } else {
              if ((value instanceof Boolean)) {
                String _key_3 = keyValue.getKey();
                properties.putBoolean(_key_3, ((Boolean) value).booleanValue());
              }
            }
          }
        }
      }
    }
    properties.flush();
  }
  
  public Preferences loadProperties() {
    String _canonicalName = ConfigurationComponent.class.getCanonicalName();
    return Gdx.app.getPreferences(_canonicalName);
  }
  
  @Override
  public void reset() {
    CommonConstants.LOG.info("Reset Configuration");
    final Procedure1<SaveableData> _function = new Procedure1<SaveableData>() {
      @Override
      public void apply(final SaveableData it) {
        it.reset();
      }
    };
    IterableExtensions.<SaveableData>forEach(this.configurations, _function);
  }
  
  public Class<ConfigurationComponent> getInterface() {
    return ConfigurationComponent.class;
  }
  
  @Pure
  public GraphicsConfigurationData getGraphicsConfiguration() {
    return this.graphicsConfiguration;
  }
  
  public void setGraphicsConfiguration(final GraphicsConfigurationData graphicsConfiguration) {
    this.graphicsConfiguration = graphicsConfiguration;
  }
  
  @Pure
  public GameConfigurationData getGameConfiguration() {
    return this.gameConfiguration;
  }
  
  public void setGameConfiguration(final GameConfigurationData gameConfiguration) {
    this.gameConfiguration = gameConfiguration;
  }
  
  @Pure
  public GameLoopConfigurationData getGameLoopConfiguration() {
    return this.gameLoopConfiguration;
  }
  
  public void setGameLoopConfiguration(final GameLoopConfigurationData gameLoopConfiguration) {
    this.gameLoopConfiguration = gameLoopConfiguration;
  }
  
  @Pure
  public NetworkConfigurationData getNetworkConfiguration() {
    return this.networkConfiguration;
  }
  
  public void setNetworkConfiguration(final NetworkConfigurationData networkConfiguration) {
    this.networkConfiguration = networkConfiguration;
  }
  
  @Pure
  public GlobalOptionsData getGlobalOptions() {
    return this.globalOptions;
  }
  
  public void setGlobalOptions(final GlobalOptionsData globalOptions) {
    this.globalOptions = globalOptions;
  }
  
  @Pure
  public Set<SaveableData> getConfigurations() {
    return this.configurations;
  }
}
