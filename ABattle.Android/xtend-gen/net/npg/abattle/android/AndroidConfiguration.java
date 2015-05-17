package net.npg.abattle.android;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

@SuppressWarnings("all")
public class AndroidConfiguration {
  public static AndroidApplicationConfiguration getConfig() {
    final AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
    config.useAccelerometer = false;
    config.useCompass = false;
    config.useWakelock = false;
    return config;
  }
}
