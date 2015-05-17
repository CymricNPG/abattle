/**
 *
 */
package net.npg.abattle.client.dependent;

import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Widgets;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * @author cymric
 *
 */
public interface MultiplayerScreen {

	void create(Widgets widgets, Stage stage, BasicScreen screen);

	String getDefaultText();

	void setSwitcher(ScreenSwitcher switcher);

}
