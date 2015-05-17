/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.communication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.communication.service.ServerService;
import net.npg.abattle.communication.service.common.ClientInfoResult;
import net.npg.abattle.server.service.impl.ServerServiceImpl;

import org.jmock.Mockery;
import org.junit.After;
import org.junit.Test;

import com.google.common.base.Strings;

/**
 * @author spatzenegger
 * 
 */
public class LocalCommunicationTest {

	private static final String TEST_USER = "testuser";

	private ServerService createLocalServer() throws BaseException {
		final Mockery context = new Mockery();
		return new ServerServiceImpl(context.mock(CommandQueueServer.class));
	}

	@After
	public void tearDown() throws Exception {
		ComponentLookup.shutdownInstance();
	}

	@Test
	public void test() throws BaseException {
		ComponentLookup.createInstance();
		final ServerService serverService = createLocalServer();
		final ClientInfoResult infoResult = serverService.login(TEST_USER);
		System.err.println(infoResult.errorMessage);
		assertTrue(Strings.isNullOrEmpty(infoResult.errorMessage));
		assertTrue(infoResult.success);
		assertEquals(TEST_USER, infoResult.clientInfo.name);
	}
}
