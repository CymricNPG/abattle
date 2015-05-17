package net.npg.abattle.server.logic;

import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.configuration.impl.SelectableClassList;
import net.npg.abattle.server.logic.Logic;
import net.npg.abattle.server.logic.SimpleDistributeArmies;
import net.npg.abattle.server.logic.XBattleDistribute;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

@SuppressWarnings("all")
public class Logics extends SelectableClassList<Logic> {
  public final static SelectableClassList<Logic> logicMap = new Logics();
  
  public Logics() {
    super(
      new Function0<String>() {
        @Override
        public String apply() {
          ComponentLookup _instance = ComponentLookup.getInstance();
          ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
          GameConfigurationData _gameConfiguration = _component.getGameConfiguration();
          return _gameConfiguration.getLogic();
        }
      }, 
      XBattleDistribute.NAME);
    SimpleDistributeArmies _simpleDistributeArmies = new SimpleDistributeArmies();
    this.addSectableClass(_simpleDistributeArmies);
    XBattleDistribute _xBattleDistribute = new XBattleDistribute();
    this.addSectableClass(_xBattleDistribute);
  }
}
