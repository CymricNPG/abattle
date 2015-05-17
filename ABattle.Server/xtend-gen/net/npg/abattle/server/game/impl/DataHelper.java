package net.npg.abattle.server.game.impl;

import java.util.ArrayList;
import java.util.Collection;
import net.npg.abattle.common.model.Color;
import net.npg.abattle.communication.command.data.PlayerData;
import net.npg.abattle.server.game.GameEnvironment;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerPlayer;

@SuppressWarnings("all")
public class DataHelper {
  public static PlayerData[] createPlayerData(final GameEnvironment game) {
    final ArrayList<PlayerData> players = new ArrayList<PlayerData>();
    ServerGame _serverGame = game.getServerGame();
    Collection<ServerPlayer> _players = _serverGame.getPlayers();
    for (final ServerPlayer player : _players) {
      PlayerData _createPlayerData = DataHelper.createPlayerData(player);
      players.add(_createPlayerData);
    }
    int _size = players.size();
    PlayerData[] _createArray = new PlayerData[_size];
    return players.<PlayerData>toArray(_createArray);
  }
  
  public static PlayerData createPlayerData(final ServerPlayer player) {
    int _id = player.getId();
    String _name = player.getName();
    Color _color = player.getColor();
    int _r = _color.getR();
    Color _color_1 = player.getColor();
    int _g = _color_1.getG();
    Color _color_2 = player.getColor();
    int _b = _color_2.getB();
    return new PlayerData(_id, _name, _r, _g, _b);
  }
}
