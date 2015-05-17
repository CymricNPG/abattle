package net.npg.abattle.server.model.impl;

import net.npg.abattle.common.model.impl.LinkImpl;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerLink;
import net.npg.abattle.server.model.ServerPlayer;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class ServerLinkImpl extends LinkImpl<ServerCell> implements ServerLink {
  @Accessors
  private ServerPlayer Player;
  
  public ServerLinkImpl(final ServerCell sourceCell, final ServerCell destinationCell, final ServerPlayer player) {
    super(sourceCell, destinationCell);
    this.setPlayer(player);
  }
  
  @Pure
  public ServerPlayer getPlayer() {
    return this.Player;
  }
  
  public void setPlayer(final ServerPlayer Player) {
    this.Player = Player;
  }
}
