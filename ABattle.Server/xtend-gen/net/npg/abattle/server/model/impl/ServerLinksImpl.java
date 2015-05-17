package net.npg.abattle.server.model.impl;

import com.google.common.base.Objects;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import net.npg.abattle.common.model.impl.IDElementImpl;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerLink;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;
import net.npg.abattle.server.model.impl.ServerLinkImpl;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ServerLinksImpl extends IDElementImpl implements ServerLinks {
  private final Multimap<ServerCell, ServerLink> outgoingCellLinks;
  
  public ServerLinksImpl() {
    super();
    HashMultimap<ServerCell, ServerLink> _create = HashMultimap.<ServerCell, ServerLink>create(1024, 16);
    this.outgoingCellLinks = _create;
  }
  
  @Override
  public synchronized Collection<ServerLink> getLinks() {
    Collection<ServerLink> _values = this.outgoingCellLinks.values();
    return IterableExtensions.<ServerLink>toList(_values);
  }
  
  @Override
  public synchronized Collection<ServerLink> getLinks(final ServerPlayer player) {
    Collection<ServerLink> _values = this.outgoingCellLinks.values();
    final Function1<ServerLink, Boolean> _function = new Function1<ServerLink, Boolean>() {
      @Override
      public Boolean apply(final ServerLink it) {
        ServerPlayer _player = it.getPlayer();
        return Boolean.valueOf(Objects.equal(_player, player));
      }
    };
    Iterable<ServerLink> _filter = IterableExtensions.<ServerLink>filter(_values, _function);
    return IterableExtensions.<ServerLink>toList(_filter);
  }
  
  @Override
  public synchronized List<ServerCell> getOutgoingLinks(final ServerPlayer player, final ServerCell cell) {
    Collection<ServerLink> _get = this.outgoingCellLinks.get(cell);
    final Function1<ServerLink, Boolean> _function = new Function1<ServerLink, Boolean>() {
      @Override
      public Boolean apply(final ServerLink it) {
        ServerPlayer _player = it.getPlayer();
        return Boolean.valueOf(Objects.equal(_player, player));
      }
    };
    Iterable<ServerLink> _filter = IterableExtensions.<ServerLink>filter(_get, _function);
    final Function1<ServerLink, ServerCell> _function_1 = new Function1<ServerLink, ServerCell>() {
      @Override
      public ServerCell apply(final ServerLink it) {
        return it.getDestinationCell();
      }
    };
    Iterable<ServerCell> _map = IterableExtensions.<ServerLink, ServerCell>map(_filter, _function_1);
    return IterableExtensions.<ServerCell>toList(_map);
  }
  
  @Override
  public synchronized void toggleOutgoingLink(final ServerCell startCell, final ServerCell endCell, final ServerPlayer player) {
    final ServerLink oldLink = this.getLink(startCell, endCell, player);
    boolean _notEquals = (!Objects.equal(oldLink, null));
    if (_notEquals) {
      this.outgoingCellLinks.remove(startCell, oldLink);
    } else {
      ServerLinkImpl _serverLinkImpl = new ServerLinkImpl(startCell, endCell, player);
      this.outgoingCellLinks.put(startCell, _serverLinkImpl);
    }
  }
  
  @Override
  public synchronized boolean hasLink(final ServerCell startCell, final ServerCell endCell, final ServerPlayer player) {
    ServerLink _link = this.getLink(startCell, endCell, player);
    return (!Objects.equal(_link, null));
  }
  
  private ServerLink getLink(final ServerCell startCell, final ServerCell endCell, final ServerPlayer player) {
    Collection<ServerLink> _get = this.outgoingCellLinks.get(startCell);
    final Function1<ServerLink, Boolean> _function = new Function1<ServerLink, Boolean>() {
      @Override
      public Boolean apply(final ServerLink it) {
        boolean _and = false;
        ServerPlayer _player = it.getPlayer();
        boolean _equals = Objects.equal(_player, player);
        if (!_equals) {
          _and = false;
        } else {
          ServerCell _destinationCell = it.getDestinationCell();
          boolean _equals_1 = Objects.equal(_destinationCell, endCell);
          _and = _equals_1;
        }
        return Boolean.valueOf(_and);
      }
    };
    return IterableExtensions.<ServerLink>findFirst(_get, _function);
  }
}
