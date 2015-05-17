package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import net.npg.abattle.client.local.GameBaseParametersImpl;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.screens.Layout;
import net.npg.abattle.client.view.screens.MyTextButton;
import net.npg.abattle.client.view.screens.OptionTable;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.Widgets;
import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.configuration.GlobalOptionsData;
import net.npg.abattle.common.i18n.I18N;
import net.npg.abattle.common.utils.Validate;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class GameParameterPartScreen {
  private final Widgets widgets;
  
  private final boolean humans;
  
  private final BasicScreen parentScreen;
  
  private OptionTable table;
  
  private GameConfigurationData configuration;
  
  public GameParameterPartScreen(final Widgets widgets, final boolean humans, final BasicScreen parentScreen) {
    Validate.notNulls(widgets, parentScreen);
    this.widgets = widgets;
    ComponentLookup _instance = ComponentLookup.getInstance();
    ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
    GameConfigurationData _gameConfiguration = _component.getGameConfiguration();
    this.configuration = _gameConfiguration;
    this.parentScreen = parentScreen;
    this.humans = humans;
  }
  
  public MyTextButton create(final Procedure0 startProcedure, final Stage stage) {
    MyTextButton _xblockexpression = null;
    {
      OptionTable _optionTable = new OptionTable(this.widgets, stage);
      this.table = _optionTable;
      this.table.create();
      this.table.fillHeader();
      String _get = I18N.get("gp_width");
      int _xsize = this.configuration.getXsize();
      final Procedure1<Integer> _function = new Procedure1<Integer>() {
        @Override
        public void apply(final Integer it) {
          GameParameterPartScreen.this.configuration.setXsize((it).intValue());
        }
      };
      this.table.addSlider(_get, CommonConstants.MIN_BOARD_SIZE, CommonConstants.MAX_BOARD_SIZE, _xsize, _function);
      String _get_1 = I18N.get("gp_height");
      int _ysize = this.configuration.getYsize();
      final Procedure1<Integer> _function_1 = new Procedure1<Integer>() {
        @Override
        public void apply(final Integer it) {
          GameParameterPartScreen.this.configuration.setYsize((it).intValue());
        }
      };
      this.table.addSlider(_get_1, CommonConstants.MIN_BOARD_SIZE, CommonConstants.MAX_BOARD_SIZE, _ysize, _function_1);
      String _xifexpression = null;
      if (this.humans) {
        _xifexpression = "h";
      } else {
        _xifexpression = "c";
      }
      String _plus = ("gp_nlayers_" + _xifexpression);
      String _get_2 = I18N.get(_plus);
      int _xifexpression_1 = (int) 0;
      if (this.humans) {
        _xifexpression_1 = 2;
      } else {
        _xifexpression_1 = 1;
      }
      int _xifexpression_2 = (int) 0;
      if (this.humans) {
        _xifexpression_2 = 2;
      } else {
        _xifexpression_2 = 1;
      }
      int _playerCount = this.configuration.getPlayerCount();
      int _max = Math.max(_xifexpression_2, _playerCount);
      final Procedure1<Integer> _function_2 = new Procedure1<Integer>() {
        @Override
        public void apply(final Integer it) {
          GameParameterPartScreen.this.configuration.setPlayerCount((it).intValue());
        }
      };
      this.table.addSlider(_get_2, _xifexpression_1, (CommonConstants.MAX_PLAYERS - 1), _max, _function_2);
      final Procedure0 _function_3 = new Procedure0() {
        @Override
        public void apply() {
          GameParameterPartScreen.this.switchToOptions();
        }
      };
      this.widgets.addButton(Layout.Options, Icons.Options, _function_3);
      final Procedure0 _function_4 = new Procedure0() {
        @Override
        public void apply() {
          ScreenSwitcher _switcher = GameParameterPartScreen.this.parentScreen.getSwitcher();
          _switcher.switchToScreen(Screens.Main);
        }
      };
      this.widgets.addButton(Layout.Back, Icons.Back, _function_4);
      _xblockexpression = this.widgets.addButton(Layout.Game_Para_START, Icons.Start, startProcedure);
    }
    return _xblockexpression;
  }
  
  public void switchToOptions() {
    this.table.finish();
    ComponentLookup _instance = ComponentLookup.getInstance();
    ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
    _component.save();
    ScreenSwitcher _switcher = this.parentScreen.getSwitcher();
    Screens _type = this.parentScreen.getType();
    _switcher.switchToScreen(Screens.Options, _type);
  }
  
  public void finish() {
    this.table.finish();
  }
  
  public GameBaseParametersImpl getGameParameters() {
    ComponentLookup _instance = ComponentLookup.getInstance();
    ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
    GlobalOptionsData _globalOptions = _component.getGlobalOptions();
    String name = _globalOptions.getName();
    boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(name);
    if (_isNullOrEmpty) {
      String _get = I18N.get("gp_unknown");
      name = _get;
    }
    if (this.humans) {
      int _playerCount = this.configuration.getPlayerCount();
      int _xsize = this.configuration.getXsize();
      int _ysize = this.configuration.getYsize();
      return new GameBaseParametersImpl(0, _playerCount, _xsize, _ysize, name);
    } else {
      int _playerCount_1 = this.configuration.getPlayerCount();
      int _xsize_1 = this.configuration.getXsize();
      int _ysize_1 = this.configuration.getYsize();
      return new GameBaseParametersImpl(_playerCount_1, 1, _xsize_1, _ysize_1, name);
    }
  }
}
