/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.local;

import net.npg.abattle.client.GameBaseParameters;
import net.npg.abattle.client.lan.ClientGameEnvironment;
import net.npg.abattle.client.startup.Startup;
import net.npg.abattle.client.view.screens.GameScreen;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.impl.ClientStructureCopy;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandQueue;
import net.npg.abattle.communication.command.CommandQueueClient;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.communication.command.impl.CommandQueueImpl;
import net.npg.abattle.communication.service.common.ClientInfoResult;
import net.npg.abattle.communication.service.common.GameInfoResult;
import net.npg.abattle.communication.service.common.MutableIntPoint;
import net.npg.abattle.server.model.ServerPlayer;
import net.npg.abattle.server.service.impl.ServerServiceImpl;

/**
 * Initialize a local game and starts it
 * 
 * @author Cymric
 * 
 */
public class LocalGameStartup {

	private static final String LOCAL_PLAYER_NAME = "Local Player";

	private int clientId;

	private final ComponentLookup componentLookup;

	private int gameId;

	private final GameBaseParameters model;

	private final ScreenSwitcher switcher;

	private ServerServiceImpl serverService;

	public LocalGameStartup(final GameBaseParameters model, final ScreenSwitcher switcher) {
		Validate.notNulls(model, switcher);
		this.model = model;
		this.switcher = switcher;
		componentLookup = ComponentLookup.getInstance();
	}

	private ClientGameEnvironment createGame(final CommandQueue commandQueueLocal) throws BaseException {
		assert componentLookup != null;
		assert model != null;
		final ClientInfoResult login = serverService.login(LOCAL_PLAYER_NAME);
		if (!login.success) {
			throw new RuntimeException(login.errorMessage);
		}
		clientId = login.clientInfo.id;
		final GameInfoResult game = serverService.createGame(clientId, model.getHumanPlayers(), MutableIntPoint.from(model.getXSize(), model.getYSize()),
				model.getAIPlayers());
		if (!game.success) {
			throw new RuntimeException(game.errorMessage);
		}
		gameId = game.id;

		serverService.initSingleGame(gameId);

		final ServerPlayer player = getPlayer();
		final ClientGame clientGame = ClientStructureCopy.copy(serverService.getGameEnvironment(gameId).getClientGame(player));
		serverService.getGameEnvironment(gameId).startGame((CommandQueueServer) commandQueueLocal);
		return new ClientGameEnvironmentLocal(clientGame, (CommandQueueClient) commandQueueLocal);
	}

	private LocalClient createLocalClient() {
		return new LocalClient(switcher);
	}

	private ServerPlayer getPlayer() {
		for (final ServerPlayer player : serverService.getGameEnvironment(gameId).getServerGame().getPlayers()) {
			if (player.getId() == clientId) {
				return player;
			}
		}
		throw new IllegalArgumentException();
	}

	public GameScreen run() throws BaseException {
		final CommandQueue commandQueueLocal = new CommandQueueImpl(null, null);
		// start command queue in pause, because this is also called by the server thread and not all client processors
		// are registered
		commandQueueLocal.pause();
		serverService = Startup.l20local((CommandQueueServer) commandQueueLocal);
		final ClientGameEnvironment clientGame = createGame(commandQueueLocal);
		Startup.l30(clientGame);
		commandQueueLocal.resume();
		while (!clientGame.checkGameStart()) {
			try {
				Thread.sleep(200);
			} catch (final InterruptedException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return startLocalClient(clientGame);
	}

	private GameScreen startLocalClient(final ClientGameEnvironment clientGame) throws BaseException {
		final LocalClient runnable = createLocalClient();
		return runnable.run(clientGame);
	}
}
