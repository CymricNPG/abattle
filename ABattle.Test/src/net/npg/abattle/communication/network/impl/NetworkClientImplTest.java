/**
 * 
 */
package net.npg.abattle.communication.network.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.configuration.NetworkConfigurationData;
import net.npg.abattle.communication.network.NetworkClient;
import net.npg.abattle.communication.network.NetworkServer;
import net.npg.abattle.communication.network.data.NetworkGameInfo;
import net.npg.abattle.communication.service.ServerService;
import net.npg.abattle.communication.service.common.BooleanResult;
import net.npg.abattle.communication.service.common.ClientInfo;
import net.npg.abattle.communication.service.common.ClientInfoResult;
import net.npg.abattle.communication.service.common.GameInfo;
import net.npg.abattle.communication.service.common.GameInfoResult;
import net.npg.abattle.communication.service.common.GameParameterInfo;
import net.npg.abattle.communication.service.common.MutableIntPoint;
import net.npg.abattle.server.service.impl.ServerServiceImpl;

import org.junit.Before;
import org.junit.Test;

import com.esotericsoftware.minlog.Log;
import com.google.common.base.Optional;

/**
 * @author Cymric
 * 
 */
public class NetworkClientImplTest {

	@Before
	public void setUp() throws Exception {
		Log.DEBUG();
	}

	/**
	 * Test method for
	 * {@link net.npg.abattle.communication.network.impl.NetworkClientImpl#NetworkClientImpl(net.npg.abattle.common.configuration.NetworkConfigurationData)}
	 * .
	 */
	@Test
	public void testNetworkClientImpl() {
		createNetworkClient();
	}

	/**
	 * Test method for {@link net.npg.abattle.communication.network.impl.NetworkClientImpl#discoverHostAndGame()}.
	 */
	@Test
	public void testDiscoverHost() {
		final NetworkClient nc = createNetworkClient();
		assertFalse(nc.getServerService().isPresent());
		final Optional<NetworkGameInfo> game = nc.discoverHostAndGame();
		assertFalse(game.isPresent());
	}

	/**
	 * Test method for {@link net.npg.abattle.communication.network.impl.NetworkClientImpl#getServerService()}.
	 */
	@Test
	public void testGetServerService() {
		final NetworkClient nc = createNetworkClient();
		assertFalse(nc.getServerService().isPresent());
	}

	/**
	 * Test method for {@link net.npg.abattle.communication.network.impl.NetworkClientImpl#dispose()}.
	 */
	@Test
	public void testDispose() {
		final NetworkClient nc = createNetworkClient();
		nc.dispose();
	}

	private NetworkClient createNetworkClient() {
		final NetworkConfigurationData config = new NetworkConfigurationData(new Properties());
		final NetworkClientImpl nc = new NetworkClientImpl(config);
		return nc;
	}

	/**
	 * Test method for {@link net.npg.abattle.communication.network.impl.NetworkClientImpl#getFoundGame()}.
	 */
	@Test
	public void testGetFoundGame() {
		final NetworkServer ns = startNetworkServer();
		try {
			final NetworkClient nc = createNetworkClient();
			assertFalse(nc.getServerService().isPresent());
			nc.discoverHostAndGame();
			assertTrue(nc.getFoundGame().isPresent());
		} finally {
			try {
				ns.dispose();
			} catch (final Exception e) {
				CommonConstants.LOG.error(e.getMessage(), e);
			}
		}
	}

	private NetworkServer startNetworkServer() {
		final NetworkServer ns = new NetworkServerImpl(new NetworkConfigurationData(new Properties()));
		ns.start();
		final ServerService service = new ServerService() {

			@Override
			public GameInfoResult createGame(final int playerId, final int maxPlayers, final MutableIntPoint size, final int computerPlayers) {
				final GameInfo game = new GameInfo();
				game.currentPlayers = 1;
				game.maxPlayers = maxPlayers;
				game.players = new ClientInfo[0];
				game.parameters = new GameParameterInfo();
				return new GameInfoResult(game, 42, "", true);
			}

			@Override
			public GameInfoResult[] getPendingGames() {
				final GameInfoResult[] games = new GameInfoResult[1];
				games[0] = createGame(1, 15, MutableIntPoint.from(3, 5), 1);
				return games;
			}

			@Override
			public GameInfoResult joinGame(final int gameId, final int clientId) {
				return null;
			}

			@Override
			public BooleanResult leaveGame(final int gameId, final int clientId) {
				return null;
			}

			@Override
			public ClientInfoResult login(final String name) {
				return null;
			}

			@Override
			public BooleanResult link(final int gameId, final int clientId, final MutableIntPoint startCell, final MutableIntPoint endCell, final boolean create) {
				return null;
			}

			@Override
			public void leaveGames(final int clientId) {

			}

			@Override
			public BooleanResult initSingleGame(final int gameId) {
				return null;
			}

			@Override
			public BooleanResult ping(final int gameId) {
				return null;
			}
		};

		ns.registerService(service, ServerServiceImpl.SERVER_SERVICE_ID);
		return ns;
	}

}
