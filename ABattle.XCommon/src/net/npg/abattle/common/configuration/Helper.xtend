package net.npg.abattle.common.configuration

import java.util.Properties
import com.badlogic.gdx.Preferences

public class Helper {

	public static def float getfloat(Preferences properties, String key, String defaultValue) {
		properties.getFloat(key, Float.valueOf(defaultValue))
	}

	public static def boolean getboolean(Preferences properties, String key, String defaultValue) {
		properties.getBoolean(key, Boolean.valueOf(defaultValue))
	}

	public static def String getString(Preferences properties, String key, String defaultValue) {
		properties.getString(key, defaultValue)
	}

	public static def int getint(Preferences properties, String key, String defaultValue) {
		properties.getInteger(key, Integer.valueOf(defaultValue))
	}

	public static def long getlong(Preferences properties, String key, String defaultValue) {
		properties.getLong(key, Long.valueOf(defaultValue))
	}

	public static def getfloat(Properties properties, String key, String defaultValue) {
		val value = properties.getProperty(key, defaultValue)
		Float.valueOf(value)
	}

	public static def getint(Properties properties, String key, String defaultValue) {
		val value = properties.getProperty(key, defaultValue)
		Integer.valueOf(value)
	}

	public static def getlong(Properties properties, String key, String defaultValue) {
		val value = properties.getProperty(key, defaultValue)
		Long.valueOf(value)
	}

	public static def getString(Properties properties, String key, String defaultValue) {
		properties.getProperty(key, defaultValue)
	}

	public static def boolean getboolean(Properties properties, String key, String defaultValue) {
		val value = properties.getProperty(key, defaultValue)
		Boolean.valueOf(value)
	}

	public static def float getfloat(String defaultValue) {
		Float.valueOf(defaultValue)
	}

	public static def boolean getboolean(String defaultValue) {
		Boolean.valueOf(defaultValue)
	}

	public static def String getString(String defaultValue) {
		defaultValue
	}

	public static def int getint(String defaultValue) {
		Integer.valueOf(defaultValue)
	}

	public static def long getlong(String defaultValue) {
		Long.valueOf(defaultValue)
	}

}
