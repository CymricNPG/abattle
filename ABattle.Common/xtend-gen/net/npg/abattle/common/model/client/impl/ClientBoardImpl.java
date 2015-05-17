/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model.client.impl;

import com.google.common.base.Objects;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientLinks;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.client.impl.ClientLinksImpl;
import net.npg.abattle.common.model.impl.BoardImpl;

@SuppressWarnings("all")
public class ClientBoardImpl extends BoardImpl<ClientPlayer, ClientCell, ClientLinks> implements ClientBoard {
  private final ClientLinks links;
  
  public ClientBoardImpl(final int xsize, final int ysize) {
    super(xsize, ysize);
    ClientLinksImpl _clientLinksImpl = new ClientLinksImpl();
    this.links = _clientLinksImpl;
  }
  
  public ClientBoardImpl(final int id, final int xsize, final int ysize) {
    super(id, xsize, ysize);
    ClientLinksImpl _clientLinksImpl = new ClientLinksImpl();
    this.links = _clientLinksImpl;
  }
  
  @Override
  public ClientLinks getLinks() {
    boolean _notEquals = (!Objects.equal(this.links, null));
    assert _notEquals;
    return this.links;
  }
}
