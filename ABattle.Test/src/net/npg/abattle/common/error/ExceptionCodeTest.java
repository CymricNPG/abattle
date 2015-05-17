/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.error;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.npg.abattle.common.model.ModelExceptionCode;

import org.junit.Test;

/**
 * @author Cymric
 * 
 */
public class ExceptionCodeTest {

	/**
	 * Test method for {@link net.npg.abattle.common.error.ExceptionCode#ExceptionCode(int, java.lang.String)}.
	 */
	@Test
	public void testExceptionCode() {
		new ExceptionCode(666, "The number of the Beast");
		try {
			new ExceptionCode(666, "The number of the Beast");
			fail();
		} catch (final IllegalArgumentException e) {
			// ok
		}
	}

	/**
	 * Test method for {@link net.npg.abattle.common.error.ExceptionCode#getCode()}.
	 */
	@Test
	public void testGetCode() {
		assertEquals(1001, ModelExceptionCode.PLAYER_ALREADY_ADDED.getCode());
	}

	/**
	 * Test method for {@link net.npg.abattle.common.error.ExceptionCode#getMessage()}.
	 */
	@Test
	public void testGetMessage() {
		assertEquals("Player already added to game.", ModelExceptionCode.PLAYER_ALREADY_ADDED.getMessage());
	}

}
