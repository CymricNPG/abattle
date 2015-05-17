package net.npg.abattle.communication.network.data;

import java.net.InetAddress;
import org.eclipse.xtend.lib.Data;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringHelper;

@Data
@SuppressWarnings("all")
public class NetworkGameInfo {
  private final InetAddress _ipAddr;
  
  private final String _gameName;
  
  private final int _currentPlayer;
  
  private final int _maxPlayer;
  
  private final int _gameId;
  
  public NetworkGameInfo(final InetAddress ipAddr, final String gameName, final int currentPlayer, final int maxPlayer, final int gameId) {
    super();
    this._ipAddr = ipAddr;
    this._gameName = gameName;
    this._currentPlayer = currentPlayer;
    this._maxPlayer = maxPlayer;
    this._gameId = gameId;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this._ipAddr== null) ? 0 : this._ipAddr.hashCode());
    result = prime * result + ((this._gameName== null) ? 0 : this._gameName.hashCode());
    result = prime * result + this._currentPlayer;
    result = prime * result + this._maxPlayer;
    result = prime * result + this._gameId;
    return result;
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    NetworkGameInfo other = (NetworkGameInfo) obj;
    if (this._ipAddr == null) {
      if (other._ipAddr != null)
        return false;
    } else if (!this._ipAddr.equals(other._ipAddr))
      return false;
    if (this._gameName == null) {
      if (other._gameName != null)
        return false;
    } else if (!this._gameName.equals(other._gameName))
      return false;
    if (other._currentPlayer != this._currentPlayer)
      return false;
    if (other._maxPlayer != this._maxPlayer)
      return false;
    if (other._gameId != this._gameId)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public String toString() {
    String result = new ToStringHelper().toString(this);
    return result;
  }
  
  @Pure
  public InetAddress getIpAddr() {
    return this._ipAddr;
  }
  
  @Pure
  public String getGameName() {
    return this._gameName;
  }
  
  @Pure
  public int getCurrentPlayer() {
    return this._currentPlayer;
  }
  
  @Pure
  public int getMaxPlayer() {
    return this._maxPlayer;
  }
  
  @Pure
  public int getGameId() {
    return this._gameId;
  }
}
