/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cymric
 *
 */
public class ClientConstants {

	public static final float RADIUS_HEX = 1.0f;
	/**
	 * enable renderer debugging
	 */
	public static final boolean DEBUG_ENABLED = false;

	private static String LOG_NAME = "abattle.client";

	public static Logger LOG = LoggerFactory.getLogger(LOG_NAME);

	public static String ALL_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!?'.,;:()[]{}<>|/@^$-%+=#_&~* ¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";

}
