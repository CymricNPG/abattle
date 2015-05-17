package net.npg.abattle.client.asset.impl

import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.Gdx

class MyFileHandleResolver implements FileHandleResolver{

	override resolve(String fileName) {
		val classpath = Gdx.files.classpath(fileName)
		if(classpath.exists) {
			return classpath
		}
		Gdx.files.internal(fileName)
	}

}