package net.npg.abattle.client.view.main;

import net.npg.abattle.client.ClientConstants;
import net.npg.abattle.client.lan.ClientGameEnvironment;
import net.npg.abattle.client.startup.Startup;
import net.npg.abattle.client.view.boardscene.BoardSceneComponent;
import net.npg.abattle.client.view.boardscene.BoardViewModel;
import net.npg.abattle.client.view.boardscene.ErrorMessage;
import net.npg.abattle.client.view.boardscene.ModelBuilder;
import net.npg.abattle.client.view.boardscene.SceneRenderer;
import net.npg.abattle.client.view.command.ViewCommandFactory;
import net.npg.abattle.client.view.command.impl.ViewCommandFactoryImpl;
import net.npg.abattle.client.view.renderer.Camera;
import net.npg.abattle.client.view.renderer.impl.CameraImpl;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.selection.SelectionComponent;
import net.npg.abattle.client.view.selection.SelectionModel;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.error.ErrorComponent;
import net.npg.abattle.common.error.ErrorHandler;
import net.npg.abattle.common.error.ErrorInfo;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.GameStatus;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.utils.FloatPoint;
import net.npg.abattle.common.utils.LifecycleControl;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.common.utils.impl.DisposeableImpl;
import net.npg.abattle.communication.command.CommandQueueClient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.WindowedMean;

public class BoardControl extends DisposeableImpl implements ScreenControl, ErrorHandler {

	private static final long LOG_FPS_TIME = 5 * 1000000000L; // 5 seconds

	private static final long FPS = 23;

	private static final long NANOSECS_TO_NEXT_FRAME = 1000000000L / FPS;

	BitmapFont font;

	WindowedMean renderMean = new WindowedMean(10);

	long startTime = System.nanoTime();

	private final ComponentLookup componentLookup;

	private final SelectionModel selectionModel;

	private final ViewCommandFactory viewCommandFactory;

	private final CommandQueueClient commandQueue;

	private final BoardViewModel boardViewModel;

	private final Camera camera;

	private final ClientGame game;

	private ScreenSwitcher screenSwitcher;

	private final SceneRenderer renderer;

	private volatile boolean leaveGame = false;

	private final ErrorComponent errorComponent;

	private ErrorInfo errorMessage;

	public BoardControl(final ClientGameEnvironment gameEnvironment) {
		Validate.notNulls(gameEnvironment);
		this.componentLookup = ComponentLookup.getInstance();
		this.commandQueue = gameEnvironment.getCommandQueue();
		this.game = gameEnvironment.getGame();
		final HexBase hexBase = new HexBase(ClientConstants.RADIUS_HEX);
		this.selectionModel = createSelectionModel(game, hexBase, game.getLocalPlayer());
		this.boardViewModel = createBoardViewModel(game, hexBase);
		viewCommandFactory = new ViewCommandFactoryImpl(commandQueue, game);
		camera = new CameraImpl();
		camera.setNewViewSize(boardViewModel.getWidth(), boardViewModel.getHeight());
		final BoardSceneComponent boardSceneComponent = componentLookup.getComponent(BoardSceneComponent.class);
		renderer = boardSceneComponent.getSceneRenderer(camera);
		errorComponent = componentLookup.getComponent(ErrorComponent.class);
		errorMessage = null;
		errorComponent.registerErrorHandler(this);
	}

