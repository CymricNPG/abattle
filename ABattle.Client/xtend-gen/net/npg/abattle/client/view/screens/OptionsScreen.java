package net.npg.abattle.client.view.screens;

import java.util.Set;
import net.npg.abattle.client.view.boardscene.debug.DebugCell;
import net.npg.abattle.client.view.boardscene.debug.DebugCells;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.screens.Layout;
import net.npg.abattle.client.view.screens.MyStage;
import net.npg.abattle.client.view.screens.MyTextButton;
import net.npg.abattle.client.view.screens.OptionTable;
import net.npg.abattle.client.view.screens.ParameterScreen;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.Widgets;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.configuration.GameLoopConfigurationData;
import net.npg.abattle.common.configuration.GlobalOptionsData;
import net.npg.abattle.common.configuration.GraphicsConfigurationData;
import net.npg.abattle.common.i18n.I18N;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.server.game.TerrainCreator;
import net.npg.abattle.server.game.impl.fog.Fog;
import net.npg.abattle.server.game.impl.fog.Fogs;
import net.npg.abattle.server.game.impl.terrain.TerrainCreators;
import net.npg.abattle.server.logic.Logic;
import net.npg.abattle.server.logic.Logics;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class OptionsScreen extends BasicScreen implements ParameterScreen<Screens> {
  private OptionTable table;
  
  private MyTextButton resetButton;
  
  private Screens backScreen;
  
  @Override
  public void create() {
    Widgets _widgets = this.getWidgets();
    MyStage _stage = this.getStage();
    OptionTable _optionTable = new OptionTable(_widgets, _stage);
    this.table = _optionTable;
    this.table.create();
    this.fillTable();
    Widgets _widgets_1 = this.getWidgets();
    final Procedure0 _function = new Procedure0() {
      @Override
      public void apply() {
        OptionsScreen.this.finish();
      }
    };
    _widgets_1.addButton(Layout.Back, Icons.Back, _function);
    Widgets _widgets_2 = this.getWidgets();
    final Procedure0 _function_1 = new Procedure0() {
      @Override
      public void apply() {
        OptionsScreen.this.reset();
      }
    };
    MyTextButton _addButton = _widgets_2.addButton(Layout.Options, Icons.Reset, _function_1);
    this.resetButton = _addButton;
  }
  
  public boolean reset() {
    boolean _xblockexpression = false;
    {
      ConfigurationComponent _configuration = this.getConfiguration();
      _configuration.reset();
      this.resetButton.setChecked(false);
      _xblockexpression = this.fillTable();
    }
    return _xblockexpression;
  }
  
  private void finish() {
    assert this.backScreen != null;
    this.table.finish();
    ConfigurationComponent _configuration = this.getConfiguration();
    _configuration.save();
    ScreenSwitcher _switcher = this.getSwitcher();
    _switcher.switchToScreen(this.backScreen);
  }
  
  @Override
  public void render() {
  }
  
  private boolean fillTable() {
    boolean _xblockexpression = false;
    {
      this.table.fillHeader();
      ConfigurationComponent _configuration = this.getConfiguration();
      final GameConfigurationData gameConfiguration = _configuration.getGameConfiguration();
      String _get = I18N.get("opt_namefield");
      ConfigurationComponent _configuration_1 = this.getConfiguration();
      GlobalOptionsData _globalOptions = _configuration_1.getGlobalOptions();
      String _name = _globalOptions.getName();
      String _get_1 = I18N.get("opt_name");
      final Procedure1<String> _function = new Procedure1<String>() {
        @Override
        public void apply(final String it) {
          ConfigurationComponent _configuration = OptionsScreen.this.getConfiguration();
          GlobalOptionsData _globalOptions = _configuration.getGlobalOptions();
          _globalOptions.setName(it);
        }
      };
      this.table.addTextField(_get, _name, _get_1, _function);
      String _get_2 = I18N.get("opt_movement");
      Set<String> _names = Logics.logicMap.getNames();
      Logic _selectedClass = Logics.logicMap.getSelectedClass();
      String _name_1 = _selectedClass.getName();
      final Procedure1<String> _function_1 = new Procedure1<String>() {
        @Override
        public void apply(final String it) {
          gameConfiguration.setLogic(it);
        }
      };
      this.table.addDropDown(_get_2, _names, _name_1, _function_1);
      String _get_3 = I18N.get("opt_levels");
      int _maxCellHeight = gameConfiguration.getMaxCellHeight();
      final Procedure1<Integer> _function_2 = new Procedure1<Integer>() {
        @Override
        public void apply(final Integer it) {
          gameConfiguration.setMaxCellHeight((it).intValue());
        }
      };
      this.table.addSlider(_get_3, 1, 9, _maxCellHeight, _function_2);
      String _get_4 = I18N.get("opt_towns");
      int _randomBases = gameConfiguration.getRandomBases();
      final Procedure1<Integer> _function_3 = new Procedure1<Integer>() {
        @Override
        public void apply(final Integer it) {
          gameConfiguration.setRandomBases((it).intValue());
        }
      };
      this.table.addSlider(_get_4, 0, 100, _randomBases, _function_3, 1.0f, "%");
      String _get_5 = I18N.get("opt_win");
      int _winCondition = gameConfiguration.getWinCondition();
      final Procedure1<Integer> _function_4 = new Procedure1<Integer>() {
        @Override
        public void apply(final Integer it) {
          gameConfiguration.setWinCondition((it).intValue());
        }
      };
      this.table.addSlider(_get_5, 0, 100, _winCondition, _function_4, 1.0f, "%");
      String _get_6 = I18N.get("opt_home");
      int _baseGrowthPerTick = gameConfiguration.getBaseGrowthPerTick();
      final Procedure1<Integer> _function_5 = new Procedure1<Integer>() {
        @Override
        public void apply(final Integer it) {
          gameConfiguration.setBaseGrowthPerTick((it).intValue());
        }
      };
      this.table.addSlider(_get_6, 100, 1000, _baseGrowthPerTick, _function_5, 50);
      String _get_7 = I18N.get("opt_town");
      int _townGrowthPerTick = gameConfiguration.getTownGrowthPerTick();
      final Procedure1<Integer> _function_6 = new Procedure1<Integer>() {
        @Override
        public void apply(final Integer it) {
          gameConfiguration.setTownGrowthPerTick((it).intValue());
        }
      };
      this.table.addSlider(_get_7, 0, 1000, _townGrowthPerTick, _function_6, 50);
      String _get_8 = I18N.get("opt_moving");
      int _maxMovement = gameConfiguration.getMaxMovement();
      final Procedure1<Integer> _function_7 = new Procedure1<Integer>() {
        @Override
        public void apply(final Integer it) {
          gameConfiguration.setMaxMovement((it).intValue());
        }
      };
      this.table.addSlider(_get_8, 50, 2000, _maxMovement, _function_7, 50);
      String _get_9 = I18N.get("opt_terrain");
      int _terrainInfluence = gameConfiguration.getTerrainInfluence();
      final Procedure1<Integer> _function_8 = new Procedure1<Integer>() {
        @Override
        public void apply(final Integer it) {
          gameConfiguration.setTerrainInfluence((it).intValue());
        }
      };
      this.table.addSlider(_get_9, 1, 100, _terrainInfluence, _function_8, 1.0f, "%");
      String _get_10 = I18N.get("opt_creator");
      Set<String> _names_1 = TerrainCreators.terrainMap.getNames();
      TerrainCreator _selectedClass_1 = TerrainCreators.terrainMap.getSelectedClass();
      String _name_2 = _selectedClass_1.getName();
      final Procedure1<String> _function_9 = new Procedure1<String>() {
        @Override
        public void apply(final String it) {
          gameConfiguration.setTerrainCreator(it);
        }
      };
      this.table.addDropDown(_get_10, _names_1, _name_2, _function_9);
      String _get_11 = I18N.get("opt_fog");
      Set<String> _names_2 = Fogs.fogList.getNames();
      Fog _selectedClass_2 = Fogs.fogList.getSelectedClass();
      String _name_3 = _selectedClass_2.getName();
      final Procedure1<String> _function_10 = new Procedure1<String>() {
        @Override
        public void apply(final String it) {
          gameConfiguration.setFog(it);
        }
      };
      this.table.addDropDown(_get_11, _names_2, _name_3, _function_10);
      String _get_12 = I18N.get("opt_peaks");
      int _peakCount = gameConfiguration.getPeakCount();
      final Procedure1<Integer> _function_11 = new Procedure1<Integer>() {
        @Override
        public void apply(final Integer it) {
          gameConfiguration.setPeakCount((it).intValue());
        }
      };
      this.table.addSlider(_get_12, 0, 100, _peakCount, _function_11, 1.0f, "%");
      String _get_13 = I18N.get("opt_shade");
      ConfigurationComponent _configuration_2 = this.getConfiguration();
      GraphicsConfigurationData _graphicsConfiguration = _configuration_2.getGraphicsConfiguration();
      boolean _isCellShading = _graphicsConfiguration.isCellShading();
      final Procedure1<Boolean> _function_12 = new Procedure1<Boolean>() {
        @Override
        public void apply(final Boolean it) {
          ConfigurationComponent _configuration = OptionsScreen.this.getConfiguration();
          GraphicsConfigurationData _graphicsConfiguration = _configuration.getGraphicsConfiguration();
          _graphicsConfiguration.setCellShading((it).booleanValue());
        }
      };
      this.table.addCheckBox(_get_13, _isCellShading, _function_12);
      String _get_14 = I18N.get("opt_selection");
      ConfigurationComponent _configuration_3 = this.getConfiguration();
      GlobalOptionsData _globalOptions_1 = _configuration_3.getGlobalOptions();
      boolean _isConeSelection = _globalOptions_1.isConeSelection();
      final Procedure1<Boolean> _function_13 = new Procedure1<Boolean>() {
        @Override
        public void apply(final Boolean it) {
          ConfigurationComponent _configuration = OptionsScreen.this.getConfiguration();
          GlobalOptionsData _globalOptions = _configuration.getGlobalOptions();
          _globalOptions.setConeSelection((it).booleanValue());
        }
      };
      this.table.addCheckBox(_get_14, _isConeSelection, _function_13);
      String _get_15 = I18N.get("opt_debugcell");
      Set<String> _names_3 = DebugCells.debugList.getNames();
      DebugCell _selectedClass_3 = DebugCells.debugList.getSelectedClass();
      String _name_4 = _selectedClass_3.getName();
      final Procedure1<String> _function_14 = new Procedure1<String>() {
        @Override
        public void apply(final String it) {
          ConfigurationComponent _configuration = OptionsScreen.this.getConfiguration();
          GraphicsConfigurationData _graphicsConfiguration = _configuration.getGraphicsConfiguration();
          _graphicsConfiguration.setDebugCell(it);
        }
      };
      this.table.addDropDown(_get_15, _names_3, _name_4, _function_14);
      String _get_16 = I18N.get("opt_updates");
      ConfigurationComponent _configuration_4 = this.getConfiguration();
      GameLoopConfigurationData _gameLoopConfiguration = _configuration_4.getGameLoopConfiguration();
      long _logicUpdatesPerSecond = _gameLoopConfiguration.getLogicUpdatesPerSecond();
      final Procedure1<Integer> _function_15 = new Procedure1<Integer>() {
        @Override
        public void apply(final Integer it) {
          ConfigurationComponent _configuration = OptionsScreen.this.getConfiguration();
          GameLoopConfigurationData _gameLoopConfiguration = _configuration.getGameLoopConfiguration();
          _gameLoopConfiguration.setLogicUpdatesPerSecond(((long) (it).intValue()));
        }
      };
      _xblockexpression = this.table.addSlider(_get_16, 5, 25, ((int) _logicUpdatesPerSecond), _function_15);
    }
    return _xblockexpression;
  }
  
  private ConfigurationComponent getConfiguration() {
    ComponentLookup _instance = ComponentLookup.getInstance();
    return _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
  }
  
  @Override
  public void backButton() {
    ScreenSwitcher _switcher = this.getSwitcher();
    _switcher.switchToScreen(Screens.Main);
  }
  
  @Override
  public void setParameter(final Screens backScreen) {
    Validate.notNull(backScreen);
    this.backScreen = backScreen;
  }
  
  @Override
  public Screens getType() {
    return Screens.Options;
  }
}
