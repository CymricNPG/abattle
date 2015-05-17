package net.npg.abattle.client.startup;

import com.google.common.base.Optional;
import java.net.InetAddress;
import java.util.List;
import net.npg.abattle.client.asset.AssetManager;
import net.npg.abattle.client.asset.impl.AssetManagerFactory;
import net.npg.abattle.client.commands.BoardUpdateCommandProcessor;
import net.npg.abattle.client.commands.GameCountdownProcessor;
import net.npg.abattle.client.commands.GameFinishedProcessor;
import net.npg.abattle.client.commands.GameUpdateProcessor;
import net.npg.abattle.client.commands.InitBoardCommandProcessor;
import net.npg.abattle.client.commands.PingProcessor;
import net.npg.abattle.client.commands.PlayerJoinedProcessor;
import net.npg.abattle.client.commands.PlayerLeftProcessor;
import net.npg.abattle.client.commands.ServerReplyProcessor;
import net.npg.abattle.client.lan.ClientGameEnvironment;
import net.npg.abattle.client.lan.impl.ClientGameEnvironmentImpl;
import net.npg.abattle.client.view.boardscene.BoardSceneComponentImpl;
import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.selection.impl.SelectionComponentImpl;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.component.ExternalRegisterComponent;
import net.npg.abattle.common.configuration.impl.ConfigurationComponentImpl;
import net.npg.abattle.common.error.ErrorHandler;
import net.npg.abattle.common.error.impl.ErrorComponentImpl;
import net.npg.abattle.common.i18n.impl.I18NComponentImpl;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.communication.command.CommandQueueClient;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.GameCommand;
import net.npg.abattle.communication.command.commands.BoardUpdateCommand;
import net.npg.abattle.communication.command.commands.ChangeLinkCommand;
import net.npg.abattle.communication.command.commands.GameCountdownCommand;
import net.npg.abattle.communication.command.commands.GameFinishedCommand;
import net.npg.abattle.communication.command.commands.GameUpdateCommand;
import net.npg.abattle.communication.command.commands.InitBoardCommand;
import net.npg.abattle.communication.command.commands.LeaveCommand;
import net.npg.abattle.communication.command.commands.PingCommand;
import net.npg.abattle.communication.command.commands.PlayerJoinedCommand;
import net.npg.abattle.communication.command.commands.PlayerLeftCommand;
import net.npg.abattle.communication.command.commands.ServerReplyCommand;
import net.npg.abattle.communication.command.impl.CommandQueueImpl;
import net.npg.abattle.communication.network.NetworkClient;
import net.npg.abattle.communication.network.NetworkComponent;
import net.npg.abattle.communication.network.NetworkServer;
import net.npg.abattle.communication.network.impl.GameCommandHandler;
import net.npg.abattle.communication.network.impl.LoginHandler;
import net.npg.abattle.communication.network.impl.NetworkComponentImpl;
import net.npg.abattle.communication.network.impl.NetworkServerProcessor;
import net.npg.abattle.server.command.ChangeLinkCommandProcessor;
import net.npg.abattle.server.command.LeaveCommandProcessor;
import net.npg.abattle.server.service.impl.ServerServiceImpl;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class Startup {
  /**
   * components before any gui is started
   */
  public static SelectionComponentImpl l0(final ErrorHandler errorHandler, final ExternalRegisterComponent externalRegisterComponent) {
    SelectionComponentImpl _xblockexpression = null;
    {
      boolean _isInitialized = ComponentLookup.isInitialized();
      boolean _not = (!_isInitialized);
      if (_not) {
        final ComponentLookup lookup = ComponentLookup.createInstance();
        final ConfigurationComponentImpl configuration = new ConfigurationComponentImpl();
        lookup.<ConfigurationComponentImpl>registerComponent(configuration);
      }
      final ComponentLookup lookup_1 = ComponentLookup.getInstance();
      I18NComponentImpl _i18NComponentImpl = new I18NComponentImpl();
      lookup_1.<I18NComponentImpl>registerComponent(_i18NComponentImpl);
      NetworkComponentImpl _networkComponentImpl = new NetworkComponentImpl();
      lookup_1.<NetworkComponentImpl>registerComponent(_networkComponentImpl);
      Startup.startAssetManager();
      BoardSceneComponentImpl _boardSceneComponentImpl = new BoardSceneComponentImpl();
      lookup_1.<BoardSceneComponentImpl>registerComponent(_boardSceneComponentImpl);
      ErrorComponentImpl _errorComponentImpl = new ErrorComponentImpl();
      final ErrorComponentImpl errorComponent = lookup_1.<ErrorComponentImpl>registerComponent(_errorComponentImpl);
      errorComponent.registerErrorHandler(errorHandler);
      externalRegisterComponent.registerComponents(lookup_1);
      SelectionComponentImpl _selectionComponentImpl = new SelectionComponentImpl();
      _xblockexpression = lookup_1.<SelectionComponentImpl>registerComponent(_selectionComponentImpl);
    }
    return _xblockexpression;
  }
  
  private static void startAssetManager() {
    final AssetManager assets = AssetManagerFactory.create();
    Icons[] _values = Icons.values();
    final Function1<Icons, String> _function = new Function1<Icons, String>() {
      @Override
      public String apply(final Icons it) {
        return it.filename;
      }
    };
    List<String> _map = ListExtensions.<Icons, String>map(((List<Icons>)Conversions.doWrapArray(_values)), _function);
    assets.loadIcons(_map);
    assets.loadFonts();
  }
  
  public static void restart0() {
    final ComponentLookup lookup = ComponentLookup.getInstance();
    lookup.restart();
    NetworkComponentImpl _networkComponentImpl = new NetworkComponentImpl();
    lookup.<NetworkComponentImpl>registerComponent(_networkComponentImpl, NetworkComponent.class);
    Startup.startAssetManager();
  }
  
  /**
   * Start a network server (if not runnin)
   */
  public static void l10() {
    try {
      ComponentLookup _instance = ComponentLookup.getInstance();
      final NetworkComponent networkComponent = _instance.<NetworkComponent>getComponent(NetworkComponent.class);
      boolean _isServerRunning = networkComponent.isServerRunning();
      if(_isServerRunning) return;;
      final NetworkServer server = networkComponent.createServer();
      final CommandQueueImpl serverQueue = new CommandQueueImpl(CommandType.TOSERVER, null);
      final ServerServiceImpl serverService = new ServerServiceImpl(serverQueue);
      LoginHandler _loginHandler = new LoginHandler(serverService);
      server.addHandler(_loginHandler);
      GameCommandHandler _gameCommandHandler = new GameCommandHandler(serverQueue);
      server.addHandler(_gameCommandHandler);
      Thread.sleep(200);
      server.registerService(serverService, ServerServiceImpl.SERVER_SERVICE_ID);
      Startup.registerServerProcessors(serverQueue, serverService);
      NetworkServerProcessor _networkServerProcessor = new NetworkServerProcessor(server);
      Optional<Class<GameCommand>> _absent = Optional.<Class<GameCommand>>absent();
      serverQueue.<GameCommand>registerCommandProcessor(_networkServerProcessor, _absent, CommandType.TOCLIENT);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * create client and login to localhost
   */
  public static ClientGameEnvironmentImpl l15local(final String name) {
    ClientGameEnvironmentImpl _xblockexpression = null;
    {
      ComponentLookup _instance = ComponentLookup.getInstance();
      NetworkComponent _component = _instance.<NetworkComponent>getComponent(NetworkComponent.class);
      final NetworkClient client = _component.createClient();
      client.connectToLocalhost();
      final ClientGameEnvironmentImpl environment = new ClientGameEnvironmentImpl(client);
      environment.login(name);
      _xblockexpression = environment;
    }
    return _xblockexpression;
  }
  
  /**
   * create client and login to remote host
   */
  public static ClientGameEnvironmentImpl l15remote(final String name, final InetAddress address) {
    ClientGameEnvironmentImpl _xblockexpression = null;
    {
      ComponentLookup _instance = ComponentLookup.getInstance();
      NetworkComponent _component = _instance.<NetworkComponent>getComponent(NetworkComponent.class);
      final NetworkClient client = _component.createClient();
      client.connectTo(address);
      final ClientGameEnvironmentImpl environment = new ClientGameEnvironmentImpl(client);
      environment.login(name);
      _xblockexpression = environment;
    }
    return _xblockexpression;
  }
  
  /**
   * start local server service and register command processors
   * @param serverCommandType to which commandtype the server should listen
   */
  public static ServerServiceImpl l20local(final CommandQueueServer serverQueue) {
    ServerServiceImpl _xblockexpression = null;
    {
      final ServerServiceImpl serverService = new ServerServiceImpl(serverQueue);
      Startup.registerServerProcessors(serverQueue, serverService);
      _xblockexpression = serverService;
    }
    return _xblockexpression;
  }
  
  private static void registerServerProcessors(final CommandQueueServer serverQueue, final ServerServiceImpl serverService) {
    ChangeLinkCommandProcessor _changeLinkCommandProcessor = new ChangeLinkCommandProcessor(serverService);
    Optional<Class<ChangeLinkCommand>> _of = Optional.<Class<ChangeLinkCommand>>of(ChangeLinkCommand.class);
    serverQueue.<ChangeLinkCommand>registerCommandProcessor(_changeLinkCommandProcessor, _of, CommandType.TOSERVER);
    LeaveCommandProcessor _leaveCommandProcessor = new LeaveCommandProcessor(serverService);
    Optional<Class<LeaveCommand>> _of_1 = Optional.<Class<LeaveCommand>>of(LeaveCommand.class);
    serverQueue.<LeaveCommand>registerCommandProcessor(_leaveCommandProcessor, _of_1, CommandType.TOSERVER);
  }
  
  /**
   * start client command processors
   * @param serverCommandType to which commandtype the client should listen
   */
  public static void l30(final ClientGameEnvironment gameEnvironment) {
    final CommandQueueClient commandQueue = gameEnvironment.getCommandQueue();
    final ClientGame game = gameEnvironment.getGame();
    BoardUpdateCommandProcessor _boardUpdateCommandProcessor = new BoardUpdateCommandProcessor(game, gameEnvironment);
    Optional<Class<BoardUpdateCommand>> _of = Optional.<Class<BoardUpdateCommand>>of(BoardUpdateCommand.class);
    commandQueue.<BoardUpdateCommand>registerCommandProcessor(_boardUpdateCommandProcessor, _of, CommandType.TOCLIENT);
    InitBoardCommandProcessor _initBoardCommandProcessor = new InitBoardCommandProcessor(game, gameEnvironment);
    Optional<Class<InitBoardCommand>> _of_1 = Optional.<Class<InitBoardCommand>>of(InitBoardCommand.class);
    commandQueue.<InitBoardCommand>registerCommandProcessor(_initBoardCommandProcessor, _of_1, CommandType.TOCLIENT);
    ServerReplyProcessor _serverReplyProcessor = new ServerReplyProcessor();
    Optional<Class<ServerReplyCommand>> _of_2 = Optional.<Class<ServerReplyCommand>>of(ServerReplyCommand.class);
    commandQueue.<ServerReplyCommand>registerCommandProcessor(_serverReplyProcessor, _of_2, CommandType.TOCLIENT);
    GameUpdateProcessor _gameUpdateProcessor = new GameUpdateProcessor(game);
    Optional<Class<GameUpdateCommand>> _of_3 = Optional.<Class<GameUpdateCommand>>of(GameUpdateCommand.class);
    commandQueue.<GameUpdateCommand>registerCommandProcessor(_gameUpdateProcessor, _of_3, CommandType.TOCLIENT);
    PlayerJoinedProcessor _playerJoinedProcessor = new PlayerJoinedProcessor(game);
    Optional<Class<PlayerJoinedCommand>> _of_4 = Optional.<Class<PlayerJoinedCommand>>of(PlayerJoinedCommand.class);
    commandQueue.<PlayerJoinedCommand>registerCommandProcessor(_playerJoinedProcessor, _of_4, CommandType.TOCLIENT);
    PlayerLeftProcessor _playerLeftProcessor = new PlayerLeftProcessor(game);
    Optional<Class<PlayerLeftCommand>> _of_5 = Optional.<Class<PlayerLeftCommand>>of(PlayerLeftCommand.class);
    commandQueue.<PlayerLeftCommand>registerCommandProcessor(_playerLeftProcessor, _of_5, CommandType.TOCLIENT);
    GameFinishedProcessor _gameFinishedProcessor = new GameFinishedProcessor(game);
    Optional<Class<GameFinishedCommand>> _of_6 = Optional.<Class<GameFinishedCommand>>of(GameFinishedCommand.class);
    commandQueue.<GameFinishedCommand>registerCommandProcessor(_gameFinishedProcessor, _of_6, CommandType.TOCLIENT);
    GameCountdownProcessor _gameCountdownProcessor = new GameCountdownProcessor(game, gameEnvironment);
    Optional<Class<GameCountdownCommand>> _of_7 = Optional.<Class<GameCountdownCommand>>of(GameCountdownCommand.class);
    commandQueue.<GameCountdownCommand>registerCommandProcessor(_gameCountdownProcessor, _of_7, CommandType.TOCLIENT);
    PingProcessor _pingProcessor = new PingProcessor(game, gameEnvironment);
    Optional<Class<PingCommand>> _of_8 = Optional.<Class<PingCommand>>of(PingCommand.class);
    commandQueue.<PingCommand>registerCommandProcessor(_pingProcessor, _of_8, CommandType.TOCLIENT);
  }
}
