/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

/**
 * @author spatzenegger
 * 
 */
public interface VisitableSceneElement {

	public void accept(SceneRenderer visitor);

}
