package net.npg.abattle.server.game.impl.terrain;

import java.util.Random;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.server.game.TerrainCreator;
import net.npg.abattle.server.game.impl.terrain.BoardHelper;
import net.npg.abattle.server.model.impl.ServerBoardImpl;
import org.eclipse.xtext.xbase.lib.Functions.Function2;

@SuppressWarnings("all")
public class RandomTerrain implements TerrainCreator {
  private final Random rand = new Random(System.currentTimeMillis());
  
  private final GameConfigurationData configuration;
  
  public RandomTerrain() {
    ComponentLookup _instance = ComponentLookup.getInstance();
    ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
    GameConfigurationData _gameConfiguration = _component.getGameConfiguration();
    this.configuration = _gameConfiguration;
  }
  
  @Override
  public void createBoard(final ServerBoardImpl board, final IntPoint size, final CheckModelElement checker) {
    final Function2<Integer, Integer, Integer> _function = new Function2<Integer, Integer, Integer>() {
      @Override
      public Integer apply(final Integer x, final Integer y) {
        int _maxCellHeight = RandomTerrain.this.configuration.getMaxCellHeight();
        return Integer.valueOf(RandomTerrain.this.rand.nextInt(_maxCellHeight));
      }
    };
    BoardHelper.fillBoard(board, size, checker, _function);
  }
  
  @Override
  public String getName() {
    return "terrain.random";
  }
}
