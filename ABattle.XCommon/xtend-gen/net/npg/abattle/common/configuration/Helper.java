package net.npg.abattle.common.configuration;

import com.badlogic.gdx.Preferences;
import java.util.Properties;

@SuppressWarnings("all")
public class Helper {
  public static float getfloat(final Preferences properties, final String key, final String defaultValue) {
    Float _valueOf = Float.valueOf(defaultValue);
    return properties.getFloat(key, (_valueOf).floatValue());
  }
  
  public static boolean getboolean(final Preferences properties, final String key, final String defaultValue) {
    Boolean _valueOf = Boolean.valueOf(defaultValue);
    return properties.getBoolean(key, (_valueOf).booleanValue());
  }
  
  public static String getString(final Preferences properties, final String key, final String defaultValue) {
    return properties.getString(key, defaultValue);
  }
  
  public static int getint(final Preferences properties, final String key, final String defaultValue) {
    Integer _valueOf = Integer.valueOf(defaultValue);
    return properties.getInteger(key, (_valueOf).intValue());
  }
  
  public static long getlong(final Preferences properties, final String key, final String defaultValue) {
    Long _valueOf = Long.valueOf(defaultValue);
    return properties.getLong(key, (_valueOf).longValue());
  }
  
  public static Float getfloat(final Properties properties, final String key, final String defaultValue) {
    Float _xblockexpression = null;
    {
      final String value = properties.getProperty(key, defaultValue);
      _xblockexpression = Float.valueOf(value);
    }
    return _xblockexpression;
  }
  
  public static Integer getint(final Properties properties, final String key, final String defaultValue) {
    Integer _xblockexpression = null;
    {
      final String value = properties.getProperty(key, defaultValue);
      _xblockexpression = Integer.valueOf(value);
    }
    return _xblockexpression;
  }
  
  public static Long getlong(final Properties properties, final String key, final String defaultValue) {
    Long _xblockexpression = null;
    {
      final String value = properties.getProperty(key, defaultValue);
      _xblockexpression = Long.valueOf(value);
    }
    return _xblockexpression;
  }
  
  public static String getString(final Properties properties, final String key, final String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }
  
  public static boolean getboolean(final Properties properties, final String key, final String defaultValue) {
    Boolean _xblockexpression = null;
    {
      final String value = properties.getProperty(key, defaultValue);
      _xblockexpression = Boolean.valueOf(value);
    }
    return (_xblockexpression).booleanValue();
  }
  
  public static float getfloat(final String defaultValue) {
    return (Float.valueOf(defaultValue)).floatValue();
  }
  
  public static boolean getboolean(final String defaultValue) {
    return (Boolean.valueOf(defaultValue)).booleanValue();
  }
  
  public static String getString(final String defaultValue) {
    return defaultValue;
  }
  
  public static int getint(final String defaultValue) {
    return (Integer.valueOf(defaultValue)).intValue();
  }
  
  public static long getlong(final String defaultValue) {
    return (Long.valueOf(defaultValue)).longValue();
  }
}
