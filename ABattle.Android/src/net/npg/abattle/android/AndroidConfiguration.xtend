package net.npg.abattle.android

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

class AndroidConfiguration {
	public static def AndroidApplicationConfiguration getConfig() {
		val config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = false;
		// config.useGL20 = true;
		return config
	}

}