package net.npg.abattle.client.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.util.List;
import net.npg.abattle.common.component.Component;

@SuppressWarnings("all")
public interface AssetManager extends Component {
  public abstract BitmapFont getNormalFont();
  
  public abstract BitmapFont getSmallFont();
  
  public abstract void loadFonts();
  
  public abstract void waitForAssets();
  
  public abstract void loadIcons(final List<String> filenames);
  
  public abstract Image getImage(final String filename);
  
  public abstract Texture getTexture(final String string);
  
  public abstract String loadTextfile(final String filename);
  
  public abstract Skin getSkin();
}
