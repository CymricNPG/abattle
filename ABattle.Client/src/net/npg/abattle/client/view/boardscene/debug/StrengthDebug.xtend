package net.npg.abattle.client.view.boardscene.debug

class StrengthDebug implements DebugCell {

	override getTextCreator() {
		return [shape|shape.cell.strength.toString]
	}

	protected static final String NAME = "strengthdebug"

	override getName() {
		NAME
	}

}