package net.npg.abattle.server.model.clientfacade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import net.npg.abattle.common.error.NotAvailableException;
import net.npg.abattle.common.model.Link;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientLinks;
import net.npg.abattle.common.model.impl.IDElementImpl;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerLink;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;
import net.npg.abattle.server.model.clientfacade.Server2ClientLinkFunction;

@SuppressWarnings("all")
public class ClientLinksFacade extends IDElementImpl implements ClientLinks {
  private Server2ClientLinkFunction server2ClientLinkFunction;
  
  private ServerPlayer player;
  
  private ServerBoard board;
  
  public ClientLinksFacade(final ServerBoard board, final ServerPlayer player, final ClientBoard clientBoard) {
    this.board = board;
    this.player = player;
    Server2ClientLinkFunction _server2ClientLinkFunction = new Server2ClientLinkFunction(clientBoard);
    this.server2ClientLinkFunction = _server2ClientLinkFunction;
  }
  
  @Override
  public void addLink(final Link<ClientCell> link) {
    throw new NotAvailableException();
  }
  
  @Override
  public boolean doesLinkExists(final ClientCell startCell, final ClientCell endCell) {
    throw new NotAvailableException();
  }
  
  @Override
  public boolean hasLink(final int linkId) {
    throw new NotAvailableException();
  }
  
  @Override
  public void preserverLinks(final Set<Integer> linkIds) {
    throw new NotAvailableException();
  }
  
  @Override
  public Collection<Link<ClientCell>> getLinks() {
    final List<Link<ClientCell>> links = new ArrayList<Link<ClientCell>>();
    ServerLinks _links = this.board.getLinks();
    Collection<ServerLink> _links_1 = _links.getLinks(this.player);
    for (final Link<ServerCell> link : _links_1) {
      Link<ClientCell> _apply = this.server2ClientLinkFunction.apply(link);
      links.add(_apply);
    }
    return links;
  }
}
