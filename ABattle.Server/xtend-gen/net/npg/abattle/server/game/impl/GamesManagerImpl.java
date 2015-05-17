package net.npg.abattle.server.game.impl;

import com.google.common.base.Objects;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.Color;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.GameStatus;
import net.npg.abattle.common.model.impl.ColorImpl;
import net.npg.abattle.common.model.impl.GameConfigurationImpl;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.MyConcurrentHashMap;
import net.npg.abattle.common.utils.MyMap;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.commands.PlayerJoinedCommand;
import net.npg.abattle.communication.command.commands.PlayerLeftCommand;
import net.npg.abattle.communication.command.commands.PlayerLeftCommandBuilder;
import net.npg.abattle.communication.command.data.PlayerData;
import net.npg.abattle.server.ServerConstants;
import net.npg.abattle.server.ServerExceptionCode;
import net.npg.abattle.server.game.GameEnvironment;
import net.npg.abattle.server.game.GamesManager;
import net.npg.abattle.server.game.impl.BoardCreatorImpl;
import net.npg.abattle.server.game.impl.DataHelper;
import net.npg.abattle.server.game.impl.GameEnvironmentImpl;
import net.npg.abattle.server.game.impl.InitGameSender;
import net.npg.abattle.server.game.impl.PlayerColors;
import net.npg.abattle.server.game.impl.PreGameThread;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerPlayer;
import net.npg.abattle.server.model.impl.ServerPlayerImpl;
import net.npg.abattle.server.service.impl.StartGameThread;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class GamesManagerImpl implements GamesManager {
  /**
   * The max time (120s) until a pendimg game is purged
   */
  private final static long MAX_PENDING_TIME = (120 * 1000);
  
  private final MyMap<Integer, ServerPlayer> players;
  
  /**
   * The server games (key=id,value=ServerGame).
   */
  private final MyMap<Integer, GameEnvironment> serverGames;
  
  private final CommandQueueServer commandQueue;
  
  public GamesManagerImpl(final CommandQueueServer commandQueue) {
    Validate.notNull(commandQueue);
    this.commandQueue = commandQueue;
    MyConcurrentHashMap<Integer, GameEnvironment> _myConcurrentHashMap = new MyConcurrentHashMap<Integer, GameEnvironment>();
    this.serverGames = _myConcurrentHashMap;
    MyConcurrentHashMap<Integer, ServerPlayer> _myConcurrentHashMap_1 = new MyConcurrentHashMap<Integer, ServerPlayer>();
    this.players = _myConcurrentHashMap_1;
  }
  
  private boolean boardSizeExceedsLimits(final IntPoint size) {
    return ((((size.x < ServerConstants.MIN_XSIZE) || (size.x > ServerConstants.MAX_XSIZE)) || (size.y < ServerConstants.MIN_YSIZE)) || 
      (size.y > ServerConstants.MAX_YSIZE));
  }
  
  /**
   * Cleanup players which are connected to the service longer than 1h and not playing.
   */
  private void cleanupPlayers() {
    long _currentTimeMillis = System.currentTimeMillis();
    final long expireDate = (_currentTimeMillis - ((1000 * 60) * 60));
    Collection<ServerPlayer> _values = this.players.values();
    final Function1<ServerPlayer, Boolean> _function = new Function1<ServerPlayer, Boolean>() {
      @Override
      public Boolean apply(final ServerPlayer it) {
        long _creationTime = it.getCreationTime();
        return Boolean.valueOf((_creationTime < expireDate));
      }
    };
    Iterable<ServerPlayer> _filter = IterableExtensions.<ServerPlayer>filter(_values, _function);
    final Function1<ServerPlayer, Integer> _function_1 = new Function1<ServerPlayer, Integer>() {
      @Override
      public Integer apply(final ServerPlayer it) {
        return Integer.valueOf(it.getId());
      }
    };
    final Iterable<Integer> removeList = IterableExtensions.<ServerPlayer, Integer>map(_filter, _function_1);
    for (final Integer playerId : removeList) {
      this.players.remove(playerId);
    }
  }
  
  /**
   * Cleanup server games which are too old.
   */
  private void cleanupServerGames() {
    Collection<GameEnvironment> _pendingGames = this.getPendingGames();
    for (final GameEnvironment pendingGame : _pendingGames) {
      long _currentTimeMillis = System.currentTimeMillis();
      long _creationTime = pendingGame.getCreationTime();
      long _minus = (_currentTimeMillis - _creationTime);
      boolean _greaterThan = (_minus > GamesManagerImpl.MAX_PENDING_TIME);
      if (_greaterThan) {
        int _id = pendingGame.getId();
        this.serverGames.remove(Integer.valueOf(_id));
        pendingGame.dispose();
      }
    }
  }
  
  @Override
  public GameEnvironment createGame(final int maxPlayers, final IntPoint size) throws BaseException {
    boolean _notEquals = (!Objects.equal(this.serverGames, null));
    assert _notEquals;
    Validate.notNull(size);
    Validate.notNulls(Integer.valueOf(size.x), Integer.valueOf(size.y));
    boolean _boardSizeExceedsLimits = this.boardSizeExceedsLimits(size);
    if (_boardSizeExceedsLimits) {
      throw new BaseException(ServerExceptionCode.INVALID_BOARD_SIZE);
    }
    if (((maxPlayers < 2) || (maxPlayers > ServerConstants.MAX_PLAYERS))) {
      throw new BaseException(ServerExceptionCode.INCORRECT_NUMBER_OF_PLAYERS);
    }
    final GameConfiguration configuration = this.buildGameConfiguration(size);
    CheckModelElement _checker = configuration.getChecker();
    final BoardCreatorImpl boardCreator = new BoardCreatorImpl(size, _checker);
    final GameEnvironmentImpl serverGame = new GameEnvironmentImpl(maxPlayers, boardCreator, configuration);
    ServerGame _serverGame = serverGame.getServerGame();
    int _id = _serverGame.getId();
    this.serverGames.put(Integer.valueOf(_id), serverGame);
    this.cleanupServerGames();
    final PreGameThread preGameThread = new PreGameThread(this.commandQueue, serverGame);
    serverGame.attachThread(preGameThread);
    Thread _thread = new Thread(preGameThread);
    _thread.start();
    return serverGame;
  }
  
  private GameConfiguration buildGameConfiguration(final IntPoint size) {
    ComponentLookup _instance = ComponentLookup.getInstance();
    ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
    final GameConfigurationData configuration = _component.getGameConfiguration();
    final GameConfigurationImpl newConfiguration = new GameConfigurationImpl();
    newConfiguration.setConfiguration(configuration);
    newConfiguration.setXSize(size.x);
    newConfiguration.setYSize(size.y);
    return newConfiguration;
  }
  
  @Override
  public GameEnvironment getGame(final int gameId) {
    return this.serverGames.get(Integer.valueOf(gameId));
  }
  
  @Override
  public Collection<GameEnvironment> getPendingGames() {
    boolean _notEquals = (!Objects.equal(this.serverGames, null));
    assert _notEquals;
    Collection<GameEnvironment> _values = this.serverGames.values();
    final Function1<GameEnvironment, Boolean> _function = new Function1<GameEnvironment, Boolean>() {
      @Override
      public Boolean apply(final GameEnvironment it) {
        ServerGame _serverGame = it.getServerGame();
        GameStatus _status = _serverGame.getStatus();
        return Boolean.valueOf(Objects.equal(GameStatus.PENDING, _status));
      }
    };
    Iterable<GameEnvironment> _filter = IterableExtensions.<GameEnvironment>filter(_values, _function);
    return IterableExtensions.<GameEnvironment>toList(_filter);
  }
  
  @Override
  public ServerPlayer getPlayer(final int id) {
    return this.players.get(Integer.valueOf(id));
  }
  
  @Override
  public boolean joinGame(final GameEnvironment game, final ServerPlayer player) {
    ServerGame _serverGame = game.getServerGame();
    Collection<ServerPlayer> _players = _serverGame.getPlayers();
    boolean _contains = _players.contains(player);
    if (_contains) {
      int _id = player.getId();
      String _plus = ("Player already joined:" + Integer.valueOf(_id));
      ServerConstants.LOG.error(_plus);
      return false;
    }
    try {
      ServerGame _serverGame_1 = game.getServerGame();
      Color _freeColor = this.getFreeColor(player, _serverGame_1);
      player.setColor(_freeColor);
      /* game; */
      synchronized (game) {
        game.addNewPlayer(player);
      }
      this.notifyGamers(game);
    } catch (final Throwable _t) {
      if (_t instanceof BaseException) {
        final BaseException e = (BaseException)_t;
        String _message = e.getMessage();
        ServerConstants.LOG.error(_message, e);
        return false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    this.startGameIfPossible(game);
    return true;
  }
  
  private void startGameIfPossible(final GameEnvironment game) {
    final ServerGame serverGame = game.getServerGame();
    Collection<ServerPlayer> _players = serverGame.getPlayers();
    int _size = _players.size();
    int _maxPlayers = serverGame.getMaxPlayers();
    boolean _equals = (_size == _maxPlayers);
    if (_equals) {
      StartGameThread _startGameThread = new StartGameThread(game, this.commandQueue);
      Thread _thread = new Thread(_startGameThread);
      _thread.start();
    }
  }
  
  private void notifyGamers(final GameEnvironment game) {
    final PlayerData[] playersData = DataHelper.createPlayerData(game);
    int _id = game.getId();
    final PlayerJoinedCommand command = new PlayerJoinedCommand(playersData, true, _id);
    ServerGame _serverGame = game.getServerGame();
    Collection<ServerPlayer> _players = _serverGame.getPlayers();
    final Function1<ServerPlayer, Integer> _function = new Function1<ServerPlayer, Integer>() {
      @Override
      public Integer apply(final ServerPlayer it) {
        return Integer.valueOf(it.getId());
      }
    };
    Iterable<Integer> _map = IterableExtensions.<ServerPlayer, Integer>map(_players, _function);
    final List<Integer> destinations = IterableExtensions.<Integer>toList(_map);
    this.commandQueue.addCommand(command, CommandType.TOCLIENT, destinations);
  }
  
  @Override
  public void removeGame(final GameEnvironment game) {
    int _id = game.getId();
    this.serverGames.remove(Integer.valueOf(_id));
    game.dispose();
  }
  
  @Override
  public boolean leaveGame(final GameEnvironment game, final ServerPlayer player) {
    try {
      boolean success = false;
      /* game; */
      synchronized (game) {
        {
          ServerGame _serverGame = game.getServerGame();
          Collection<ServerPlayer> _players = _serverGame.getPlayers();
          boolean _contains = _players.contains(player);
          boolean _not = (!_contains);
          if (_not) {
            int _id = player.getId();
            String _plus = ("Player already left:" + Integer.valueOf(_id));
            String _plus_1 = (_plus + " game:");
            int _id_1 = game.getId();
            String _plus_2 = (_plus_1 + Integer.valueOf(_id_1));
            ServerConstants.LOG.error(_plus_2);
            return false;
          }
          boolean _removePlayer = game.removePlayer(player);
          success = _removePlayer;
        }
      }
      if (success) {
        this.notifyGamers(game);
      } else {
        PlayerLeftCommandBuilder _playerLeftCommandBuilder = new PlayerLeftCommandBuilder();
        PlayerLeftCommandBuilder _dropable = _playerLeftCommandBuilder.dropable(false);
        int _id = game.getId();
        PlayerLeftCommandBuilder _game = _dropable.game(_id);
        PlayerData _createPlayerData = DataHelper.createPlayerData(player);
        PlayerLeftCommandBuilder _player = _game.player(_createPlayerData);
        final PlayerLeftCommand command = _player.build();
        ServerGame _serverGame = game.getServerGame();
        Collection<ServerPlayer> _players = _serverGame.getPlayers();
        final Function1<ServerPlayer, Integer> _function = new Function1<ServerPlayer, Integer>() {
          @Override
          public Integer apply(final ServerPlayer it) {
            return Integer.valueOf(it.getId());
          }
        };
        Iterable<Integer> _map = IterableExtensions.<ServerPlayer, Integer>map(_players, _function);
        List<Integer> _list = IterableExtensions.<Integer>toList(_map);
        this.commandQueue.addCommand(command, CommandType.TOCLIENT, _list);
        ServerGame _serverGame_1 = game.getServerGame();
        Collection<ServerPlayer> _players_1 = _serverGame_1.getPlayers();
        int _size = _players_1.size();
        boolean _equals = (_size == 0);
        if (_equals) {
          this.removeGame(game);
        }
      }
    } catch (final Throwable _t) {
      if (_t instanceof BaseException) {
        final BaseException e = (BaseException)_t;
        String _message = e.getMessage();
        ServerConstants.LOG.error(_message, e);
        return false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return true;
  }
  
  @Override
  public ServerPlayer login(final String name) {
    final ServerPlayerImpl serverPlayer = new ServerPlayerImpl(name, ColorImpl.RED, false);
    int _id = serverPlayer.getId();
    this.players.put(Integer.valueOf(_id), serverPlayer);
    this.cleanupPlayers();
    return serverPlayer;
  }
  
  @Override
  public void initSingleGame(final GameEnvironment gameEnvironment) throws BaseException {
    Validate.notNull(gameEnvironment);
    final ServerGame game = gameEnvironment.getServerGame();
    InitGameSender _initGameSender = new InitGameSender(gameEnvironment, this.commandQueue);
    game.startGame(_initGameSender);
  }
  
  @Override
  public void removePlayer(final ServerPlayer player) {
    int _id = player.getId();
    this.players.remove(Integer.valueOf(_id));
    Collection<GameEnvironment> _values = this.serverGames.values();
    final Function1<GameEnvironment, Boolean> _function = new Function1<GameEnvironment, Boolean>() {
      @Override
      public Boolean apply(final GameEnvironment it) {
        ServerGame _serverGame = it.getServerGame();
        Collection<ServerPlayer> _players = _serverGame.getPlayers();
        return Boolean.valueOf(_players.contains(player));
      }
    };
    Iterable<GameEnvironment> _filter = IterableExtensions.<GameEnvironment>filter(_values, _function);
    final Procedure1<GameEnvironment> _function_1 = new Procedure1<GameEnvironment>() {
      @Override
      public void apply(final GameEnvironment it) {
        GamesManagerImpl.this.leaveGame(it, player);
      }
    };
    IterableExtensions.<GameEnvironment>forEach(_filter, _function_1);
  }
  
  @Override
  public synchronized Color getFreeColor(final ServerPlayer player, final ServerGame game) {
    Color _xblockexpression = null;
    {
      final HashSet<Color> colors = new HashSet<Color>(PlayerColors.colors);
      Collection<ServerPlayer> _players = game.getPlayers();
      for (final ServerPlayer existingPlayer : _players) {
        Color _color = existingPlayer.getColor();
        colors.remove(_color);
      }
      Iterator<Color> _iterator = colors.iterator();
      _xblockexpression = _iterator.next();
    }
    return _xblockexpression;
  }
}
