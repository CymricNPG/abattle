package net.npg.abattle.common.configuration

@PropertyStored
class GameConfigurationData implements SaveableData {

	@PropertyName(name="game.baseGrowthPerTick", defaultValue="200")
	@DirtyAccessors int baseGrowthPerTick

	@PropertyName(name="game.townGrowthPerTick", defaultValue="100")
	@DirtyAccessors int townGrowthPerTick

	@PropertyName(name="game.maxCellStrength", defaultValue="10000")
	@DirtyAccessors int maxCellStrength

	@PropertyName(name="game.maxCellHeight", defaultValue="5")
	@DirtyAccessors int maxCellHeight

	@PropertyName(name="game.maxMovement", defaultValue="800")
	@DirtyAccessors int maxMovement

	@PropertyName(name="game.winCondition", defaultValue="95")
	@DirtyAccessors int winCondition

	@PropertyName(name="game.logic", defaultValue="")
	@DirtyAccessors String logic

	@PropertyName(name="game.randomBases", defaultValue="10")
	@DirtyAccessors int randomBases

	@PropertyName(name="game.terrainInfluence", defaultValue="70")
	@DirtyAccessors int terrainInfluence

	@PropertyName(name="game.peakCount", defaultValue="10")
	@DirtyAccessors int peakCount

	@PropertyName(name="game.fog", defaultValue="")
	@DirtyAccessors String fog

	@PropertyName(name="game.terrainCreator", defaultValue="")
	@DirtyAccessors String terrainCreator

	@PropertyName(name="game.xsize", defaultValue="8")
	@DirtyAccessors int xsize

	@PropertyName(name="game.ysize", defaultValue="8")
	@DirtyAccessors int ysize

	@PropertyName(name="game.playerCount", defaultValue="2")
	@DirtyAccessors int playerCount

}
