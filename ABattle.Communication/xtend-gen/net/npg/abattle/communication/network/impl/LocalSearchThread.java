package net.npg.abattle.communication.network.impl;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import net.npg.abattle.common.configuration.NetworkConfigurationData;
import net.npg.abattle.common.utils.LoopRunnable;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.network.NetworkClient;
import net.npg.abattle.communication.network.data.NetworkGameInfo;
import net.npg.abattle.communication.network.impl.NetworkClientImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class LocalSearchThread extends LoopRunnable {
  private Optional<NetworkGameInfo> gameFound;
  
  private final NetworkConfigurationData configuration;
  
  private final NetworkClient client;
  
  public LocalSearchThread(final NetworkConfigurationData configuration) {
    super("LocalSearch");
    Validate.notNull(configuration);
    Optional<NetworkGameInfo> _absent = Optional.<NetworkGameInfo>absent();
    this.gameFound = _absent;
    this.configuration = configuration;
    NetworkClientImpl _networkClientImpl = new NetworkClientImpl(configuration);
    this.client = _networkClientImpl;
  }
  
  @Override
  public void innerLoop() {
    try {
      boolean _equals = Objects.equal(this.gameFound, null);
      if (_equals) {
        Optional<NetworkGameInfo> _discoverHostAndGame = this.client.discoverHostAndGame();
        this.gameFound = _discoverHostAndGame;
      } else {
        Thread.sleep(1000);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public Optional<NetworkGameInfo> getGame() {
    return this.gameFound;
  }
  
  public NetworkClient getClient() {
    return this.client;
  }
}
