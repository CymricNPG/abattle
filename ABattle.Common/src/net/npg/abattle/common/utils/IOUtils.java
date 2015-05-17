/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.utils;

import java.io.Closeable;

import net.npg.abattle.common.CommonConstants;

/**
 * @author cymric
 * 
 */
public class IOUtils {

	public static void safeClose(final Closeable closeable) {
		try {
			closeable.close();
		} catch (final Exception e) {
			CommonConstants.LOG.error("Ignored:", e);
		}
	}
}
