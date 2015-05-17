package net.npg.abattle.common.configuration

@PropertyStored
class GameLoopConfigurationData implements SaveableData{

	@PropertyName(name="loop.logicUpdatesPerSecond", defaultValue="15")
	@DirtyAccessors long logicUpdatesPerSecond

	@PropertyName(name="loop.sentUpdatesPerSecond", defaultValue="15")
	@DirtyAccessors long sentUpdatesPerSecond

	@PropertyName(name="loop.processCommandsPerSecond", defaultValue="20")
	@DirtyAccessors long processCommandsPerSecond

}