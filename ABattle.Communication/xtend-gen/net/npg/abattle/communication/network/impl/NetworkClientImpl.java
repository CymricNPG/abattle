package net.npg.abattle.communication.network.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import java.net.InetAddress;
import java.util.List;
import net.npg.abattle.common.configuration.NetworkConfigurationData;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.utils.LifecycleControl;
import net.npg.abattle.common.utils.StopListener;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.common.utils.impl.DisposeableImpl;
import net.npg.abattle.communication.CommunicationConstants;
import net.npg.abattle.communication.command.ReceiveHandler;
import net.npg.abattle.communication.command.commands.LoginCommand;
import net.npg.abattle.communication.network.NetworkClient;
import net.npg.abattle.communication.network.NetworkErrorException;
import net.npg.abattle.communication.network.data.NetworkGameInfo;
import net.npg.abattle.communication.network.impl.KyroListenerAdapter;
import net.npg.abattle.communication.network.impl.SerializerHelper;
import net.npg.abattle.communication.service.ServerService;
import net.npg.abattle.communication.service.common.GameInfoResult;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * TODO complete rework!
 */
@SuppressWarnings("all")
public class NetworkClientImpl extends DisposeableImpl implements NetworkClient, StopListener {
  private Client client;
  
  private final NetworkConfigurationData configuration;
  
  private Optional<NetworkGameInfo> gameFound;
  
  private Optional<ServerService> serverService;
  
  private int bytesSent;
  
  public NetworkClientImpl(final NetworkConfigurationData configuration) {
    Validate.notNull(configuration);
    this.configuration = configuration;
    Client _client = new Client();
    this.client = _client;
    Kryo _kryo = this.client.getKryo();
    ObjectSpace.registerClasses(_kryo);
    Optional<NetworkGameInfo> _absent = Optional.<NetworkGameInfo>absent();
    this.gameFound = _absent;
    Optional<ServerService> _absent_1 = Optional.<ServerService>absent();
    this.serverService = _absent_1;
    LifecycleControl _control = LifecycleControl.getControl();
    _control.addStopListener(this);
  }
  
