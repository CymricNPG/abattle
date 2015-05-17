/**
 *
 */
package net.npg.abattle.client.view.screens;


/**
 * @author Cymric
 * 
 */
public interface ScreenSwitcher {

	void switchToScreen(Screens newScreen);

	void switchToScreen(Screens newScreen, Object parameter);

	void switchToScreen(MyScreen newScreen);
}
