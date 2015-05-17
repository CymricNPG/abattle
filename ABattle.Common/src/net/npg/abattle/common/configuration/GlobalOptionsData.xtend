package net.npg.abattle.common.configuration

@PropertyStored
class GlobalOptionsData implements SaveableData{
	@PropertyName(name="global.name", defaultValue="", noReset=true)
	@DirtyAccessors String name

	@PropertyName(name="global.gui.coneSelection", defaultValue="true")
	@DirtyAccessors boolean coneSelection

	@PropertyName(name="global.version", defaultValue="")
	@DirtyAccessors String version
}
