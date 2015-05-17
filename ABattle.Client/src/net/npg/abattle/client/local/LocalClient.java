/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.local;

import net.npg.abattle.client.lan.ClientGameEnvironment;
import net.npg.abattle.client.view.boardscene.SceneRenderer;
import net.npg.abattle.client.view.boardscene.SceneRendererImpl;
import net.npg.abattle.client.view.main.BoardControl;
import net.npg.abattle.client.view.renderer.Renderer;
import net.npg.abattle.client.view.renderer.RendererComponent;
import net.npg.abattle.client.view.renderer.impl.GLRendererComponentImpl;
import net.npg.abattle.client.view.screens.GameScreen;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.common.component.Component;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.utils.Validate;

/**
 * @author Cymric
 * 
 */
public class LocalClient {

	final private ScreenSwitcher switcher;

	protected ComponentLookup getLookup() {
		return ComponentLookup.getInstance();
	}

	protected GameScreen getMainControl(final ClientGameEnvironment game) {
		Validate.notNulls(game);
		final GameScreen mainControl = new GameScreen(switcher);
		final BoardControl boardControl = new BoardControl(game);
		mainControl.switchScreenControl(boardControl);
		return mainControl;
	}

	protected SceneRenderer getSceneRenderer(final Renderer renderer) {
		return new SceneRendererImpl(renderer);
	}

	protected void initBaseComponents() {
		final RendererComponent rendererComponent = initRendererComponent();
		registerComponent(rendererComponent);
	}

	protected GameScreen initGameView(final ClientGameEnvironment game) throws BaseException {
		return getMainControl(game);
	}

	protected RendererComponent initRendererComponent() {
		return new GLRendererComponentImpl();

	}

	protected <T extends Component> T registerComponent(final T component) {
		return getLookup().registerComponent(component);
	}

	final public GameScreen run(final ClientGameEnvironment game) throws BaseException {
		initBaseComponents();
		final GameScreen mainControl = initGameView(game);
		return mainControl;
	}

	public LocalClient(final ScreenSwitcher switcher) {
		this.switcher = switcher;
	}

}
