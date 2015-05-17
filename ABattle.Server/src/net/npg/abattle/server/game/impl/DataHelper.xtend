package net.npg.abattle.server.game.impl

import java.util.ArrayList
import net.npg.abattle.communication.command.data.PlayerData
import net.npg.abattle.server.game.GameEnvironment
import net.npg.abattle.server.model.ServerPlayer
import static net.npg.abattle.common.utils.MyArrayLiterals.*

class DataHelper {

	def static PlayerData[] createPlayerData(GameEnvironment game) {
		val players = new ArrayList<PlayerData>()
		for (ServerPlayer player : game.getServerGame().getPlayers()) {
			players.add(createPlayerData(player))
		}
		return players.toArray(createArray(players.size))
	}

	def static PlayerData createPlayerData(ServerPlayer player) {
		new PlayerData(player.getId(), player.getName(), player.getColor().getR(), player.getColor().getG(), player.getColor().getB())
	}
}
