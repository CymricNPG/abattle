/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.client.view.renderer.Camera;
import net.npg.abattle.client.view.renderer.RendererComponent;
import net.npg.abattle.common.component.Component;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.common.utils.impl.DisposeableImpl;

/**
 * @author spatzenegger
 * 
 */
public class BoardSceneComponentImpl extends DisposeableImpl implements BoardSceneComponent {

	@Override
	public Class<? extends Component> getInterface() {
		return BoardSceneComponent.class;
	}

	@Override
	public ModelBuilder getModelBuilder(final HexBase hexBase, final GameConfiguration gameConfiguration) {
		final ConfigurationComponent configurationComponent = ComponentLookup.getInstance().getComponent(ConfigurationComponent.class);
		return new ModelBuilder(hexBase, configurationComponent.getGraphicsConfiguration(), gameConfiguration);

	}

	@Override
	public SceneRenderer getSceneRenderer(final Camera camera) {
		final RendererComponent rendererComponent = ComponentLookup.getInstance().getComponent(RendererComponent.class);
		Validate.notNull(rendererComponent);
		return new SceneRendererImpl(rendererComponent.getRenderer(camera));
	}

}
