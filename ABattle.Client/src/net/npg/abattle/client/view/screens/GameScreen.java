/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.screens;

import net.npg.abattle.client.ClientConstants;
import net.npg.abattle.client.dependent.ConfirmInterface;
import net.npg.abattle.client.dependent.UIDialogComponent;
import net.npg.abattle.client.view.main.ScreenControl;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.i18n.I18N;
import net.npg.abattle.common.utils.LifecycleControl;
import net.npg.abattle.common.utils.Validate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * a Facade to BoardControl
 *
 * @TODO refactor to a MyScreen (with correct instantiate!
 * @author cymric http://basic4gl.wikispaces.com/2D+Drawing+in+OpenGL
 */
public class GameScreen implements MyScreen, InputProcessor {

	private final LifecycleControl control;

	private ScreenControl screenControl;

	private ScreenSwitcher screenSwitcher;

	public GameScreen(final ScreenSwitcher screenSwitcher) {
		super();
		Validate.notNull(screenSwitcher);
		this.screenSwitcher = screenSwitcher;
		this.control = LifecycleControl.getControl();
		// TODO don't like it here... perhaps better in renderer?
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	}

	public void switchScreenControl(final ScreenControl newScreenControl) {
		Validate.notNull(newScreenControl);
		shutdownScreenControl();
		screenControl = newScreenControl;
		screenControl.setScreenSwitcher(screenSwitcher);
	}

	private void shutdownScreenControl() {
		if (screenControl != null && !screenControl.isDisposed()) {
			screenControl.dispose();
		}
		screenControl = null;
	}

	@Override
	public void dispose(final boolean isSwitchedDispose) {
		if (isSwitchedDispose) {
			shutdownScreenControl();
		} else {
			dispose();
		}
	}

	@Override
	public void dispose() {
		ClientConstants.LOG.info("Window disposed");
		control.stopApplication();
		shutdownScreenControl();
	}

	@Override
	public boolean keyDown(final int keycode) {
		ClientConstants.LOG.info("keyDown!");
		return true;
	}

	@Override
	public boolean keyTyped(final char character) {
		ClientConstants.LOG.info("keyTyped!");
		return true;
	}

	@Override
	public boolean keyUp(final int keycode) {
		if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
			ComponentLookup.getInstance().getComponent(UIDialogComponent.class).getRequestHandler().confirm(new ConfirmInterface() {

				@Override
				public void yes() {
					screenControl.leaveGame();
				}

				@Override
				public void no() {
				}

				@Override
				public String headerText() {
					return I18N.get("leave.header");
				}

				@Override
				public String questionText() {
					return I18N.get("leave.question");
				}

				@Override
				public String yesText() {
					return I18N.get("leave.yes");
				}

				@Override
				public String noText() {
					return I18N.get("leave.no");
				}
			});
		}
		return true;
	}

	@Override
	public boolean mouseMoved(final int arg0, final int arg1) {
		return true;
	}

	@Override
	public void pause() {
		ClientConstants.LOG.info("Window paused");
		control.pauseApplication();
	}

	@Override
	public void resize(final int width, final int height) {
		ClientConstants.LOG.info("App resized:" + width + "/" + height);
		screenControl.resize(width, height);
	}

	@Override
	public void resume() {
		ClientConstants.LOG.info("App resumed");
		control.continueApplication();
	}

	@Override
	public boolean scrolled(final int amount) {
		ClientConstants.LOG.info("scrolled!");
		return true;
	}

	@Override
	public boolean touchDown(final int x, final int y, final int pointer, final int button) {
		ClientConstants.LOG.info("Touch:" + x + "/" + y);
		if (screenControl != null) {
			screenControl.touchDown(x, y, pointer, button);
		}
		return true;
	}

	@Override
	public boolean touchDragged(final int x, final int y, final int pointer) {
		if (screenControl != null) {
			screenControl.touchDragged(x, y, pointer);
		}
		return true;
	}

	@Override
	public boolean touchUp(final int x, final int y, final int pointer, final int button) {
		if (screenControl != null) {
			screenControl.touchUp(x, y, pointer, button);
		}
		return true;
	}

	@Override
	public void render(final float delta) {
		if (control.isPaused()) {
			return;
		}
		if (screenControl != null) {
			screenControl.render();
		}
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		resume();
	}

	@Override
	public void hide() {
		pause();
	}

	/**
	 * @return the screenSwitcher
	 */
	protected ScreenSwitcher getScreenSwitcher() {
		return screenSwitcher;
	}

	@Override
	public void instantiate(final ScreenSwitcher switcher) {
		screenSwitcher = switcher;
	}

}
