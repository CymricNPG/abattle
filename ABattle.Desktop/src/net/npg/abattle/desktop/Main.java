/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.desktop;

import java.awt.ScrollPane;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.prefs.Preferences;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.npg.abattle.client.ClientConstants;
import net.npg.abattle.client.dependent.ConfirmInterface;
import net.npg.abattle.client.dependent.RequestHandler;
import net.npg.abattle.client.dependent.ReturnControl;
import net.npg.abattle.client.view.screens.BasicScreen;
import net.npg.abattle.client.view.screens.MenuControl;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.component.ExternalRegisterComponent;
import net.npg.abattle.desktop.impl.UIDialogComponentImpl;

import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * TODO class is a mess ...
 *
 * @author Cymric
 */
public class Main implements RequestHandler, ExternalRegisterComponent {

	private UIDialogComponentImpl uiDialogComponent;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		// Locale.setDefault(Locale.ENGLISH);
		try {
			new Main().showMainMenu();
		} catch (final Exception e) {
			ClientConstants.LOG.error("Uncatched:", e);
		}
	}

	private void showMainMenu() {
		final MenuControl menuControl = new MenuControl(this);

		uiDialogComponent = new UIDialogComponentImpl(this);
		final Preferences userPrefs = Preferences.userRoot().node(Main.class.getCanonicalName());
		final int width = Math.max(userPrefs.getInt("global.windowWidth", BasicScreen.VIRTUAL_WIDTH), BasicScreen.VIRTUAL_WIDTH / 2);
		final int height = Math.max(userPrefs.getInt("global.windowHeight", BasicScreen.VIRTUAL_HEIGHT), BasicScreen.VIRTUAL_HEIGHT / 2);
		new LwjglApplication(menuControl, "TestView for ABattle", width, height).addLifecycleListener(new LifecycleListener() {

			@Override
			public void resume() {
			}

			@Override
			public void pause() {
			}

			@Override
			public void dispose() {
				final Preferences userPrefs = Preferences.userRoot().node(Main.class.getCanonicalName());
				userPrefs.put("global.windowWidth", Integer.toString(BasicScreen.lastWidth));
				userPrefs.put("global.windowHeight", Integer.toString(BasicScreen.lastHeight));
			}
		});
	}

	@Override
	public void confirm(final ConfirmInterface confirmInterface) {
		final int result = JOptionPane.showConfirmDialog(null, confirmInterface.headerText() + "\n" + confirmInterface.questionText());
		switch (result) {
		case JOptionPane.YES_OPTION:
			confirmInterface.yes();
			break;
		default:
			confirmInterface.no();
			break;

		}
	}

	@Override
	public void showHTMLView(final String text, final ReturnControl switchBackFunction) {

		URL url;
		try {
			url = new URL(null, "classpath:help.html", new Handler(ClassLoader.getSystemClassLoader()));
		} catch (final MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		JEditorPane label;
		try {
			label = new JEditorPane(url);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		label.setEditable(false);
		final JFrame frame = new JFrame("Help");
		frame.setSize(525, 500);
		final ScrollPane pane = new ScrollPane();
		pane.add(label);
		frame.add(pane);
		frame.setVisible(true);
		switchBackFunction.switchBack();
	}

	/** A {@link URLStreamHandler} that handles resources on the classpath. */
	public class Handler extends URLStreamHandler {
		/** The classloader to find resources from. */
		private final ClassLoader classLoader;

		public Handler() {
			this.classLoader = getClass().getClassLoader();
		}

		public Handler(final ClassLoader classLoader) {
			this.classLoader = classLoader;
		}

		@Override
		protected URLConnection openConnection(final URL u) throws IOException {
			final URL resourceUrl = classLoader.getResource(u.getPath());
			return resourceUrl.openConnection();
		}
	}

	@Override
	public void registerComponents(final ComponentLookup lookup) {
		lookup.registerComponent(uiDialogComponent);
	}

}
