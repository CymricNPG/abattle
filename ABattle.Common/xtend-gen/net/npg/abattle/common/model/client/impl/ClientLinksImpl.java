package net.npg.abattle.common.model.client.impl;

import com.google.common.base.Objects;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.model.Link;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientLinks;
import net.npg.abattle.common.model.impl.IDElementImpl;
import net.npg.abattle.common.utils.MyHashMap;
import net.npg.abattle.common.utils.MyMap;
import net.npg.abattle.common.utils.Validate;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

/**
 * @author Cymric
 */
@SuppressWarnings("all")
public class ClientLinksImpl extends IDElementImpl implements ClientLinks {
  private final MyMap<Integer, Link<ClientCell>> links;
  
  private final MyMap<Integer, Set<Integer>> cellToCells;
  
  public ClientLinksImpl() {
    super();
    MyHashMap<Integer, Link<ClientCell>> _myHashMap = new MyHashMap<Integer, Link<ClientCell>>();
    this.links = _myHashMap;
    MyHashMap<Integer, Set<Integer>> _myHashMap_1 = new MyHashMap<Integer, Set<Integer>>();
    this.cellToCells = _myHashMap_1;
  }
  
  public ClientLinksImpl(final int id) {
    super(id);
    MyHashMap<Integer, Link<ClientCell>> _myHashMap = new MyHashMap<Integer, Link<ClientCell>>();
    this.links = _myHashMap;
    MyHashMap<Integer, Set<Integer>> _myHashMap_1 = new MyHashMap<Integer, Set<Integer>>();
    this.cellToCells = _myHashMap_1;
  }
  
  @Override
  public synchronized void addLink(final Link<ClientCell> link) {
    boolean _notEquals = (!Objects.equal(this.links, null));
    assert _notEquals;
    boolean _notEquals_1 = (!Objects.equal(this.cellToCells, null));
    assert _notEquals_1;
    Validate.notNull(link);
    int _id = link.getId();
    boolean _containsKey = this.links.containsKey(Integer.valueOf(_id));
    boolean _not = (!_containsKey);
    Validate.isTrue(_not);
    int _id_1 = link.getId();
    this.links.put(Integer.valueOf(_id_1), link);
    ClientCell _sourceCell = link.getSourceCell();
    int _id_2 = _sourceCell.getId();
    Set<Integer> cellLinks = this.cellToCells.get(Integer.valueOf(_id_2));
    boolean _equals = Objects.equal(cellLinks, null);
    if (_equals) {
      HashSet<Integer> _newHashSet = CollectionLiterals.<Integer>newHashSet();
      cellLinks = _newHashSet;
      ClientCell _sourceCell_1 = link.getSourceCell();
      int _id_3 = _sourceCell_1.getId();
      this.cellToCells.put(Integer.valueOf(_id_3), cellLinks);
    }
    ClientCell _destinationCell = link.getDestinationCell();
    int _id_4 = _destinationCell.getId();
    cellLinks.add(Integer.valueOf(_id_4));
    int _id_5 = link.getId();
    String _plus = ("Link:" + Integer.valueOf(_id_5));
    String _plus_1 = (_plus + " added.");
    CommonConstants.LOG.debug(_plus_1);
  }
  
  @Override
  public synchronized boolean doesLinkExists(final ClientCell startCell, final ClientCell endCell) {
    boolean _xblockexpression = false;
    {
      int _id = startCell.getId();
      Set<Integer> cellLinks = this.cellToCells.get(Integer.valueOf(_id));
      boolean _equals = Objects.equal(cellLinks, null);
      if (_equals) {
        return false;
      }
      int _id_1 = endCell.getId();
      _xblockexpression = cellLinks.contains(Integer.valueOf(_id_1));
    }
    return _xblockexpression;
  }
  
  @Override
  public synchronized Collection<Link<ClientCell>> getLinks() {
    boolean _notEquals = (!Objects.equal(this.links, null));
    assert _notEquals;
    Collection<Link<ClientCell>> _values = this.links.values();
    return CollectionLiterals.<Link<ClientCell>>newArrayList(((Link<ClientCell>[])Conversions.unwrapArray(_values, Link.class)));
  }
  
  @Override
  public synchronized boolean hasLink(final int linkId) {
    return this.links.containsKey(Integer.valueOf(linkId));
  }
  
  @Override
  public synchronized void preserverLinks(final Set<Integer> linkIds) {
    Set<Map.Entry<Integer, Link<ClientCell>>> _entrySet = this.links.entrySet();
    final Function1<Map.Entry<Integer, Link<ClientCell>>, Boolean> _function = new Function1<Map.Entry<Integer, Link<ClientCell>>, Boolean>() {
      @Override
      public Boolean apply(final Map.Entry<Integer, Link<ClientCell>> it) {
        Integer _key = it.getKey();
        boolean _contains = linkIds.contains(_key);
        return Boolean.valueOf((!_contains));
      }
    };
    Iterable<Map.Entry<Integer, Link<ClientCell>>> _filter = IterableExtensions.<Map.Entry<Integer, Link<ClientCell>>>filter(_entrySet, _function);
    final Procedure1<Map.Entry<Integer, Link<ClientCell>>> _function_1 = new Procedure1<Map.Entry<Integer, Link<ClientCell>>>() {
      @Override
      public void apply(final Map.Entry<Integer, Link<ClientCell>> it) {
        Link<ClientCell> _value = it.getValue();
        ClientLinksImpl.this.remove(_value);
      }
    };
    IterableExtensions.<Map.Entry<Integer, Link<ClientCell>>>forEach(_filter, _function_1);
    Set<Integer> _keySet = this.links.keySet();
    final Function1<Integer, Boolean> _function_2 = new Function1<Integer, Boolean>() {
      @Override
      public Boolean apply(final Integer it) {
        boolean _contains = linkIds.contains(it);
        return Boolean.valueOf((!_contains));
      }
    };
    net.npg.abattle.common.utils.IterableExtensions.<Integer>removeConditional(_keySet, _function_2);
  }
  
  private void remove(final Link<ClientCell> link) {
    ClientCell _sourceCell = link.getSourceCell();
    int _id = _sourceCell.getId();
    Set<Integer> cellLinks = this.cellToCells.get(Integer.valueOf(_id));
    boolean _equals = Objects.equal(cellLinks, null);
    if (_equals) {
      return;
    }
    ClientCell _destinationCell = link.getDestinationCell();
    int _id_1 = _destinationCell.getId();
    boolean _contains = cellLinks.contains(Integer.valueOf(_id_1));
    boolean _not = (!_contains);
    if (_not) {
      return;
    }
    ClientCell _destinationCell_1 = link.getDestinationCell();
    int _id_2 = _destinationCell_1.getId();
    final boolean success = cellLinks.remove(Integer.valueOf(_id_2));
    assert success;
    int _id_3 = link.getId();
    String _plus = ("Link:" + Integer.valueOf(_id_3));
    String _plus_1 = (_plus + " removed.");
    CommonConstants.LOG.debug(_plus_1);
  }
}
