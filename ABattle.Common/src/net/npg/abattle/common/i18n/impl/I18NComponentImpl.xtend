package net.npg.abattle.common.i18n.impl

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.I18NBundle
import net.npg.abattle.common.component.ComponentType
import net.npg.abattle.common.i18n.I18NComponent
import net.npg.abattle.common.utils.impl.DisposeableImpl
import net.npg.abattle.common.CommonConstants

@ComponentType
class I18NComponentImpl extends DisposeableImpl implements I18NComponent {

	I18NBundle myBundle

	new() {
		val baseFileHandle = Gdx.files.internal("messages")
		myBundle = I18NBundle.createBundle(baseFileHandle)
	}

	override format(String key, String... params) {
		myBundle.format(key,  params as Object [])
	}

	override get(String key) {
		try {
			myBundle.get(key)
		} catch (java.util.MissingResourceException e) {
			CommonConstants.LOG.error("Cannot find key:"+key)
			return key
		}
	}

}
