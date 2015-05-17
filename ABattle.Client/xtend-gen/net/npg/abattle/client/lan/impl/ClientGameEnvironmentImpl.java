package net.npg.abattle.client.lan.impl;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import java.util.Collections;
import java.util.List;
import net.npg.abattle.client.lan.ClientGameEnvironment;
import net.npg.abattle.client.lan.impl.EmptyBoardCreator;
import net.npg.abattle.client.startup.Startup;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.error.ErrorMessage;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientIdRegister;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.client.impl.ClientGameImpl;
import net.npg.abattle.common.model.client.impl.ClientModelComponentImpl;
import net.npg.abattle.common.model.impl.GameConfigurationImpl;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.common.utils.impl.DisposeableImpl;
import net.npg.abattle.communication.CommunicationConstants;
import net.npg.abattle.communication.command.CommandProcessor;
import net.npg.abattle.communication.command.CommandQueueClient;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.DeadCommand;
import net.npg.abattle.communication.command.ErrorCommand;
import net.npg.abattle.communication.command.GameCommand;
import net.npg.abattle.communication.command.commands.GameCountdownCommand;
import net.npg.abattle.communication.command.commands.InitBoardCommand;
import net.npg.abattle.communication.command.commands.LeaveCommand;
import net.npg.abattle.communication.command.commands.LeaveCommandBuilder;
import net.npg.abattle.communication.command.impl.CommandQueueImpl;
import net.npg.abattle.communication.network.NetworkClient;
import net.npg.abattle.communication.network.NetworkErrorException;
import net.npg.abattle.communication.network.impl.GameCommandHandler;
import net.npg.abattle.communication.network.impl.LoginResultHandler;
import net.npg.abattle.communication.network.impl.NetworkClientProcessor;
import net.npg.abattle.communication.service.ServerService;
import net.npg.abattle.communication.service.common.BooleanResult;
import net.npg.abattle.communication.service.common.GameInfoResult;
import net.npg.abattle.communication.service.common.MutableIntPoint;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ClientGameEnvironmentImpl extends DisposeableImpl implements ClientGameEnvironment, CommandProcessor<DeadCommand> {
  private CommandQueueClient commandQueue;
  
  private ClientGameImpl game;
  
  private NetworkClient client;
  
  private ClientPlayer player;
  
  private ClientIdRegister clientModel;
  
  private volatile int gameStarted;
  
  private Optional<Integer> remainingCount = Optional.<Integer>absent();
  
  public ClientGameEnvironmentImpl(final NetworkClient _client) {
    Validate.notNull(_client);
    this.client = _client;
    ClientModelComponentImpl _clientModelComponentImpl = new ClientModelComponentImpl();
    this.clientModel = _clientModelComponentImpl;
    CommandQueueImpl _commandQueueImpl = new CommandQueueImpl(CommandType.TOCLIENT, this);
    this.commandQueue = _commandQueueImpl;
    GameCommandHandler _gameCommandHandler = new GameCommandHandler(this.commandQueue);
    this.client.addHandler(_gameCommandHandler);
    NetworkClientProcessor _networkClientProcessor = new NetworkClientProcessor(this.client);
    Optional<Class<GameCommand>> _absent = Optional.<Class<GameCommand>>absent();
    this.commandQueue.<GameCommand>registerCommandProcessor(_networkClientProcessor, _absent, CommandType.TOSERVER);
    this.gameStarted = 0;
  }
  
  @Override
  public ErrorMessage createGame(final IntPoint size, final int playerCount) {
    boolean _isDisposed = this.isDisposed();
    boolean _not = (!_isDisposed);
    assert _not;
    boolean _equals = Objects.equal(this.game, null);
    assert _equals;
    Validate.notNull(this.player);
    this.commandQueue.pause();
    Optional<ServerService> _serverService = this.client.getServerService();
    ServerService _get = _serverService.get();
    int _id = this.player.getId();
    MutableIntPoint _from = MutableIntPoint.from(size);
    final GameInfoResult gameDTO = _get.createGame(_id, playerCount, _from, 0);
    if ((!gameDTO.success)) {
      return new ErrorMessage(CommunicationConstants.GAME_CREATION_FAILED, gameDTO.errorMessage);
    }
    this.buildGame(gameDTO);
    Startup.l30(this);
    this.commandQueue.resume();
    return new ErrorMessage(false);
  }
  
  private void buildGame(final GameInfoResult gameDTO) {
    try {
      Validate.notNull(this.player);
      final GameConfigurationImpl configuration = new GameConfigurationImpl();
      GameConfigurationData _gameConfigurationData = new GameConfigurationData();
      configuration.setConfiguration(_gameConfigurationData);
      GameConfigurationData _configuration = configuration.getConfiguration();
      _configuration.setBaseGrowthPerTick(0);
      GameConfigurationData _configuration_1 = configuration.getConfiguration();
      _configuration_1.setMaxCellHeight(gameDTO.gameInfo.parameters.maxCellHeight);
      GameConfigurationData _configuration_2 = configuration.getConfiguration();
      _configuration_2.setMaxCellStrength(gameDTO.gameInfo.parameters.maxCellStrength);
      GameConfigurationData _configuration_3 = configuration.getConfiguration();
      _configuration_3.setMaxMovement(gameDTO.gameInfo.parameters.maxMovement);
      IntPoint _to = gameDTO.gameInfo.parameters.size.to();
      CheckModelElement _checker = configuration.getChecker();
      final EmptyBoardCreator creator = new EmptyBoardCreator(_to, _checker);
      ClientGameImpl _clientGameImpl = new ClientGameImpl(gameDTO.id, creator, configuration);
      this.game = _clientGameImpl;
      this.game.addPlayer(this.player);
      this.game.setLocalPlayer(this.player);
      this.game.initGame();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public ErrorMessage joinGame(final int gameId) {
    boolean _isDisposed = this.isDisposed();
    boolean _not = (!_isDisposed);
    assert _not;
    Optional<ServerService> _serverService = this.client.getServerService();
    ServerService _get = _serverService.get();
    int _id = this.player.getId();
    final GameInfoResult gameDTO = _get.joinGame(gameId, _id);
    if ((!gameDTO.success)) {
      return new ErrorMessage(CommunicationConstants.GAME_JOINED_FAILED, gameDTO.errorMessage);
    }
    this.buildGame(gameDTO);
    Startup.l30(this);
    return new ErrorMessage(false);
  }
  
  @Override
  public CommandQueueClient getCommandQueue() {
    boolean _isDisposed = this.isDisposed();
    boolean _not = (!_isDisposed);
    assert _not;
    return this.commandQueue;
  }
  
  @Override
  public int login(final String name) {
    int _xblockexpression = (int) 0;
    {
      boolean _notEquals = (!Objects.equal(this.client, null));
      assert _notEquals;
      boolean _isDisposed = this.isDisposed();
      boolean _not = (!_isDisposed);
      assert _not;
      this.clientModel.setName(name);
      LoginResultHandler _loginResultHandler = new LoginResultHandler(this.clientModel);
      this.client.addHandler(_loginResultHandler);
      this.client.doLogin(name);
      ClientPlayer _waitForClientId = this.waitForClientId();
      this.player = _waitForClientId;
      _xblockexpression = this.player.getId();
    }
    return _xblockexpression;
  }
  
  private ClientPlayer waitForClientId() {
    try {
      boolean _notEquals = (!Objects.equal(this.clientModel, null));
      assert _notEquals;
      boolean _isDisposed = this.isDisposed();
      boolean _not = (!_isDisposed);
      assert _not;
      this.clientModel.waitForClientId();
      final ClientPlayer player = this.clientModel.getLocalPlayer();
      assert player != null;
      this.clientModel.resetClientPlayer();
      return player;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public ClientGame getGame() {
    boolean _notEquals = (!Objects.equal(this.game, null));
    assert _notEquals;
    boolean _isDisposed = this.isDisposed();
    boolean _not = (!_isDisposed);
    assert _not;
    return this.game;
  }
  
  @Override
  public void leave() {
    try {
      boolean _isDisposed = this.isDisposed();
      boolean _not = (!_isDisposed);
      assert _not;
      LeaveCommandBuilder _leaveCommandBuilder = new LeaveCommandBuilder();
      int _id = this.game.getId();
      LeaveCommandBuilder _game = _leaveCommandBuilder.game(_id);
      int _id_1 = this.player.getId();
      LeaveCommandBuilder _originatedPlayer = _game.originatedPlayer(_id_1);
      LeaveCommand _build = _originatedPlayer.build();
      List<Integer> _emptyList = Collections.<Integer>emptyList();
      this.commandQueue.addCommand(_build, CommandType.TOSERVER, _emptyList);
      this.commandQueue.flush();
      Thread.sleep(100);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public boolean checkGameStart() {
    boolean _xblockexpression = false;
    {
      boolean _isDisposed = this.isDisposed();
      boolean _not = (!_isDisposed);
      assert _not;
      _xblockexpression = (this.gameStarted > 0);
    }
    return _xblockexpression;
  }
  
  @Override
  public Optional<Integer> getRemainingCount() {
    boolean _isDisposed = this.isDisposed();
    boolean _not = (!_isDisposed);
    assert _not;
    return this.remainingCount;
  }
  
  @Override
  public void receivedCommand(final GameCommand command) {
    boolean _isDisposed = this.isDisposed();
    boolean _not = (!_isDisposed);
    assert _not;
    if ((command instanceof InitBoardCommand)) {
      this.gameStarted++;
    } else {
      if ((command instanceof GameCountdownCommand)) {
        Integer _valueOf = Integer.valueOf(((GameCountdownCommand) command).remainingCount);
        Optional<Integer> _of = Optional.<Integer>of(_valueOf);
        this.remainingCount = _of;
      }
    }
  }
  
  @Override
  public void dispose() {
    super.dispose();
    boolean _isDisposed = this.client.isDisposed();
    boolean _not = (!_isDisposed);
    if (_not) {
      this.client.dispose();
    }
  }
  
  @Override
  public Optional<ErrorCommand> execute(final DeadCommand command, final int destination) {
    return null;
  }
  
  @Override
  public void ping() {
    try {
      boolean _equals = Objects.equal(this.game, null);
      if(_equals) return;;
      this.client.ping();
      Optional<ServerService> _serverService = this.client.getServerService();
      ServerService _get = _serverService.get();
      int _id = this.game.getId();
      final BooleanResult retValue = _get.ping(_id);
      if ((!retValue.success)) {
        throw new NetworkErrorException(retValue.errorMessage);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
