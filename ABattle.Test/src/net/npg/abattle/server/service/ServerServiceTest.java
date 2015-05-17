package net.npg.abattle.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.impl.ConfigurationComponentImpl;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.communication.service.ServerService;
import net.npg.abattle.communication.service.common.ClientInfo;
import net.npg.abattle.communication.service.common.ClientInfoResult;
import net.npg.abattle.communication.service.common.GameInfo;
import net.npg.abattle.communication.service.common.GameInfoResult;
import net.npg.abattle.communication.service.common.MutableIntPoint;
import net.npg.abattle.server.service.impl.ServerServiceImpl;

import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerServiceTest {
	private static final String TEST_USER = "testuser";

	private ServerService getServerService() throws BaseException {
		final Mockery context = new Mockery();
		final ServerService serverService = new ServerServiceImpl(context.mock(CommandQueueServer.class));
		assertNotNull(serverService);
		return serverService;
	}

	private ClientInfo loginTestUser(final ServerService serverService) {
		final ClientInfoResult result = serverService.login(TEST_USER);
		assertTrue(result.success);
		assertNotNull(result.clientInfo);
		return result.clientInfo;
	}

	@Before
	public void setUp() throws Exception {
		if (ComponentLookup.isInitialized()) {
			ComponentLookup.shutdownInstance();
		}
		ComponentLookup.createInstance();
	}

	@After
	public void tearDown() throws Exception {
		ComponentLookup.shutdownInstance();
	}

	@Test
	public void testCreateGame() throws BaseException {
		ComponentLookup.getInstance().registerComponent(new ConfigurationComponentImpl());
		final ServerService serverService = getServerService();

		final ClientInfo testUser = loginTestUser(serverService);
		final GameInfoResult gameResult = serverService.createGame(testUser.id, 2, new MutableIntPoint(16, 16), 2);
		assertNotNull(gameResult);
		assertTrue(gameResult.errorMessage, gameResult.success);
		final GameInfo gameInfo = gameResult.gameInfo;
		assertNotNull(gameInfo);
		assertEquals(4, gameInfo.maxPlayers);
		assertEquals(3, gameInfo.currentPlayers);
	}

	@Test
	public void testLogin() throws BaseException {
		final ServerService serverService = getServerService();
		loginTestUser(serverService);
		final ClientInfoResult nullResult = serverService.login(null);
		assertFalse(nullResult.success);
	}

	@Test
	public void testServerServiceImpl() throws BaseException {
		getServerService();
	}

}
