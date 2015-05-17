package net.npg.abattle.server.game.impl.fog;

import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.configuration.impl.SelectableClassList;
import net.npg.abattle.server.game.impl.fog.Fog;
import net.npg.abattle.server.game.impl.fog.NoFog;
import net.npg.abattle.server.game.impl.fog.SimpleFog;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

@SuppressWarnings("all")
public class Fogs extends SelectableClassList<Fog> {
  public final static SelectableClassList<Fog> fogList = new Fogs();
  
  public Fogs() {
    super(
      new Function0<String>() {
        @Override
        public String apply() {
          ComponentLookup _instance = ComponentLookup.getInstance();
          ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
          GameConfigurationData _gameConfiguration = _component.getGameConfiguration();
          return _gameConfiguration.getFog();
        }
      }, SimpleFog.NAME);
    SimpleFog _simpleFog = new SimpleFog();
    this.addSectableClass(_simpleFog);
    NoFog _noFog = new NoFog();
    this.addSectableClass(_noFog);
  }
}
