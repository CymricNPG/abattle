package net.npg.abattle.client.asset

import net.npg.abattle.common.component.Component
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Image
import java.util.List
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Skin

interface AssetManager extends Component {

	def BitmapFont getNormalFont()

	def BitmapFont getSmallFont()

	def void loadFonts()

	def void waitForAssets()

	def void loadIcons(List<String> filenames)

	def Image getImage(String filename)

	def Texture getTexture(String string)

	def String loadTextfile(String filename)
	
	def Skin getSkin()
}
