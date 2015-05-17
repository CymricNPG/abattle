package net.npg.abattle.communication;

import com.google.common.base.Optional;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.npg.abattle.client.lan.ClientGameEnvironment;
import net.npg.abattle.client.lan.impl.ClientGameEnvironmentImpl;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.impl.ConfigurationComponentImpl;
import net.npg.abattle.common.error.ErrorMessage;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.client.impl.ClientModelComponentImpl;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.GameCommand;
import net.npg.abattle.communication.command.commands.ChangeLinkCommand;
import net.npg.abattle.communication.command.impl.CommandQueueImpl;
import net.npg.abattle.communication.network.NetworkClient;
import net.npg.abattle.communication.network.NetworkComponent;
import net.npg.abattle.communication.network.NetworkServer;
import net.npg.abattle.communication.network.data.NetworkGameInfo;
import net.npg.abattle.communication.network.impl.GameCommandHandler;
import net.npg.abattle.communication.network.impl.LoginHandler;
import net.npg.abattle.communication.network.impl.LoginResultHandler;
import net.npg.abattle.communication.network.impl.NetworkComponentImpl;
import net.npg.abattle.communication.network.impl.NetworkServerProcessor;
import net.npg.abattle.communication.service.ServerService;
import net.npg.abattle.server.command.ChangeLinkCommandProcessor;
import net.npg.abattle.server.service.impl.ServerServiceImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class PlayerConnectionTest {
  @Test
  public void testPlayerConnection() {
    try {
      this.baseInit();
      final ClientGameEnvironmentImpl environment1 = this.doPlayer1();
      final ClientGameEnvironmentImpl environment2 = this.doPlayer2();
      Thread.sleep(1000);
      this.processCommandQueue(environment1);
      this.processCommandQueue(environment2);
      this.checkEnvironment(environment1);
      this.checkEnvironment(environment2);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private Object processCommandQueue(final ClientGameEnvironment env) {
    return null;
  }
  
  private void checkEnvironment(final ClientGameEnvironment environment) {
    final ClientGame game2 = environment.getGame();
    Collection<ClientPlayer> _players = game2.getPlayers();
    int _size = _players.size();
    Assert.assertEquals(2, _size);
  }
  
  private void baseInit() {
    ComponentLookup.createInstance();
    ComponentLookup _instance = ComponentLookup.getInstance();
    ConfigurationComponentImpl _configurationComponentImpl = new ConfigurationComponentImpl();
    _instance.<ConfigurationComponentImpl>registerComponent(_configurationComponentImpl);
    final CommandQueueImpl cqs = new CommandQueueImpl(CommandType.TOSERVER, null);
    ComponentLookup _instance_1 = ComponentLookup.getInstance();
    NetworkComponentImpl _networkComponentImpl = new NetworkComponentImpl();
    final NetworkComponentImpl networkComponent = _instance_1.<NetworkComponentImpl>registerComponent(_networkComponentImpl, NetworkComponent.class);
    final ClientModelComponentImpl clientIdRegister = new ClientModelComponentImpl();
    final ServerServiceImpl serverService = new ServerServiceImpl(cqs);
    final NetworkServer server = networkComponent.createServer();
    LoginHandler _loginHandler = new LoginHandler(serverService);
    server.addHandler(_loginHandler);
    LoginResultHandler _loginResultHandler = new LoginResultHandler(clientIdRegister);
    server.addHandler(_loginResultHandler);
    GameCommandHandler _gameCommandHandler = new GameCommandHandler(cqs);
    server.addHandler(_gameCommandHandler);
    server.registerService(serverService, ServerServiceImpl.SERVER_SERVICE_ID);
    this.registerServerProcessors(server, serverService, cqs);
  }
  
  private void registerServerProcessors(final NetworkServer server, final ServerService serverService, final CommandQueueServer serverQueue) {
    ChangeLinkCommandProcessor _changeLinkCommandProcessor = new ChangeLinkCommandProcessor(serverService);
    Optional<Class<ChangeLinkCommand>> _of = Optional.<Class<ChangeLinkCommand>>of(ChangeLinkCommand.class);
    serverQueue.<ChangeLinkCommand>registerCommandProcessor(_changeLinkCommandProcessor, _of, CommandType.TOSERVER);
    NetworkServerProcessor _networkServerProcessor = new NetworkServerProcessor(server);
    Optional<Class<GameCommand>> _absent = Optional.<Class<GameCommand>>absent();
    serverQueue.<GameCommand>registerCommandProcessor(_networkServerProcessor, _absent, CommandType.TOCLIENT);
  }
  
  private ClientGameEnvironmentImpl doPlayer1() {
    ClientGameEnvironmentImpl _xblockexpression = null;
    {
      ComponentLookup _instance = ComponentLookup.getInstance();
      NetworkComponent _component = _instance.<NetworkComponent>getComponent(NetworkComponent.class);
      final NetworkClient client1 = _component.createClient();
      boolean _discoverHost = client1.discoverHost();
      boolean _not = (!_discoverHost);
      if (_not) {
        throw new RuntimeException("cannot find local host!");
      }
      final ClientGameEnvironmentImpl environment = new ClientGameEnvironmentImpl(client1);
      environment.login("Test");
      IntPoint _from = IntPoint.from(10, 10);
      final ErrorMessage error = environment.createGame(_from, 2);
      boolean _isFailed = error.isFailed();
      Assert.assertFalse(_isFailed);
      _xblockexpression = environment;
    }
    return _xblockexpression;
  }
  
  private ClientGameEnvironmentImpl doPlayer2() {
    ClientGameEnvironmentImpl _xblockexpression = null;
    {
      ComponentLookup _instance = ComponentLookup.getInstance();
      NetworkComponent _component = _instance.<NetworkComponent>getComponent(NetworkComponent.class);
      final NetworkClient client2 = _component.createClient();
      final Optional<NetworkGameInfo> networkGame = client2.discoverHostAndGame();
      boolean _isPresent = networkGame.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        throw new RuntimeException("cannot find local host!");
      }
      final ClientGameEnvironmentImpl environment = new ClientGameEnvironmentImpl(client2);
      Optional<NetworkGameInfo> _foundGame = client2.getFoundGame();
      boolean _isPresent_1 = _foundGame.isPresent();
      Assert.assertTrue(_isPresent_1);
      environment.login("Test2");
      Optional<NetworkGameInfo> _foundGame_1 = client2.getFoundGame();
      NetworkGameInfo _get = _foundGame_1.get();
      int _gameId = _get.getGameId();
      final ErrorMessage result = environment.joinGame(_gameId);
      boolean _isFailed = result.isFailed();
      Assert.assertFalse(_isFailed);
      _xblockexpression = environment;
    }
    return _xblockexpression;
  }
  
  @After
  public void cleanUp() {
    this.interruptAllThreads();
  }
  
  private void interruptAllThreads() {
    Map<Thread, StackTraceElement[]> _allStackTraces = Thread.getAllStackTraces();
    Set<Thread> _keySet = _allStackTraces.keySet();
    for (final Thread thread : _keySet) {
      boolean _and = false;
      String _name = thread.getName();
      boolean _equals = "main".equals(_name);
      boolean _not = (!_equals);
      if (!_not) {
        _and = false;
      } else {
        String _name_1 = thread.getName();
        boolean _equals_1 = "finalizer".equals(_name_1);
        boolean _not_1 = (!_equals_1);
        _and = _not_1;
      }
      if (_and) {
        thread.interrupt();
      }
    }
  }
}
