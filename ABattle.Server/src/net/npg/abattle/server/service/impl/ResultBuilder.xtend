package net.npg.abattle.server.service.impl

import java.util.ArrayList
import java.util.Collection
import net.npg.abattle.communication.service.common.BooleanResult
import net.npg.abattle.communication.service.common.ClientInfo
import net.npg.abattle.communication.service.common.ClientInfoResult
import net.npg.abattle.communication.service.common.GameInfo
import net.npg.abattle.communication.service.common.GameInfoResult
import net.npg.abattle.communication.service.common.GameParameterInfoBuilder
import net.npg.abattle.communication.service.common.MutableIntPoint
import net.npg.abattle.server.ServerConstants
import net.npg.abattle.server.game.GameEnvironment
import net.npg.abattle.server.model.ServerPlayer
import net.npg.abattle.communication.service.common.GameInfoResultBuilder
import net.npg.abattle.common.utils.Validate

public class ResultBuilder {

	static def BooleanResult buildBooleanResultError(String message) {
		ServerConstants.LOG.debug(message)
		new BooleanResult(message, false)
	}

	static def BooleanResult buildBooleanResultSuccess() {
		new BooleanResult("", true)
	}

	static def ClientInfoResult buildClientInfoResultError(String message) {
		new ClientInfoResult(null, message, false)
	}

	static def GameInfoResult buildGameInfoResultError(String message) {
		new GameInfoResult(null, 0, message, false)
	}

	static def GameInfoResult buildGameInfoResultSuccess(GameEnvironment game) {
		new GameInfoResult(convert(game), game.id, "", true)
	}

	static def BooleanResult buildResult(boolean success, String errorMessage) {
		if (success) {
			buildBooleanResultSuccess
		} else {
			buildBooleanResultError(errorMessage)
		}
	}

	static def ClientInfo[] convert(Collection<ServerPlayer> players) {
		val clientInfos = new ArrayList<ClientInfo>
		for (ServerPlayer player : players) {
			clientInfos.add(convert(player))
		}
		clientInfos
	}

	static def GameInfo convert(GameEnvironment gameEnvironment) {
		val game = gameEnvironment.getServerGame
		val gameInfo = new GameInfo
		gameInfo.currentPlayers = game.players.size
		gameInfo.maxPlayers = game.maxPlayers
		gameInfo.players = convert(game.players)
		gameInfo.parameters = new GameParameterInfoBuilder().maxCellHeight(game.gameConfiguration.configuration.maxCellHeight).
			maxCellStrength(game.gameConfiguration.configuration.maxCellStrength).maxMovement(game.gameConfiguration.configuration.maxMovement).
			size(MutableIntPoint.from(gameEnvironment.serverGame.size)).
			build
		gameInfo
	}

	static def ClientInfo convert(ServerPlayer player) {
		val clientInfo = new ClientInfo
		clientInfo.id = player.id
		clientInfo.name = player.name
		clientInfo
	}

	static def BooleanResult unknownGame(int gameId) {
		buildBooleanResultError("Unknown Game:" + gameId)
	}

	static def GameInfoResult toGameInfoResult(BooleanResult result) {
		Validate.isFalse(result.success)
		return new GameInfoResultBuilder().success(false).errorMessage(result.errorMessage).build
	}

	static def BooleanResult unknownPlayer(int playerId) {
		return buildBooleanResultError("Unknown Player:" + playerId)
	}
}
