package net.npg.abattle.communication.network.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import java.util.HashSet;
import java.util.Set;
import net.npg.abattle.common.configuration.NetworkConfigurationData;
import net.npg.abattle.common.utils.MyHashMap;
import net.npg.abattle.common.utils.MyMap;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.common.utils.impl.DisposeableImpl;
import net.npg.abattle.communication.CommunicationConstants;
import net.npg.abattle.communication.command.ReceiveHandler;
import net.npg.abattle.communication.network.NetworkListener;
import net.npg.abattle.communication.network.NetworkServer;
import net.npg.abattle.communication.network.NetworkService;
import net.npg.abattle.communication.network.impl.KyroListenerAdapter;
import net.npg.abattle.communication.network.impl.MyConnection;
import net.npg.abattle.communication.network.impl.MyServer;
import net.npg.abattle.communication.network.impl.SerializerHelper;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class NetworkServerImpl extends DisposeableImpl implements NetworkServer, NetworkListener {
  private final Server server;
  
  private final ObjectSpace objectSpace;
  
  private final NetworkConfigurationData configuration;
  
  private volatile long sentBytes;
  
  private volatile long lastLog;
  
  private MyMap<Integer, NetworkService> serviceMap;
  
  private Set<Listener> listeners;
  
  public NetworkServerImpl(final NetworkConfigurationData configuration) {
    Validate.notNull(configuration);
    this.configuration = configuration;
    ObjectSpace _objectSpace = new ObjectSpace();
    this.objectSpace = _objectSpace;
    MyServer _myServer = new MyServer(this.objectSpace);
    this.server = _myServer;
    Kryo _kryo = this.server.getKryo();
    ObjectSpace.registerClasses(_kryo);
    Kryo _kryo_1 = this.server.getKryo();
    SerializerHelper.initializeKryo(_kryo_1);
    this.sentBytes = 0L;
    long _currentTimeMillis = System.currentTimeMillis();
    this.lastLog = _currentTimeMillis;
    MyHashMap<Integer, NetworkService> _myHashMap = new MyHashMap<Integer, NetworkService>();
    this.serviceMap = _myHashMap;
    HashSet<Listener> _newHashSet = CollectionLiterals.<Listener>newHashSet();
    this.listeners = _newHashSet;
  }
  
  @Override
  public void dispose() {
    this.server.stop();
    this.objectSpace.close();
  }
  
  @Override
  public NetworkService getService(final int id) {
    return this.serviceMap.get(Integer.valueOf(id));
  }
  
  @Override
  public void registerService(final NetworkService service, final int id) {
    Validate.notNull(service);
    this.objectSpace.register(id, service);
    this.serviceMap.put(Integer.valueOf(id), service);
  }
  
  @Override
  public void start() {
    try {
      this.server.start();
      int _port = this.configuration.getPort();
      int _port_1 = this.configuration.getPort();
      this.server.bind(_port, _port_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public boolean sendTo(final Object object, final int clientId) {
    int count = 0;
    Connection[] _connections = this.server.getConnections();
    final Function1<Connection, Boolean> _function = new Function1<Connection, Boolean>() {
      @Override
      public Boolean apply(final Connection it) {
        return Boolean.valueOf((it instanceof MyConnection));
      }
    };
    Iterable<Connection> _filter = IterableExtensions.<Connection>filter(((Iterable<Connection>)Conversions.doWrapArray(_connections)), _function);
    final Function1<Connection, Boolean> _function_1 = new Function1<Connection, Boolean>() {
      @Override
      public Boolean apply(final Connection it) {
        Optional<Integer> _clientId = ((MyConnection) it).getClientId();
        return Boolean.valueOf(_clientId.isPresent());
      }
    };
    Iterable<Connection> _filter_1 = IterableExtensions.<Connection>filter(_filter, _function_1);
    final Function1<Connection, Boolean> _function_2 = new Function1<Connection, Boolean>() {
      @Override
      public Boolean apply(final Connection it) {
        Optional<Integer> _clientId = ((MyConnection) it).getClientId();
        Integer _get = _clientId.get();
        return Boolean.valueOf(((_get).intValue() == clientId));
      }
    };
    Connection connection = IterableExtensions.<Connection>findFirst(_filter_1, _function_2);
    boolean _equals = Objects.equal(connection, null);
    if (_equals) {
      return false;
    }
    if (CommunicationConstants.DEBUG_ENABLED) {
      CommunicationConstants.LOG.debug(("Send object to:" + Integer.valueOf(clientId)));
    }
    long _sentBytes = this.sentBytes;
    int _sendTCP = connection.sendTCP(object);
    this.sentBytes = (_sentBytes + _sendTCP);
    count++;
    if ((count == 0)) {
      String _plus = (Integer.valueOf(clientId) + " failed to send object:");
      String _plus_1 = (_plus + object);
      CommunicationConstants.LOG.error(_plus_1);
      return false;
    }
    this.logSentBytes();
    return true;
  }
  
  public void logSentBytes() {
    long _currentTimeMillis = System.currentTimeMillis();
    final long timeDiff = (_currentTimeMillis - this.lastLog);
    if ((timeDiff > 5000L)) {
      final double kb_s = ((this.sentBytes / 5.0) / 1024);
      this.sentBytes = 0L;
      long _currentTimeMillis_1 = System.currentTimeMillis();
      this.lastLog = _currentTimeMillis_1;
      CommunicationConstants.LOG.debug(("Sent KiloBytes per Second:" + Integer.valueOf(((int) kb_s))));
    }
  }
  
  @SuppressWarnings("rawtypes")
  @Override
  public synchronized void addHandler(final ReceiveHandler handler) {
    Validate.notNull(handler);
    final KyroListenerAdapter listener = new KyroListenerAdapter(handler);
    this.listeners.add(listener);
    this.server.addListener(listener);
  }
  
  @Override
  public synchronized void removeHandlers() {
    final Procedure1<Listener> _function = new Procedure1<Listener>() {
      @Override
      public void apply(final Listener it) {
        NetworkServerImpl.this.server.removeListener(it);
      }
    };
    IterableExtensions.<Listener>forEach(this.listeners, _function);
    this.listeners.clear();
  }
}
