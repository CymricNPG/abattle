package net.npg.abattle.desktop.impl

import net.npg.abattle.client.dependent.BackPressed
import net.npg.abattle.client.dependent.RequestHandler
import net.npg.abattle.client.dependent.UIDialogComponent
import net.npg.abattle.common.component.ComponentType
import net.npg.abattle.common.utils.impl.DisposeableImpl
import net.npg.abattle.desktop.Main
import org.eclipse.xtend.lib.annotations.AccessorType
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.xtend.lib.annotations.AccessorType.*
import net.npg.abattle.client.dependent.MultiplayerScreen
import net.npg.abattle.client.view.screens.Widgets
import net.npg.abattle.common.i18n.I18N
import net.npg.abattle.client.view.screens.ScreenSwitcher
import com.badlogic.gdx.scenes.scene2d.Stage
import net.npg.abattle.client.view.screens.BasicScreen

@ComponentType
class UIDialogComponentImpl extends DisposeableImpl implements UIDialogComponent {

	@Accessors(AccessorType.PUBLIC_GETTER) RequestHandler requestHandler

	new(Main requestHandler) {
		this.requestHandler = requestHandler;
	}

	public def BackPressed getBackPressedHandler() {
		return requestHandler as BackPressed
	}

	override getMultiplayerScreen() {
		//new MultiplayerScreenImpl
		new MultiplayerScreen() {
			override getDefaultText() {
				return I18N.get("notyetimplemented")
			}

			override setSwitcher(ScreenSwitcher switcher) {
			}

			override create(Widgets widgets, Stage stage, BasicScreen screen) {
			}

		}
	}
}