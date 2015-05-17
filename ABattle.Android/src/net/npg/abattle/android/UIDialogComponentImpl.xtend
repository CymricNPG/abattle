package net.npg.abattle.android

import net.npg.abattle.client.dependent.BackPressed
import net.npg.abattle.client.dependent.RequestHandler
import net.npg.abattle.client.dependent.UIDialogComponent
import net.npg.abattle.common.component.ComponentType
import net.npg.abattle.common.utils.impl.DisposeableImpl
import org.eclipse.xtend.lib.annotations.AccessorType
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.xtend.lib.annotations.AccessorType.*

@ComponentType
class UIDialogComponentImpl extends DisposeableImpl implements UIDialogComponent {

	@Accessors(AccessorType.PUBLIC_GETTER) RequestHandler requestHandler

	new(RequestHandlerImpl requestHandler) {
		this.requestHandler = requestHandler;
	}

	public def BackPressed getBackPressedHandler() {
		return requestHandler as BackPressed
	}

	override getMultiplayerScreen() {
		new MultiplayerScreenImpl()
	}

}