  private void connectToFoundHost(final InetAddress serverAddress) {
    try {
      assert this.client != null;
      this.client.stop();
      Client _client = new Client(8192, (8192 * 4));
      this.client = _client;
      Kryo _kryo = this.client.getKryo();
      ObjectSpace.registerClasses(_kryo);
      this.client.start();
      Thread.sleep(500);
      int _connectTimeout = this.configuration.getConnectTimeout();
      int _port = this.configuration.getPort();
      int _port_1 = this.configuration.getPort();
      this.client.connect(_connectTimeout, serverAddress, _port, _port_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public Optional<NetworkGameInfo> discoverHostAndGame() {
    Optional<NetworkGameInfo> _xblockexpression = null;
    {
      assert this.client != null;
      boolean _and = false;
      boolean _notEquals = (!Objects.equal(this.gameFound, null));
      if (!_notEquals) {
        _and = false;
      } else {
        boolean _isPresent = this.gameFound.isPresent();
        _and = _isPresent;
      }
      if (_and) {
        throw new RuntimeException("Todo: multiple use of discoverHost ..");
      }
      final Optional<InetAddress> address = this.findHostAndService();
      boolean _isPresent_1 = address.isPresent();
      boolean _not = (!_isPresent_1);
      if (_not) {
        return Optional.<NetworkGameInfo>absent();
      }
      InetAddress _get = address.get();
      this.queryGames(_get);
      _xblockexpression = this.gameFound;
    }
    return _xblockexpression;
  }
  
  private Optional<InetAddress> findHostAndService() {
    Optional<InetAddress> _xblockexpression = null;
    {
      assert this.client != null;
      int _port = this.configuration.getPort();
      int _searchTimeout = this.configuration.getSearchTimeout();
      final List<InetAddress> address = this.client.discoverHosts(_port, _searchTimeout);
      boolean _or = false;
      boolean _equals = Objects.equal(address, null);
      if (_equals) {
        _or = true;
      } else {
        boolean _isEmpty = address.isEmpty();
        _or = _isEmpty;
      }
      if (_or) {
        return Optional.<InetAddress>absent();
      }
      try {
        InetAddress _get = address.get(0);
        this.connectToFoundHost(_get);
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception e = (Exception)_t;
          String _message = e.getMessage();
          String _plus = ("Failed during connect:" + _message);
          CommunicationConstants.LOG.error(_plus, e);
          return Optional.<InetAddress>absent();
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      this.initializeDeserializer();
      this.getRemoteServerService();
      InetAddress _get_1 = address.get(0);
      _xblockexpression = Optional.<InetAddress>of(_get_1);
    }
    return _xblockexpression;
  }
  
  private void queryGames(final InetAddress address) {
    try {
      boolean _isPresent = this.serverService.isPresent();
      boolean _not = (!_isPresent);
      if(_not) return;;
      ServerService _get = this.serverService.get();
      final GameInfoResult[] games = _get.getPendingGames();
      boolean _or = false;
      boolean _equals = Objects.equal(games, null);
      if (_equals) {
        _or = true;
      } else {
        int _length = games.length;
        boolean _equals_1 = (_length == 0);
        _or = _equals_1;
      }
      if(_or) return;;
      final GameInfoResult game = games[0];
      if ((!game.success)) {
        throw new NetworkErrorException(("Server failed to deliver:" + game.errorMessage));
      }
      NetworkGameInfo _networkGameInfo = new NetworkGameInfo(address, "N/A", game.gameInfo.currentPlayers, game.gameInfo.maxPlayers, game.id);
      Optional<NetworkGameInfo> _of = Optional.<NetworkGameInfo>of(_networkGameInfo);
      this.gameFound = _of;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void initializeDeserializer() {
    Kryo _kryo = this.client.getKryo();
    SerializerHelper.initializeKryo(_kryo);
  }
  
  @Override
  public Optional<ServerService> getServerService() {
    return this.serverService;
  }
  
  private Optional<ServerService> getRemoteServerService() {
    Optional<ServerService> _xblockexpression = null;
    {
      final ServerService someObject = ObjectSpace.<ServerService>getRemoteObject(this.client, ServerService.SERVER_SERVICE_ID, ServerService.class);
      ((RemoteObject) someObject).setNonBlocking(false);
      Optional<ServerService> _fromNullable = Optional.<ServerService>fromNullable(someObject);
      _xblockexpression = this.serverService = _fromNullable;
    }
    return _xblockexpression;
  }
  
  @Override
  public void dispose() {
    super.dispose();
    this.client.stop();
  }
  
  @Override
  public Optional<NetworkGameInfo> getFoundGame() {
    return this.gameFound;
  }
  
  @Override
  public void connectToLocalhost() {
    try {
      assert this.client != null;
      final InetAddress address = InetAddress.getLocalHost();
      this.connectToFoundHost(address);
      this.initializeDeserializer();
      this.getRemoteServerService();
      Optional.<InetAddress>of(address);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public void connectTo(final InetAddress address) {
    assert this.client != null;
    this.connectToFoundHost(address);
    this.initializeDeserializer();
    this.getRemoteServerService();
    Optional.<InetAddress>of(address);
  }
  
  @Override
  public boolean discoverHost() {
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(this.serverService, null));
    if (!_notEquals) {
      _and = false;
    } else {
      boolean _isPresent = this.serverService.isPresent();
      _and = _isPresent;
    }
    if (_and) {
      throw new RuntimeException("Todo: multiple use of discoverHost ..");
    }
    Optional<InetAddress> _findHostAndService = this.findHostAndService();
    return _findHostAndService.isPresent();
  }
  
  public Optional<NetworkGameInfo> registerGame(final GameInfoResult info) {
    try {
      Optional<NetworkGameInfo> _xblockexpression = null;
      {
        boolean _or = false;
        boolean _equals = Objects.equal(info, null);
        if (_equals) {
          _or = true;
        } else {
          _or = (!info.success);
        }
        if (_or) {
          throw new IllegalArgumentException("Invalid game info!");
        }
        InetAddress _localHost = InetAddress.getLocalHost();
        NetworkGameInfo _networkGameInfo = new NetworkGameInfo(_localHost, "N/A", info.gameInfo.currentPlayers, info.gameInfo.maxPlayers, info.id);
        Optional<NetworkGameInfo> _of = Optional.<NetworkGameInfo>of(_networkGameInfo);
        _xblockexpression = this.gameFound = _of;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public boolean isDead() {
    boolean _isConnected = this.client.isConnected();
    return (!_isConnected);
  }
  
  @Override
  public void send(final Object object) {
    try {
      final int currentBytes = this.client.sendTCP(object);
      if ((currentBytes == 0)) {
        throw new BaseException(CommunicationConstants.NETWORK_SERVER_UNREACHABLE, "Fail to send object.");
      }
      this.bytesSent = (this.bytesSent + currentBytes);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public void doLogin(final String name) {
    boolean _isConnected = this.client.isConnected();
    Validate.isTrue(_isConnected);
    LoginCommand _loginCommand = new LoginCommand(name);
    this.send(_loginCommand);
  }
  
  @Override
  public void clearFoundHost() {
    boolean _notEquals = (!Objects.equal(this.gameFound, null));
    if (_notEquals) {
      this.gameFound = null;
    }
    this.client.stop();
  }
  
  @Override
  public void applicationStopped() {
    CommunicationConstants.LOG.info("Stopping client.");
    this.client.stop();
  }
  
  @SuppressWarnings("rawtypes")
  @Override
  public void addHandler(final ReceiveHandler handler) {
    Validate.notNull(handler);
    KyroListenerAdapter _kyroListenerAdapter = new KyroListenerAdapter(handler);
    this.client.addListener(_kyroListenerAdapter);
  }
  
  @Override
  public void ping() {
    try {
      boolean _isConnected = this.client.isConnected();
      boolean _not = (!_isConnected);
      if (_not) {
        throw new NetworkErrorException("Not connected.");
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
