package net.npg.abattle.client.view.boardscene.debug;

import net.npg.abattle.client.view.boardscene.debug.DebugCell;
import net.npg.abattle.client.view.boardscene.debug.HeightDebug;
import net.npg.abattle.client.view.boardscene.debug.NoDebug;
import net.npg.abattle.client.view.boardscene.debug.StrengthDebug;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.GraphicsConfigurationData;
import net.npg.abattle.common.configuration.impl.SelectableClassList;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

@SuppressWarnings("all")
public class DebugCells extends SelectableClassList<DebugCell> {
  public final static SelectableClassList<DebugCell> debugList = new DebugCells();
  
  public DebugCells() {
    super(
      new Function0<String>() {
        @Override
        public String apply() {
          ComponentLookup _instance = ComponentLookup.getInstance();
          ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
          GraphicsConfigurationData _graphicsConfiguration = _component.getGraphicsConfiguration();
          return _graphicsConfiguration.getDebugCell();
        }
      }, 
      NoDebug.NAME);
    NoDebug _noDebug = new NoDebug();
    this.addSectableClass(_noDebug);
    HeightDebug _heightDebug = new HeightDebug();
    this.addSectableClass(_heightDebug);
    StrengthDebug _strengthDebug = new StrengthDebug();
    this.addSectableClass(_strengthDebug);
  }
}
