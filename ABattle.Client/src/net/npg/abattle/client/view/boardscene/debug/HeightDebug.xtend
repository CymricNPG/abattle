package net.npg.abattle.client.view.boardscene.debug

class HeightDebug implements DebugCell {

	override getTextCreator() {
		return [shape|shape.cell.height.toString]
	}

	protected static final String NAME = "heightdebug"

	override getName() {
		NAME
	}

}