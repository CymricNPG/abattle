package net.npg.abattle.communication.network.impl

import com.google.common.base.Optional
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.ErrorCommand
import net.npg.abattle.communication.command.GameCommand
import net.npg.abattle.communication.network.NetworkServer
import net.npg.abattle.communication.service.ServerService

class NetworkServerProcessor implements CommandProcessor<GameCommand> {

	NetworkServer connection

	new(NetworkServer connection) {
		Validate.notNulls(connection)
		this.connection = connection
	}

	override Optional<ErrorCommand> execute(GameCommand command, int destination) {
		if(!connection.sendTo(command, destination)) {
			val service = connection.getService(ServerService.SERVER_SERVICE_ID) as ServerService
			service.leaveGames(destination)
		}
		Optional.absent
	}

}
