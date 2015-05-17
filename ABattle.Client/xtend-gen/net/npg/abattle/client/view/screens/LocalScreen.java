package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.common.base.Objects;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import net.npg.abattle.client.lan.LANGameStartup;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.GameSearcher;
import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.screens.Layout;
import net.npg.abattle.client.view.screens.MyStage;
import net.npg.abattle.client.view.screens.MyTextButton;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.Widgets;
import net.npg.abattle.common.i18n.I18N;
import net.npg.abattle.communication.network.data.NetworkGameInfo;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;

@SuppressWarnings("all")
public class LocalScreen extends BasicScreen {
  private ArrayList<NetworkGameInfo> games;
  
  private GameSearcher gameSearcher;
  
  private Table table;
  
  private final static float CELLPAD = 4;
  
  @Override
  public void create() {
    ArrayList<NetworkGameInfo> _newArrayList = CollectionLiterals.<NetworkGameInfo>newArrayList();
    this.games = _newArrayList;
    this.initGames();
    Table _table = new Table();
    this.table = _table;
    this.fillTable();
    final ScrollPane scrollpane = new ScrollPane(this.table);
    scrollpane.setSize(Layout.LOCAL_SIZE.x, Layout.LOCAL_SIZE.y);
    scrollpane.setPosition(Layout.LOCAL_SCROLL.x, Layout.LOCAL_SCROLL.y);
    MyStage _stage = this.getStage();
    _stage.addActor(scrollpane);
    Widgets _widgets = this.getWidgets();
    final Procedure0 _function = new Procedure0() {
      @Override
      public void apply() {
        LocalScreen.this.exitScreen(Screens.Main);
      }
    };
    _widgets.addButton(Layout.Back, Icons.Back, _function);
    Widgets _widgets_1 = this.getWidgets();
    final Procedure0 _function_1 = new Procedure0() {
      @Override
      public void apply() {
        LocalScreen.this.exitScreen(Screens.New);
      }
    };
    _widgets_1.addButton(Layout.LOCAL_NEW, Icons.New, _function_1);
  }
  
  private void exitScreen(final Screens to) {
    boolean _notEquals = (!Objects.equal(this.gameSearcher, null));
    if (_notEquals) {
      this.gameSearcher.setExit(true);
      this.gameSearcher = null;
    }
    ScreenSwitcher _switcher = this.getSwitcher();
    _switcher.switchToScreen(to);
  }
  
