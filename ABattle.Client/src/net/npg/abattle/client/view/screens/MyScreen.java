/**
 *
 */
package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.Screen;

/**
 * @author cymric
 * 
 */
public interface MyScreen extends Screen {

	void instantiate(ScreenSwitcher switcher);

	public void dispose(boolean isSwitchedDispose);
}
