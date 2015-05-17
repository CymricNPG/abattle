package net.npg.abattle.common.model.client;

import java.util.Collection;
import java.util.HashSet;
import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.Link;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.impl.ClientCellImpl;
import net.npg.abattle.common.model.client.impl.ClientLinksImpl;
import net.npg.abattle.common.model.impl.LinkImpl;
import net.npg.abattle.common.utils.IntPoint;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class ClientLinksTest {
  @Test
  public void testAddLink() {
    final ClientLinksImpl cli = new ClientLinksImpl();
    Collection<Link<ClientCell>> _links = cli.getLinks();
    Assert.assertNotNull(_links);
    Collection<Link<ClientCell>> _links_1 = cli.getLinks();
    int _size = _links_1.size();
    Assert.assertEquals(0, _size);
    final Link<ClientCell> link = this.newLink();
    cli.addLink(link);
    Collection<Link<ClientCell>> _links_2 = cli.getLinks();
    int _size_1 = _links_2.size();
    Assert.assertEquals(1, _size_1);
  }
  
  @Test
  public void testPreserveLink() {
    final ClientLinksImpl cli = new ClientLinksImpl();
    Collection<Link<ClientCell>> _links = cli.getLinks();
    Assert.assertNotNull(_links);
    Collection<Link<ClientCell>> _links_1 = cli.getLinks();
    int _size = _links_1.size();
    Assert.assertEquals(0, _size);
    final Link<ClientCell> link = this.newLink();
    cli.addLink(link);
    Collection<Link<ClientCell>> _links_2 = cli.getLinks();
    int _size_1 = _links_2.size();
    Assert.assertEquals(1, _size_1);
    ClientCell _sourceCell = link.getSourceCell();
    ClientCell _destinationCell = link.getDestinationCell();
    boolean _doesLinkExists = cli.doesLinkExists(_sourceCell, _destinationCell);
    Assert.assertTrue(_doesLinkExists);
    int _id = link.getId();
    final HashSet<Integer> links = CollectionLiterals.<Integer>newHashSet(Integer.valueOf(_id));
    cli.preserverLinks(links);
    Collection<Link<ClientCell>> _links_3 = cli.getLinks();
    int _size_2 = _links_3.size();
    Assert.assertEquals(1, _size_2);
    ClientCell _sourceCell_1 = link.getSourceCell();
    ClientCell _destinationCell_1 = link.getDestinationCell();
    boolean _doesLinkExists_1 = cli.doesLinkExists(_sourceCell_1, _destinationCell_1);
    Assert.assertTrue(_doesLinkExists_1);
  }
  
  @Test
  public void testRemoveLink() {
    final ClientLinksImpl cli = new ClientLinksImpl();
    Collection<Link<ClientCell>> _links = cli.getLinks();
    Assert.assertNotNull(_links);
    Collection<Link<ClientCell>> _links_1 = cli.getLinks();
    int _size = _links_1.size();
    Assert.assertEquals(0, _size);
    final Link<ClientCell> link = this.newLink();
    cli.addLink(link);
    Collection<Link<ClientCell>> _links_2 = cli.getLinks();
    int _size_1 = _links_2.size();
    Assert.assertEquals(1, _size_1);
    ClientCell _sourceCell = link.getSourceCell();
    ClientCell _destinationCell = link.getDestinationCell();
    boolean _doesLinkExists = cli.doesLinkExists(_sourceCell, _destinationCell);
    Assert.assertTrue(_doesLinkExists);
    final HashSet<Integer> links = CollectionLiterals.<Integer>newHashSet();
    cli.preserverLinks(links);
    Collection<Link<ClientCell>> _links_3 = cli.getLinks();
    int _size_2 = _links_3.size();
    Assert.assertEquals(0, _size_2);
    ClientCell _sourceCell_1 = link.getSourceCell();
    ClientCell _destinationCell_1 = link.getDestinationCell();
    boolean _doesLinkExists_1 = cli.doesLinkExists(_sourceCell_1, _destinationCell_1);
    boolean _not = (!_doesLinkExists_1);
    Assert.assertTrue(_not);
  }
  
  public Link<ClientCell> newLink() {
    LinkImpl<ClientCell> _xblockexpression = null;
    {
      final ClientCellImpl sourceCell = this.newCell();
      final ClientCellImpl destinationCell = this.newCell();
      int _id = this.id();
      _xblockexpression = new LinkImpl<ClientCell>(_id, sourceCell, destinationCell);
    }
    return _xblockexpression;
  }
  
  private static int ID = 0;
  
  public ClientCellImpl newCell() {
    ClientCellImpl _xblockexpression = null;
    {
      final Mockery context = new Mockery();
      final CheckModelElement check = context.<CheckModelElement>mock(CheckModelElement.class);
      final Expectations ex = new Expectations();
      ex.<CheckModelElement>allowing(check);
      context.checking(ex);
      int _id = this.id();
      int _id_1 = this.id();
      int _id_2 = this.id();
      IntPoint _from = IntPoint.from(_id_1, _id_2);
      _xblockexpression = new ClientCellImpl(_id, _from, 0, CellTypes.PLAIN, check);
    }
    return _xblockexpression;
  }
  
  public int id() {
    int _xblockexpression = (int) 0;
    {
      ClientLinksTest.ID = (ClientLinksTest.ID + 1);
      _xblockexpression = ClientLinksTest.ID;
    }
    return _xblockexpression;
  }
}
