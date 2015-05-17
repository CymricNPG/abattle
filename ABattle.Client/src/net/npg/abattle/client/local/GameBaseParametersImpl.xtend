package net.npg.abattle.client.local

import net.npg.abattle.client.GameBaseParameters

@org.eclipse.xtend.lib.annotations.Data class GameBaseParametersImpl implements GameBaseParameters {

	int AIPlayers

	int humanPlayers

	int xSize

	int ySize

	String name
}
