package net.npg.abattle.communication.network.impl;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.common.base.Objects;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.ReceiveHandler;

@SuppressWarnings("all")
public class KyroListenerAdapter extends Listener {
  private ReceiveHandler handler;
  
  public KyroListenerAdapter(final ReceiveHandler handler) {
    Validate.notNull(handler);
    this.handler = handler;
  }
  
  @Override
  public void received(final Connection connection, final Object object) {
    boolean _equals = Objects.equal(object, null);
    if(_equals) return;;
    boolean _equals_1 = Objects.equal(connection, null);
    if(_equals_1) return;;
    boolean _canHandle = this.handler.canHandle(object);
    if (_canHandle) {
      this.handler.handle(object, connection);
    }
  }
}