	// TODO rework
	@Override
	public void render() {

		final long startRender = System.nanoTime();

		doGameLogic();
		final long exactDuration = (System.nanoTime() - startRender);
		final float duration = exactDuration / 1000000000.0f;
		renderMean.addValue(duration);

		final long leftOver = NANOSECS_TO_NEXT_FRAME - exactDuration;
		if (leftOver > 0) {
			try {
				Thread.sleep(leftOver / 1000 / 1000);
			} catch (final InterruptedException e) {
				ClientConstants.LOG.warn(e.getMessage(), e);
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		if (System.nanoTime() - startTime > LOG_FPS_TIME) {
			ClientConstants.LOG.warn("fps: " + Gdx.graphics.getFramesPerSecond() + ", max fps:" + (1.0f / (renderMean.getMean())));
			startTime = System.nanoTime();
		}
	}

	private void doGameLogic() {
		renderer.visit(boardViewModel);
		if (GameStatus.FINISHED == game.getStatus() || leaveGame) {
			stopGame();
		}
		doErrorHandling();
	}

	private synchronized void doErrorHandling() {
		if (errorMessage == null) {
			return;
		}
		if (errorMessage.isFatal()) {
			if (screenSwitcher == null) {
				return;
			}
			shutdownGame();
			screenSwitcher.switchToScreen(Screens.Error, errorMessage.getMessage());
		}
	}

	private void stopGame() {
		if (screenSwitcher == null) {
			return;
		}
		shutdownGame();
		if (playerWins()) {
			screenSwitcher.switchToScreen(Screens.Win);
		} else {
			screenSwitcher.switchToScreen(Screens.Loose);
		}
	}

	private void shutdownGame() {
		LifecycleControl.getControl().stopApplication();
		commandQueue.dispose();
		try {
			Thread.sleep(250);
		} catch (final InterruptedException e) {
			// ignore
		}
		errorComponent.removeErrorHandler(this);
		LifecycleControl.recycle();
		Startup.restart0();
	}

	private boolean playerWins() {
		int max = 0;
		Player winPlayer = null;
		for (final Player player : game.getPlayers()) {
			if (player.getStrength() > max) {
				max = player.getStrength();
				winPlayer = player;
			}
		}
		return game.getLocalPlayer().equals(winPlayer);
	}

	private BoardViewModel createBoardViewModel(final ClientGame gameModel, final HexBase hexBase) {
		final ModelBuilder modelBuilder = componentLookup.getComponent(BoardSceneComponent.class).getModelBuilder(hexBase, gameModel.getGameConfiguration());
		final BoardViewModel createBoardModel = modelBuilder.createBoardModel(gameModel, selectionModel.getCursors());
		final ErrorMessage errorMessage = new ErrorMessage(gameModel, hexBase);
		createBoardModel.addErrorMessage(errorMessage);
		componentLookup.getComponent(ErrorComponent.class).registerErrorHandler(errorMessage);
		return createBoardModel;
	}

	private SelectionModel createSelectionModel(final ClientGame gameModel, final HexBase hexBase, final ClientPlayer clientPlayer) {
		return componentLookup.getComponent(SelectionComponent.class).createSelectionModel((ClientBoard) gameModel.getBoard(), hexBase, clientPlayer);
	}

	@Override
	public boolean touchDown(final int x, final int y, final int pointer, final int button) {
		if (camera == null) {
			return true;
		}
		selectionModel.resetSelection();
		final FloatPoint worldCoordinates = camera.unproject(x, y);
		selectionModel.startSelection(worldCoordinates);
		return true;
	}

	@Override
	public boolean touchDragged(final int x, final int y, final int pointer) {
		if (camera == null) {
			return true;
		}
		final FloatPoint worldCoordinates = camera.unproject(x, y);
		selectionModel.dragSelection(worldCoordinates);
		return true;
	}

	@Override
	public boolean touchUp(final int x, final int y, final int pointer, final int button) {
		if (camera == null) {
			return true;
		}
		if (selectionModel.inSelectionMode()) {
			final FloatPoint worldCoordinates = camera.unproject(x, y);
			selectionModel.endSelection(worldCoordinates);
			if (selectionModel.isValid()) {
				viewCommandFactory.createLinkCommand(selectionModel);
			}
		}
		return true;
	}

	@Override
	public void setScreenSwitcher(final ScreenSwitcher screenSwitcher) {
		this.screenSwitcher = screenSwitcher;
	}

	@Override
	public void resize(final int width, final int height) {
		// nothing to do
	}

	@Override
	public void leaveGame() {
		leaveGame = true;
	}

	@Override
	public synchronized void handleError(final ErrorInfo message) {
		this.errorMessage = message;
	}
}
