package net.npg.abattle.communication;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import java.util.Properties;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.NetworkConfigurationData;
import net.npg.abattle.common.configuration.impl.ConfigurationComponentImpl;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.impl.CommandQueueImpl;
import net.npg.abattle.communication.network.data.NetworkGameInfo;
import net.npg.abattle.communication.network.impl.NetworkClientImpl;
import net.npg.abattle.communication.network.impl.NetworkServerImpl;
import net.npg.abattle.communication.service.ServerService;
import net.npg.abattle.communication.service.common.BooleanResult;
import net.npg.abattle.communication.service.common.ClientInfoResult;
import net.npg.abattle.communication.service.common.GameInfoResult;
import net.npg.abattle.communication.service.common.MutableIntPoint;
import net.npg.abattle.server.service.impl.ServerServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Status: finished
 */
@SuppressWarnings("all")
public class MultiplayerTest {
  private NetworkServerImpl server;
  
  @Test
  public void testGame() {
    this.baseInit();
    Properties _properties = new Properties();
    final NetworkConfigurationData configuration = new NetworkConfigurationData(_properties);
    NetworkServerImpl _networkServerImpl = new NetworkServerImpl(configuration);
    this.server = _networkServerImpl;
    this.server.start();
    this.initServerComponents();
    final NetworkClientImpl client1 = this.doPlayer1Init(configuration);
    this.doPlayer2Init(configuration);
    this.startGame(client1);
  }
  
  public void startGame(final NetworkClientImpl client) {
    Optional<ServerService> _serverService = client.getServerService();
    ServerService _get = _serverService.get();
    Optional<NetworkGameInfo> _foundGame = client.getFoundGame();
    NetworkGameInfo _get_1 = _foundGame.get();
    int _gameId = _get_1.getGameId();
    final BooleanResult result = _get.initSingleGame(_gameId);
    Assert.assertTrue(result.success);
  }
  
  public ConfigurationComponentImpl baseInit() {
    ConfigurationComponentImpl _xblockexpression = null;
    {
      ComponentLookup.createInstance();
      ComponentLookup _instance = ComponentLookup.getInstance();
      ConfigurationComponentImpl _configurationComponentImpl = new ConfigurationComponentImpl();
      _xblockexpression = _instance.<ConfigurationComponentImpl>registerComponent(_configurationComponentImpl);
    }
    return _xblockexpression;
  }
  
  @After
  public void cleanUp() {
    boolean _notEquals = (!Objects.equal(this.server, null));
    if (_notEquals) {
      this.server.dispose();
    }
    ComponentLookup.shutdownInstance();
  }
  
  public void doPlayer2Init(final NetworkConfigurationData configuration) {
    final NetworkClientImpl client2 = new NetworkClientImpl(configuration);
    final Optional<NetworkGameInfo> gameInfo = client2.discoverHostAndGame();
    boolean _isPresent = gameInfo.isPresent();
    Assert.assertTrue(_isPresent);
    final NetworkGameInfo gameInfoDTO = gameInfo.get();
    Optional<ServerService> _serverService = client2.getServerService();
    final ServerService remote2SS = _serverService.get();
    final ClientInfoResult client2DTO = remote2SS.login("client2");
    Assert.assertTrue(client2DTO.success);
    int _gameId = gameInfoDTO.getGameId();
    final GameInfoResult joinDTO = remote2SS.joinGame(_gameId, client2DTO.clientInfo.id);
    Assert.assertTrue(joinDTO.success);
  }
  
  public NetworkClientImpl doPlayer1Init(final NetworkConfigurationData configuration) {
    NetworkClientImpl _xblockexpression = null;
    {
      final NetworkClientImpl client1 = new NetworkClientImpl(configuration);
      boolean _discoverHost = client1.discoverHost();
      Assert.assertTrue(_discoverHost);
      Optional<ServerService> _serverService = client1.getServerService();
      final ServerService remote1SS = _serverService.get();
      final ClientInfoResult client1DTO = remote1SS.login("client1");
      Assert.assertTrue(client1DTO.success);
      MutableIntPoint _from = MutableIntPoint.from(16, 16);
      final GameInfoResult game = remote1SS.createGame(client1DTO.clientInfo.id, 2, _from, 0);
      Assert.assertTrue(game.success);
      client1.registerGame(game);
      _xblockexpression = client1;
    }
    return _xblockexpression;
  }
  
  public void initServerComponents() {
    final CommandQueueImpl cq = new CommandQueueImpl(CommandType.TOCLIENT, null);
    final ServerServiceImpl serverService = new ServerServiceImpl(cq);
    this.server.registerService(serverService, ServerServiceImpl.SERVER_SERVICE_ID);
  }
}
