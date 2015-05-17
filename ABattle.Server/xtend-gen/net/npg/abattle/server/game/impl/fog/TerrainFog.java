package net.npg.abattle.server.game.impl.fog;

import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.server.game.impl.fog.Fog;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerPlayer;

@SuppressWarnings("all")
public class TerrainFog implements Fog {
  @Override
  public String getName() {
    return "terrainfog";
  }
  
  @Override
  public boolean isVisible(final ClientCell[][] clientBoard, final ServerBoard board, final ServerPlayer player, final ClientCell cell) {
    return true;
  }
}
