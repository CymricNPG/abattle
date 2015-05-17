package net.npg.abattle.communication.network.impl;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.google.common.base.Objects;
import net.npg.abattle.common.utils.Validate;

@SuppressWarnings("all")
public class RemoveConnectionListener extends Listener {
  private final ObjectSpace objectSpace;
  
  public RemoveConnectionListener(final ObjectSpace objectSpace) {
    Validate.notNull(objectSpace);
    this.objectSpace = objectSpace;
  }
  
  @Override
  public void disconnected(final Connection connection) {
    boolean _equals = Objects.equal(connection, null);
    if(_equals) return;;
    this.objectSpace.removeConnection(connection);
  }
}
