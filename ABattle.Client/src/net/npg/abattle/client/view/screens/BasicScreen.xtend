package net.npg.abattle.client.view.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.utils.viewport.FitViewport
import net.npg.abattle.client.ClientConstants
import net.npg.abattle.common.utils.LifecycleControl
import net.npg.abattle.common.utils.Validate
import org.eclipse.xtend.lib.annotations.AccessorType
import org.eclipse.xtend.lib.annotations.Accessors

import static net.npg.abattle.client.view.screens.Layout.*
import static net.npg.abattle.common.utils.Controls.*

import static extension org.eclipse.xtend.lib.annotations.AccessorType.*
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.google.common.base.Strings

abstract class BasicScreen implements MyScreen, MyStage.OnHardKeyListener {

	public static final int VIRTUAL_WIDTH = 480
	public static final int VIRTUAL_HEIGHT = 800

	public static int lastHeight = 0;

	public static int lastWidth = 0;

	public static final int XD = VIRTUAL_WIDTH / 2
	public static final int YD = VIRTUAL_HEIGHT / 2

	private boolean paused

	@Accessors(AccessorType.PUBLIC_GETTER) MyStage stage

	@Accessors(AccessorType.PUBLIC_GETTER) ScreenSwitcher switcher

	private boolean initialized = false

	@Accessors(AccessorType.PUBLIC_GETTER) Widgets widgets

	private BackgroundRender backgroundRenderer

	private FitViewport viewport

	@Accessors(AccessorType.PUBLIC_GETTER) private Label helpLabel

	new() {
		stage = new MyStage
		viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT)
		stage.setViewport(viewport)
		stage.setHardKeyListener(this)
		widgets = new Widgets(stage)

		addLogo
		addHelpLine

		create
		backgroundRenderer = new BackgroundRender(stage.camera)

	}

	def void addHelpLine() {
		widgets.addImage(MENU_MBOX.x, MENU_MBOX.y, Icons.Message)
		helpLabel = widgets.addLabel(MENU_MTEXT.x, MENU_MTEXT.y, defaultText)
		widgets.setHelp(helpLabel,defaultText)
	}

	def String getDefaultText() {
		"--"
	}

	override void onHardKey(int keyCode, int state) {
		if(keyCode == Input.Keys.BACK && state == 0) {
			backButton
		}
		if(keyCode == Input.Keys.MENU && state == 0) {
			backButton
		}
		if(keyCode == Input.Keys.ESCAPE && state == 0) {
			backButton
		}
	}

	def void backButton()

	def void menuButton() {
	}

	override instantiate(ScreenSwitcher switcher) {
		Validate.notNull(switcher)
		this.switcher = switcher
	}

	def void create()

	def void addLogo() {
		widgets.addImage(Menu_A1.x, Menu_A1.y, Icons.A)
		widgets.addImage(Menu_B.x, Menu_B.y, Icons.B)
		widgets.addImage(Menu_A2.x, Menu_A2.y, Icons.A)
		widgets.addImage(Menu_T1.x, Menu_T1.y, Icons.T)
		widgets.addImage(Menu_T2.x, Menu_T2.y, Icons.T)
		widgets.addImage(Menu_L.x, Menu_L.y, Icons.L)
		widgets.addImage(Menu_E.x, Menu_E.y, Icons.E)
	}

	def init() {
		if(!initialized) {
			ClientConstants.LOG.info("Init screen:" + this.class.simpleName)
			initialized = true
		}
	}

	override dispose(boolean isSwitchedDispose) {
		dispose
	}

	override dispose() {
		ClientConstants.LOG.info("Disposed screen:" + this.class.simpleName)
		paused = true
		backgroundRenderer.dispose();
		stage.dispose()
	}

	override hide() {
		ClientConstants.LOG.info("Hide screen:" + this.class.simpleName)
		paused = true
		widgets.hide
	}

	def void saveScreeenSize() {
		returnif(Gdx.graphics.width <= 0 || Gdx.graphics.height <= 0)
		if(lastWidth != Gdx.graphics.width || lastHeight != Gdx.graphics.height) {
			lastWidth = Gdx.graphics.width
			lastHeight = Gdx.graphics.height
		}
	}

	override pause() {
		ClientConstants.LOG.info("Paused screen:" + this.class.simpleName)
		paused = true
	}

	override render(float delta) {
		returnif(paused)
		labelFix
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		backgroundRenderer.drawHexField
		viewport.apply(false) // drawhex uses different viewport
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f))
		stage.draw()
		try {
			render
		} catch(Exception e) {
			ClientConstants.LOG.error(e.message, e)
			LifecycleControl.control.stopApplication
			throw e
		}
	}

	def labelFix() {
		if(helpLabel.text == null || Strings.isNullOrEmpty(helpLabel.text.toString)) {
			helpLabel.text = defaultText
		}
	}

	def void render()

	override resize(int width, int height) {
		stage.getViewport().update(width, height, false);
		ClientConstants.LOG.info("Resized to:" + width + "," + height + " in " + this.class.simpleName)
		saveScreeenSize
	}

	override resume() {
		ClientConstants.LOG.info("Resume game:" + this.class.simpleName)
		paused = false
	}

	override show() {
		ClientConstants.LOG.info("Show screen:" + this.class.simpleName)
		paused = false
		widgets.show
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);
	}

	abstract def Screens getType()
}
