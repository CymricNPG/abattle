/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.renderer;

import net.npg.abattle.common.component.Component;

/**
 * @author spatzenegger
 * 
 */
public interface RendererComponent extends Component {
	public Renderer getRenderer(Camera camera);
}
