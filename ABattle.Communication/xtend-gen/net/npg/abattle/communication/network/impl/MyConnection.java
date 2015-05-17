package net.npg.abattle.communication.network.impl;

import com.esotericsoftware.kryonet.Connection;
import com.google.common.base.Optional;

@SuppressWarnings("all")
public class MyConnection extends Connection {
  private Optional<Integer> clientId;
  
  public MyConnection() {
    super();
    Optional<Integer> _absent = Optional.<Integer>absent();
    this.clientId = _absent;
  }
  
  public Optional<Integer> setClientId(final int clientId) {
    Optional<Integer> _of = Optional.<Integer>of(Integer.valueOf(clientId));
    return this.clientId = _of;
  }
  
  public Optional<Integer> getClientId() {
    return this.clientId;
  }
}