  public void fillTable() {
    this.table.clear();
    Widgets _widgets = this.getWidgets();
    Label _createLabel = _widgets.createLabel("IP");
    Cell<Label> _add = this.table.<Label>add(_createLabel);
    _add.pad(LocalScreen.CELLPAD);
    Widgets _widgets_1 = this.getWidgets();
    Label _createLabel_1 = _widgets_1.createLabel("Name");
    Cell<Label> _add_1 = this.table.<Label>add(_createLabel_1);
    _add_1.pad(LocalScreen.CELLPAD);
    Widgets _widgets_2 = this.getWidgets();
    Label _createLabel_2 = _widgets_2.createLabel(" # / # ");
    Cell<Label> _add_2 = this.table.<Label>add(_createLabel_2);
    _add_2.pad(LocalScreen.CELLPAD);
    Widgets _widgets_3 = this.getWidgets();
    Label _createLabel_3 = _widgets_3.createLabel("");
    Cell<Label> _add_3 = this.table.<Label>add(_createLabel_3);
    _add_3.pad(LocalScreen.CELLPAD);
    this.table.row();
    for (final NetworkGameInfo game : this.games) {
      {
        Widgets _widgets_4 = this.getWidgets();
        InetAddress _ipAddr = game.getIpAddr();
        String _hostAddress = _ipAddr.getHostAddress();
        Label _createLabel_4 = _widgets_4.createLabel(_hostAddress);
        Cell<Label> _add_4 = this.table.<Label>add(_createLabel_4);
        _add_4.pad(LocalScreen.CELLPAD);
        Widgets _widgets_5 = this.getWidgets();
        String _gameName = game.getGameName();
        Label _createLabel_5 = _widgets_5.createLabel(_gameName);
        Cell<Label> _add_5 = this.table.<Label>add(_createLabel_5);
        _add_5.pad(LocalScreen.CELLPAD);
        Widgets _widgets_6 = this.getWidgets();
        int _currentPlayer = game.getCurrentPlayer();
        String _plus = (" " + Integer.valueOf(_currentPlayer));
        String _plus_1 = (_plus + " / ");
        int _maxPlayer = game.getMaxPlayer();
        String _plus_2 = (_plus_1 + Integer.valueOf(_maxPlayer));
        String _plus_3 = (_plus_2 + " ");
        Label _createLabel_6 = _widgets_6.createLabel(_plus_3);
        Cell<Label> _add_6 = this.table.<Label>add(_createLabel_6);
        _add_6.pad(LocalScreen.CELLPAD);
        Widgets _widgets_7 = this.getWidgets();
        Icons _xifexpression = null;
        int _gameId = game.getGameId();
        boolean _equals = (_gameId == (-1));
        if (_equals) {
          _xifexpression = Icons.Join;
        } else {
          _xifexpression = Icons.JoinActivate;
        }
        final Procedure0 _function = new Procedure0() {
          @Override
          public void apply() {
            LocalScreen.this.startGame(game);
          }
        };
        boolean _xifexpression_1 = false;
        int _gameId_1 = game.getGameId();
        boolean _equals_1 = (_gameId_1 == (-1));
        if (_equals_1) {
          _xifexpression_1 = true;
        } else {
          _xifexpression_1 = false;
        }
        MyTextButton _createButton = _widgets_7.createButton(_xifexpression, _function, _xifexpression_1);
        Cell<MyTextButton> _add_7 = this.table.<MyTextButton>add(_createButton);
        _add_7.pad(LocalScreen.CELLPAD);
        this.table.row();
      }
    }
  }
  
  private void startGame(final NetworkGameInfo game) {
    try {
      int _gameId = game.getGameId();
      boolean _lessThan = (_gameId < 0);
      if(_lessThan) return;;
      this.gameSearcher.setExit(true);
      this.gameSearcher = null;
      int _gameId_1 = game.getGameId();
      InetAddress _ipAddr = game.getIpAddr();
      final LANGameStartup startup = new LANGameStartup(_gameId_1, _ipAddr);
      startup.join();
      ScreenSwitcher _switcher = this.getSwitcher();
      _switcher.switchToScreen(Screens.Waiting, startup);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void startNetworkBoradcast() {
    boolean _equals = Objects.equal(this.gameSearcher, null);
    if (_equals) {
      GameSearcher _gameSearcher = new GameSearcher();
      this.gameSearcher = _gameSearcher;
      Thread _thread = new Thread(this.gameSearcher);
      _thread.start();
    }
  }
  
  private boolean initGames() {
    InetAddress _loopbackAddress = InetAddress.getLoopbackAddress();
    String _get = I18N.get("local_search");
    NetworkGameInfo _networkGameInfo = new NetworkGameInfo(_loopbackAddress, _get, 0, 8, (-1));
    return this.games.add(_networkGameInfo);
  }
  
  @Override
  public void render() {
    this.startNetworkBoradcast();
    this.queryGames();
  }
  
  private void queryGames() {
    boolean _isNewDataAvailable = this.gameSearcher.isNewDataAvailable();
    boolean _not = (!_isNewDataAvailable);
    if(_not) return;;
    List<NetworkGameInfo> newGames = this.gameSearcher.getGames();
    boolean _isEmpty = newGames.isEmpty();
    if(_isEmpty) return;;
    this.games.clear();
    this.games.addAll(newGames);
    this.fillTable();
  }
  
  @Override
  public void backButton() {
    ScreenSwitcher _switcher = this.getSwitcher();
    _switcher.switchToScreen(Screens.Main);
  }
  
  @Override
  public Screens getType() {
    return Screens.Local;
  }
}
