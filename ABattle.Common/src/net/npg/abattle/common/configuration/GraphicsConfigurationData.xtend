package net.npg.abattle.common.configuration

@PropertyStored
class GraphicsConfigurationData implements SaveableData{

	@PropertyName(name="graphics.maxUnitSize", defaultValue="0.8")
	@DirtyAccessors
	float maxUnitSize

	@PropertyName(name="graphics.minUnitSize", defaultValue="0.1")
	@DirtyAccessors
	float minUnitSize

	@PropertyName(name="graphics.structureSize", defaultValue="0.8")
	@DirtyAccessors
	float structureSize

	@PropertyName(name="graphics.fight.radius", defaultValue="0.8")
	@DirtyAccessors
	float fightRadius

	@PropertyName(name="graphics.fight.cooldown", defaultValue="20")
	@DirtyAccessors
	int cooldownFrames

	@PropertyName(name="graphics.fight.bombAddTime", defaultValue="200")
	@DirtyAccessors
	int bombAddTime

	@PropertyName(name="graphics.fight.bombGrowTime", defaultValue="40")
	@DirtyAccessors
	int bombGrowTime

	@PropertyName(name="graphics.cellShading", defaultValue="false")
	@DirtyAccessors
	boolean cellShading

	@PropertyName(name="graphics.debugCell", defaultValue="")
	@DirtyAccessors
	String debugCell

}