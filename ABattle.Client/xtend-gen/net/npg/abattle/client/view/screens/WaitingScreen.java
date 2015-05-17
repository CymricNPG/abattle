package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.common.base.Optional;
import java.util.Collection;
import net.npg.abattle.client.lan.LANGameStartup;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.GameScreen;
import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.screens.Layout;
import net.npg.abattle.client.view.screens.MyStage;
import net.npg.abattle.client.view.screens.ParameterScreen;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.Widgets;
import net.npg.abattle.common.i18n.I18N;
import net.npg.abattle.common.model.Color;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.utils.ColorConvert;
import net.npg.abattle.communication.CommunicationConstants;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class WaitingScreen extends BasicScreen implements ParameterScreen<LANGameStartup> {
  @Accessors
  private LANGameStartup parameter;
  
  private Table table;
  
  private Label waitLabel;
  
  private long lastTime = 0L;
  
  @Override
  public void create() {
    Table _table = new Table();
    this.table = _table;
    Widgets _widgets = this.getWidgets();
    String _get = I18N.get("wait_name");
    Label _createLabel = _widgets.createLabel(_get);
    this.table.<Label>add(_createLabel);
    this.table.row();
    final ScrollPane scrollpane = new ScrollPane(this.table);
    scrollpane.setSize(Layout.LOCAL_SIZE.x, Layout.LOCAL_SIZE.y);
    scrollpane.setPosition(Layout.LOCAL_SCROLL.x, Layout.LOCAL_SCROLL.y);
    MyStage _stage = this.getStage();
    _stage.addActor(scrollpane);
    Widgets _widgets_1 = this.getWidgets();
    final Procedure0 _function = new Procedure0() {
      @Override
      public void apply() {
        WaitingScreen.this.leaveGame();
      }
    };
    _widgets_1.addButton(Layout.Back, Icons.Back, _function);
    Widgets _widgets_2 = this.getWidgets();
    String _get_1 = I18N.get("wait_msg");
    Label _addLabel = _widgets_2.addLabel(Layout.WAIT_START.x, Layout.WAIT_START.y, _get_1);
    this.waitLabel = _addLabel;
  }
  
  @Override
  public void render() {
    this.updateTable();
    this.checkGameStart();
    this.addWaiting();
    this.pingServer();
  }
  
  public void pingServer() {
    long _currentTimeMillis = System.currentTimeMillis();
    boolean _greaterThan = (_currentTimeMillis > (this.lastTime + 1000L));
    if (_greaterThan) {
      try {
        long _currentTimeMillis_1 = System.currentTimeMillis();
        this.lastTime = _currentTimeMillis_1;
        this.parameter.ping();
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception e = (Exception)_t;
          String _message = e.getMessage();
          CommunicationConstants.LOG.error(_message, e);
          String _message_1 = e.getMessage();
          this.errorGame(_message_1);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
  }
  
  public void errorGame(final String errorMessage) {
    ScreenSwitcher _switcher = this.getSwitcher();
    _switcher.switchToScreen(Screens.Error, errorMessage);
  }
  
  public void addWaiting() {
    Optional<Integer> _checkCountdown = this.parameter.checkCountdown();
    boolean _isPresent = _checkCountdown.isPresent();
    if (_isPresent) {
      Optional<Integer> _checkCountdown_1 = this.parameter.checkCountdown();
      Integer _get = _checkCountdown_1.get();
      String _string = _get.toString();
      String _format = I18N.format("wait_start", _string);
      this.waitLabel.setText(_format);
    }
  }
  
  private void updateTable() {
    final Collection<ClientPlayer> players = this.parameter.getPlayers();
    this.table.clear();
    Widgets _widgets = this.getWidgets();
    String _get = I18N.get("wait_name");
    Label _createLabel = _widgets.createLabel(_get);
    this.table.<Label>add(_createLabel);
    this.table.row();
    for (final ClientPlayer player : players) {
      {
        Widgets _widgets_1 = this.getWidgets();
        String _name = player.getName();
        Color _color = player.getColor();
        com.badlogic.gdx.graphics.Color _convert = ColorConvert.convert(_color);
        Label _createLabel_1 = _widgets_1.createLabel(_name, _convert);
        this.table.<Label>add(_createLabel_1);
        this.table.row();
      }
    }
  }
  
  private void checkGameStart() {
    boolean _checkGameStart = this.parameter.checkGameStart();
    if (_checkGameStart) {
      ScreenSwitcher _switcher = this.getSwitcher();
      final GameScreen screen = this.parameter.start(_switcher);
      ScreenSwitcher _switcher_1 = this.getSwitcher();
      _switcher_1.switchToScreen(screen);
    }
  }
  
  private void leaveGame() {
    this.parameter.leave();
    ScreenSwitcher _switcher = this.getSwitcher();
    _switcher.switchToScreen(Screens.Main);
  }
  
  @Override
  public void backButton() {
    ScreenSwitcher _switcher = this.getSwitcher();
    _switcher.switchToScreen(Screens.Main);
  }
  
  @Override
  public Screens getType() {
    return Screens.Waiting;
  }
  
  @Pure
  public LANGameStartup getParameter() {
    return this.parameter;
  }
  
  public void setParameter(final LANGameStartup parameter) {
    this.parameter = parameter;
  }
}
