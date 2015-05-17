/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.server.model.impl;

import net.npg.abattle.common.model.impl.BoardImpl;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;
import net.npg.abattle.server.model.impl.ServerLinksImpl;

/**
 * @author cymric
 */
@SuppressWarnings("all")
public class ServerBoardImpl extends BoardImpl<ServerPlayer, ServerCell, ServerLinks> implements ServerBoard {
  private ServerLinksImpl links;
  
  public ServerBoardImpl(final int xsize, final int ysize) {
    super(xsize, ysize);
    ServerLinksImpl _serverLinksImpl = new ServerLinksImpl();
    this.links = _serverLinksImpl;
  }
  
  @Override
  public ServerLinks getLinks() {
    return this.links;
  }
}
