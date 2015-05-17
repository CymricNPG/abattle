package net.npg.abattle.client.lan;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import java.net.InetAddress;
import java.util.Collection;
import net.npg.abattle.client.GameBaseParameters;
import net.npg.abattle.client.lan.ClientGameEnvironment;
import net.npg.abattle.client.lan.impl.ClientGameEnvironmentImpl;
import net.npg.abattle.client.local.LocalClient;
import net.npg.abattle.client.startup.Startup;
import net.npg.abattle.client.view.screens.GameScreen;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.GlobalOptionsData;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.error.ErrorMessage;
import net.npg.abattle.common.error.ExceptionCode;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.common.utils.impl.DisposeableImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

/**
 * start a game server and connect the local client to it
 * TODO SOLID violation
 */
@SuppressWarnings("all")
public class LANGameStartup extends DisposeableImpl {
  private GameBaseParameters model;
  
  private ClientGameEnvironment environment;
  
  public LANGameStartup(final GameBaseParameters model) {
    Validate.notNull(model);
    this.model = model;
  }
  
  private int gameId;
  
  private InetAddress ipAddr;
  
  public LANGameStartup(final int gameId, final InetAddress ipAddr) {
    this.gameId = gameId;
    this.ipAddr = ipAddr;
  }
  
  public void join() throws BaseException {
    ComponentLookup _instance = ComponentLookup.getInstance();
    ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
    GlobalOptionsData _globalOptions = _component.getGlobalOptions();
    String name = _globalOptions.getName();
    boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(name);
    if (_isNullOrEmpty) {
      name = "Unknown";
    }
    ClientGameEnvironmentImpl _l15remote = Startup.l15remote(name, this.ipAddr);
    this.environment = _l15remote;
    final ErrorMessage error = this.environment.joinGame(this.gameId);
    boolean _isFailed = error.isFailed();
    if (_isFailed) {
      ExceptionCode _errorCode = error.getErrorCode();
      String _errorMessage = error.getErrorMessage();
      throw new BaseException(_errorCode, _errorMessage);
    }
  }
  
  public void create() throws BaseException {
    Validate.notNull(this.model);
    Startup.l10();
    String _name = this.model.getName();
    ClientGameEnvironmentImpl _l15local = Startup.l15local(_name);
    this.environment = _l15local;
    int _xSize = this.model.getXSize();
    int _ySize = this.model.getYSize();
    IntPoint _from = IntPoint.from(_xSize, _ySize);
    int _humanPlayers = this.model.getHumanPlayers();
    final ErrorMessage error = this.environment.createGame(_from, _humanPlayers);
    boolean _isFailed = error.isFailed();
    if (_isFailed) {
      ExceptionCode _errorCode = error.getErrorCode();
      String _errorMessage = error.getErrorMessage();
      throw new BaseException(_errorCode, _errorMessage);
    }
  }
  
  public void leave() {
    this.environment.leave();
    this.environment.dispose();
  }
  
  public boolean checkGameStart() {
    return this.environment.checkGameStart();
  }
  
  public Optional<Integer> checkCountdown() {
    return this.environment.getRemainingCount();
  }
  
  public GameScreen start(final ScreenSwitcher switcher) {
    try {
      final LocalClient runnable = new LocalClient(switcher);
      return runnable.run(this.environment);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public Collection<ClientPlayer> getPlayers() {
    Collection<ClientPlayer> _xblockexpression = null;
    {
      boolean _notEquals = (!Objects.equal(this.environment, null));
      assert _notEquals;
      ClientGame _game = this.environment.getGame();
      _xblockexpression = _game.getPlayers();
    }
    return _xblockexpression;
  }
  
  public void ping() {
    this.environment.ping();
  }
  
  @Override
  public void dispose() {
    super.dispose();
    boolean _notEquals = (!Objects.equal(this.environment, null));
    if (_notEquals) {
      this.environment.dispose();
    }
  }
}
