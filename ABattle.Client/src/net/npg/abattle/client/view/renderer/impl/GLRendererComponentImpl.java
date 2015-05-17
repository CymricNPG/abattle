/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.renderer.impl;

import net.npg.abattle.client.view.renderer.Camera;
import net.npg.abattle.client.view.renderer.Renderer;
import net.npg.abattle.client.view.renderer.RendererComponent;
import net.npg.abattle.common.component.Component;
import net.npg.abattle.common.utils.impl.DisposeableImpl;

/**
 * @author spatzenegger
 * @TODO remove
 */
public class GLRendererComponentImpl extends DisposeableImpl implements RendererComponent {

	public GLRendererComponentImpl() {
	}

	@Override
	public Class<? extends Component> getInterface() {
		return RendererComponent.class;
	}

	@Override
	public Renderer getRenderer(final Camera camera) {
		return new GLRenderer(camera); // allocates a lot of memory and doesn't free it!
	}

}
