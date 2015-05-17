/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.client.view.renderer.Camera;
import net.npg.abattle.common.component.Component;
import net.npg.abattle.common.component.Reusable;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.GameConfiguration;

/**
 * @author spatzenegger
 * 
 */
public interface BoardSceneComponent extends Component, Reusable {

	public ModelBuilder getModelBuilder(HexBase hexBase, GameConfiguration gameConfiguration);

	public SceneRenderer getSceneRenderer(Camera camera);

}
