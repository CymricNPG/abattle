package net.npg.abattle.communication.network.data

import java.net.InetAddress

@Data class NetworkGameInfo {

	InetAddress ipAddr
	String gameName
	int currentPlayer
	int maxPlayer
	int gameId

}
