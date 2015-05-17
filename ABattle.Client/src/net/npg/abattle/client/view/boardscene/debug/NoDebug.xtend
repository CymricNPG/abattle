package net.npg.abattle.client.view.boardscene.debug

class NoDebug implements DebugCell {

	override getTextCreator() {
		return [shape|""]
	}

	protected static final String NAME = "nodebug"

	override getName() {
		NAME
	}

}