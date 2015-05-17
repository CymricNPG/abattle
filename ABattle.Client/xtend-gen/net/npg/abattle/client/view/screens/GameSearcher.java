package net.npg.abattle.client.view.screens;

import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.List;
import net.npg.abattle.client.ClientConstants;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.utils.MyRunnable;
import net.npg.abattle.communication.network.NetworkClient;
import net.npg.abattle.communication.network.NetworkComponent;
import net.npg.abattle.communication.network.data.NetworkGameInfo;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class GameSearcher extends MyRunnable {
  private NetworkClient client;
  
  private final ArrayList<NetworkGameInfo> games;
  
  @Accessors
  private volatile boolean newDataAvailable;
  
  @Accessors
  private volatile boolean exit;
  
  public GameSearcher() {
    super("GameSearcher");
    ArrayList<NetworkGameInfo> _newArrayList = CollectionLiterals.<NetworkGameInfo>newArrayList();
    this.games = _newArrayList;
    this.exit = false;
    this.newDataAvailable = false;
  }
  
  public List<NetworkGameInfo> getGames() {
    List<NetworkGameInfo> _xblockexpression = null;
    {
      final List<NetworkGameInfo> retGames = CollectionLiterals.<NetworkGameInfo>newArrayList();
      /* this.games; */
      synchronized (this.games) {
        {
          retGames.addAll(this.games);
          this.games.clear();
          this.newDataAvailable = false;
        }
      }
      _xblockexpression = retGames;
    }
    return _xblockexpression;
  }
  
  @Override
  public void execute() {
    try {
      ComponentLookup _instance = ComponentLookup.getInstance();
      final NetworkComponent networkComponent = _instance.<NetworkComponent>getComponent(NetworkComponent.class);
      NetworkClient _createClient = networkComponent.createClient();
      this.client = _createClient;
      do {
        {
          final Optional<NetworkGameInfo> game = this.client.discoverHostAndGame();
          boolean _isPresent = game.isPresent();
          if (_isPresent) {
            NetworkGameInfo _get = game.get();
            int _gameId = _get.getGameId();
            String _plus = ("Found game:" + Integer.valueOf(_gameId));
            ClientConstants.LOG.info(_plus);
          }
          this.addGame(game);
          this.lifeCycleWait();
        }
      } while(((!this.exit) && (this.games.size() == 0)));
      this.client.dispose();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public boolean addGame(final Optional<NetworkGameInfo> game) {
    boolean _xsynchronizedexpression = false;
    synchronized (this.games) {
      boolean _xblockexpression = false;
      {
        boolean _isPresent = game.isPresent();
        if (_isPresent) {
          this.games.clear();
          NetworkGameInfo _get = game.get();
          this.games.add(_get);
        }
        _xblockexpression = this.newDataAvailable = true;
      }
      _xsynchronizedexpression = _xblockexpression;
    }
    return _xsynchronizedexpression;
  }
  
  @Pure
  public boolean isNewDataAvailable() {
    return this.newDataAvailable;
  }
  
  public void setNewDataAvailable(final boolean newDataAvailable) {
    this.newDataAvailable = newDataAvailable;
  }
  
  @Pure
  public boolean isExit() {
    return this.exit;
  }
  
  public void setExit(final boolean exit) {
    this.exit = exit;
  }
}
