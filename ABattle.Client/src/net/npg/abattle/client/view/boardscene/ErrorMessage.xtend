package net.npg.abattle.client.view.boardscene

import net.npg.abattle.common.error.ErrorHandler
import net.npg.abattle.common.error.ErrorInfo
import net.npg.abattle.common.hex.Hex
import net.npg.abattle.common.hex.HexBase
import net.npg.abattle.common.model.client.ClientGame
import net.npg.abattle.common.utils.FloatPoint
import net.npg.abattle.common.utils.IntPoint
import org.eclipse.xtend.lib.annotations.AccessorType
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.xtend.lib.annotations.AccessorType.*

class ErrorMessage implements VisitableSceneElement, ErrorHandler {

	private volatile long timeout = 0;

	private static final long DISPLAY_TIME = 1000L * 4

	@Accessors(AccessorType.PUBLIC_GETTER)
	private volatile String errorMessage

	@Accessors(AccessorType.PUBLIC_GETTER)
	private FloatPoint pos

	new(ClientGame game, HexBase hexBase) {
		val hex = new Hex(IntPoint.from(1, game.board.YSize - 1), hexBase)
		pos = new FloatPoint(hex.centerX, hex.bottom + 0.2)
	}

	override accept(SceneRenderer visitor) {
		visitor.visit(this)
	}

	def boolean isVisible() {
		return timeout > System.currentTimeMillis && !errorMessage.isNullOrEmpty
	}

	override void handleError(ErrorInfo message) {
		this.errorMessage = message.message
		timeout = System.currentTimeMillis + DISPLAY_TIME
	}
}