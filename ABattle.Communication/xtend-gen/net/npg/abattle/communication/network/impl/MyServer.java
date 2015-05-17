package net.npg.abattle.communication.network.impl;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.CommunicationConstants;
import net.npg.abattle.communication.network.impl.MyConnection;
import net.npg.abattle.communication.network.impl.RemoveConnectionListener;

@SuppressWarnings("all")
public class MyServer extends Server {
  private final ObjectSpace objectSpace;
  
  public MyServer(final ObjectSpace objectSpace) {
    Validate.notNull(objectSpace);
    this.objectSpace = objectSpace;
    RemoveConnectionListener _removeConnectionListener = new RemoveConnectionListener(objectSpace);
    this.addListener(_removeConnectionListener);
  }
  
  @Override
  public Connection newConnection() {
    MyConnection _xblockexpression = null;
    {
      final MyConnection connection = new MyConnection();
      this.objectSpace.addConnection(connection);
      _xblockexpression = connection;
    }
    return _xblockexpression;
  }
  
  @Override
  public void start() {
    CommunicationConstants.LOG.debug("Start Server");
    super.start();
  }
}
