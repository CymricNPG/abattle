package net.npg.abattle.communication.network.impl;

import com.google.common.base.Objects;
import java.util.HashSet;
import java.util.Set;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.component.ComponentType;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.NetworkConfigurationData;
import net.npg.abattle.common.utils.Disposeable;
import net.npg.abattle.common.utils.impl.DisposeableImpl;
import net.npg.abattle.communication.CommunicationConstants;
import net.npg.abattle.communication.network.NetworkClient;
import net.npg.abattle.communication.network.NetworkComponent;
import net.npg.abattle.communication.network.NetworkServer;
import net.npg.abattle.communication.network.impl.NetworkClientImpl;
import net.npg.abattle.communication.network.impl.NetworkServerImpl;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@ComponentType
@SuppressWarnings("rawtypes")
public class NetworkComponentImpl extends DisposeableImpl implements NetworkComponent {
  private final NetworkConfigurationData configuration;
  
  private final Set<Disposeable> clients;
  
  private NetworkServer server;
  
  public NetworkComponentImpl() {
    ComponentLookup _instance = ComponentLookup.getInstance();
    ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
    NetworkConfigurationData _networkConfiguration = _component.getNetworkConfiguration();
    this.configuration = _networkConfiguration;
    HashSet<Disposeable> _newHashSet = CollectionLiterals.<Disposeable>newHashSet();
    this.clients = _newHashSet;
  }
  
  @Override
  public synchronized void dispose() {
    super.dispose();
    final Function1<Disposeable, Boolean> _function = new Function1<Disposeable, Boolean>() {
      @Override
      public Boolean apply(final Disposeable it) {
        boolean _isDisposed = it.isDisposed();
        return Boolean.valueOf((!_isDisposed));
      }
    };
    Iterable<Disposeable> _filter = IterableExtensions.<Disposeable>filter(this.clients, _function);
    final Procedure1<Disposeable> _function_1 = new Procedure1<Disposeable>() {
      @Override
      public void apply(final Disposeable it) {
        it.dispose();
      }
    };
    IterableExtensions.<Disposeable>forEach(_filter, _function_1);
    this.clients.clear();
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(this.server, null));
    if (!_notEquals) {
      _and = false;
    } else {
      boolean _isDisposed = this.server.isDisposed();
      boolean _not = (!_isDisposed);
      _and = _not;
    }
    if (_and) {
      this.server.dispose();
    }
    this.server = null;
  }
  
  @Override
  public synchronized NetworkClient createClient() {
    NetworkClientImpl _xblockexpression = null;
    {
      final NetworkClientImpl client = new NetworkClientImpl(this.configuration);
      this.clients.add(client);
      final Function1<Disposeable, Boolean> _function = new Function1<Disposeable, Boolean>() {
        @Override
        public Boolean apply(final Disposeable it) {
          return Boolean.valueOf(it.isDisposed());
        }
      };
      net.npg.abattle.common.utils.IterableExtensions.<Disposeable>removeConditional(this.clients, _function);
      _xblockexpression = client;
    }
    return _xblockexpression;
  }
  
  @Override
  public synchronized void removeServerHandlers() {
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(this.server, null));
    if (!_notEquals) {
      _and = false;
    } else {
      boolean _isDisposed = this.server.isDisposed();
      boolean _not = (!_isDisposed);
      _and = _not;
    }
    if (_and) {
      this.server.removeHandlers();
    }
  }
  
  @Override
  public synchronized NetworkServer createServer() {
    try {
      NetworkServer _xblockexpression = null;
      {
        boolean _and = false;
        boolean _notEquals = (!Objects.equal(this.server, null));
        if (!_notEquals) {
          _and = false;
        } else {
          boolean _isDisposed = this.server.isDisposed();
          boolean _not = (!_isDisposed);
          _and = _not;
        }
        if (_and) {
          return this.server;
        }
        NetworkServerImpl _networkServerImpl = new NetworkServerImpl(this.configuration);
        this.server = _networkServerImpl;
        try {
          this.server.start();
        } catch (final Throwable _t) {
          if (_t instanceof Exception) {
            final Exception e = (Exception)_t;
            String _message = e.getMessage();
            CommunicationConstants.LOG.error(_message, e);
            this.server.dispose();
            this.server = null;
            throw e;
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        }
        _xblockexpression = this.server;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public boolean isServerRunning() {
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(this.server, null));
    if (!_notEquals) {
      _and = false;
    } else {
      boolean _isDisposed = this.server.isDisposed();
      boolean _not = (!_isDisposed);
      _and = _not;
    }
    return _and;
  }
  
  public Class<NetworkComponent> getInterface() {
    return NetworkComponent.class;
  }
}
