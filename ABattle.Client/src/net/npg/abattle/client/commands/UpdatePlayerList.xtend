package net.npg.abattle.client.commands

import net.npg.abattle.client.ClientConstants
import net.npg.abattle.common.model.client.ClientGame
import net.npg.abattle.common.model.client.impl.ClientPlayerImpl
import net.npg.abattle.common.model.impl.ColorImpl
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.data.PlayerData
import net.npg.abattle.common.model.client.ClientPlayer
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.error.ErrorComponent
import net.npg.abattle.common.i18n.I18N

class UpdatePlayerList {

	protected def static void update(PlayerData[] players, ClientGame game) {
		Validate.notNull(players)
		ClientConstants.LOG.info("Players joined:" + players.length)
		for (PlayerData player : players) {
			var color = new ColorImpl(player.r, player.g, player.b)
			var exisitingPlayer = game.players.findFirst[it.id == player.playerId]
			if(exisitingPlayer == null) {
				val newPlayer = new ClientPlayerImpl(player.playerName, player.playerId, color)
				game.addPlayer(newPlayer)
			} else {
				exisitingPlayer.color = color
			}
		}
		game.players.filter[existingPlayer|players.findFirst[it.playerId == existingPlayer.id] == null].forEach[removePlayer(game, it)]
	}

	def static removePlayer(ClientGame game, ClientPlayer player) {
		game.removePlayer(player)
		ComponentLookup.instance.getComponent(ErrorComponent).addError(I18N.format("player_left", player.name), false)
	}

	def static remove(PlayerData data, ClientGame game) {
		val player = game.players.findFirst[it.id == data.playerId]
		ComponentLookup.instance.getComponent(ErrorComponent).addError(I18N.format("player_left", player.name), false)
	}

}
