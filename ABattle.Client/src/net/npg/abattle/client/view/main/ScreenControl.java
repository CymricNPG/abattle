/**
 *
 */
package net.npg.abattle.client.view.main;

import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.common.utils.Disposeable;

/**
 * @author Cymric
 * 
 */
public interface ScreenControl extends Disposeable {

	void render();

	boolean touchDown(final int x, final int y, final int pointer, final int button);

	boolean touchDragged(final int x, final int y, final int pointer);

	boolean touchUp(final int x, final int y, final int pointer, final int button);

	void setScreenSwitcher(ScreenSwitcher screenSwitcher);

	void resize(int width, int height);

	public void leaveGame();
}
