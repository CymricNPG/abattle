package net.npg.abattle.client.asset.impl

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.scenes.scene2d.ui.Image
import java.util.List
import net.npg.abattle.client.asset.AssetManager
import net.npg.abattle.common.component.ComponentFactory
import net.npg.abattle.common.component.ComponentType
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.common.utils.impl.DisposeableImpl
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import net.npg.abattle.client.ClientConstants
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader

@ComponentType
@ComponentFactory
class AssetManagerImpl extends DisposeableImpl implements AssetManager {

	private static String FONT_FILENAME = "xxx"

	private static String NORMAL_FONT = "xxx"
	private static String SMALL_FONT = "xxx"

	private com.badlogic.gdx.assets.AssetManager manager

	private Skin skin
//own FileHandleResolver
	new() {
		manager = new com.badlogic.gdx.assets.AssetManager()
		val resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont, ".ttf", new FreetypeFontLoader(resolver));
		fixAssetResolutions
	}

	override loadIcons(List<String> filenames) {
		Validate.notNull(filenames)
		for (filename : filenames) {
			if(!manager.isLoaded(filename)) {
				manager.load(filename, Texture)
			}
		}
		skin = new Skin(Gdx.files.internal("skins/Holo-dark-" + usedDpi.toString + ".json"))
		skin.getFont("default-font").data.setScale(0.5f, 0.5f)
		skin.getFont("thin-font").data.setScale(0.5f, 0.5f)
	}

	private def int calcFontSize(float origFontSize) {
		origFontSize as int

	// var densityIndependentSize = origFontSize * Gdx.graphics.getDensity();
	// Math.round(densityIndependentSize);
	}

	override getImage(String filename) {
		testLoaded(filename)
		val texture = manager.get(filename, Texture)
		Validate.notNull(texture)
		return new Image(texture)
	}

	override loadFonts() {
		loadFont(FONT_FILENAME, NORMAL_FONT, calcFontSize(14))
		loadFont(FONT_FILENAME, SMALL_FONT, calcFontSize(18))
	}

	private def loadFont(String filename, String fontname, int size) {
		var sizeParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter()
		sizeParams.fontFileName = filename
		sizeParams.fontParameters.size = size
		sizeParams.fontParameters.characters = ClientConstants.ALL_CHARACTERS
		sizeParams.fontParameters.genMipMaps = true;
		manager.load(fontname, BitmapFont, sizeParams);
	}

	override waitForAssets() {
		manager.finishLoading
	}

	override getNormalFont() {
		getFont(NORMAL_FONT)
	}

	private def testLoaded(String filename) {
		if(!manager.isLoaded(filename)) {
			waitForAssets
		}
	}

	private def getFont(String name) {
		testLoaded(name)
		manager.get(name, BitmapFont)
	}

	override getSmallFont() {
		getFont(SMALL_FONT)
	}

	override getTexture(String filename) {
		testLoaded(filename)
		val texture = manager.get(filename, Texture)
		Validate.notNull(texture)
		return texture
	}

	override loadTextfile(String filename) {
		val handle = Gdx.files.internal(filename)
		handle.readString
	}

	override getSkin() {
		return skin
	}

	public static int assumedWidth = 480;
	public static int assumedHeight = 800;

	static Resolutions usedDpi;

	private def void fixAssetResolutions() {
		/**
		 * We assume landscape view and take width as a criteria
		 * Major width in consideration are 320, 480, 800, 960
		 *
		 */
		val landscapeWidth = if(Gdx.graphics.getWidth() > Gdx.graphics.getHeight()) Gdx.graphics.getWidth() else Gdx.graphics.getHeight();
		val screenDensity = Gdx.graphics.getDensity();

		if(landscapeWidth <= 320) {
			if(screenDensity <= 1.00f) {
				usedDpi = Resolutions.ldpi;
			} else {
				usedDpi = Resolutions.mdpi;
			}
		} else if(landscapeWidth <= 480) {
			if(screenDensity < 1.00f) {
				usedDpi = Resolutions.ldpi;
			} else if(screenDensity == 1.00f) {
				usedDpi = Resolutions.mdpi;
			} else if(screenDensity >= 2.00f) {
				usedDpi = Resolutions.xhdpi;
			} else {
				usedDpi = Resolutions.mdpi;
			}
		} else if(landscapeWidth <= 854) {
			if(screenDensity <= 1.00f) {
				usedDpi = Resolutions.hdpi;
			} else {
				usedDpi = Resolutions.xhdpi;
			}
		} else {
			if(screenDensity < 1.00f) {
				usedDpi = Resolutions.hdpi;
			} else {
				usedDpi = Resolutions.xhdpi;
			}
		}

	}

}
