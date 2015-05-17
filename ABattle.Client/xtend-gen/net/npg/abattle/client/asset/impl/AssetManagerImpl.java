package net.npg.abattle.client.asset.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.util.List;
import net.npg.abattle.client.ClientConstants;
import net.npg.abattle.client.asset.AssetManager;
import net.npg.abattle.client.asset.impl.Resolutions;
import net.npg.abattle.common.component.ComponentFactory;
import net.npg.abattle.common.component.ComponentType;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.common.utils.impl.DisposeableImpl;

@ComponentType
@ComponentFactory
@SuppressWarnings("all")
public class AssetManagerImpl extends DisposeableImpl implements AssetManager {
  private static String FONT_FILENAME = "xxx";
  
  private static String NORMAL_FONT = "xxx";
  
  private static String SMALL_FONT = "xxx";
  
  private com.badlogic.gdx.assets.AssetManager manager;
  
  private Skin skin;
  
  public AssetManagerImpl() {
    com.badlogic.gdx.assets.AssetManager _assetManager = new com.badlogic.gdx.assets.AssetManager();
    this.manager = _assetManager;
    final InternalFileHandleResolver resolver = new InternalFileHandleResolver();
    FreeTypeFontGeneratorLoader _freeTypeFontGeneratorLoader = new FreeTypeFontGeneratorLoader(resolver);
    this.manager.<FreeTypeFontGenerator, FreeTypeFontGeneratorLoader.FreeTypeFontGeneratorParameters>setLoader(FreeTypeFontGenerator.class, _freeTypeFontGeneratorLoader);
    FreetypeFontLoader _freetypeFontLoader = new FreetypeFontLoader(resolver);
    this.manager.<BitmapFont, FreetypeFontLoader.FreeTypeFontLoaderParameter>setLoader(BitmapFont.class, ".ttf", _freetypeFontLoader);
    this.fixAssetResolutions();
  }
  
  @Override
  public void loadIcons(final List<String> filenames) {
    Validate.notNull(filenames);
    for (final String filename : filenames) {
      boolean _isLoaded = this.manager.isLoaded(filename);
      boolean _not = (!_isLoaded);
      if (_not) {
        this.manager.<Texture>load(filename, Texture.class);
      }
    }
    String _string = AssetManagerImpl.usedDpi.toString();
    String _plus = ("skins/Holo-dark-" + _string);
    String _plus_1 = (_plus + ".json");
    FileHandle _internal = Gdx.files.internal(_plus_1);
    Skin _skin = new Skin(_internal);
    this.skin = _skin;
    BitmapFont _font = this.skin.getFont("default-font");
    BitmapFont.BitmapFontData _data = _font.getData();
    _data.setScale(0.5f, 0.5f);
    BitmapFont _font_1 = this.skin.getFont("thin-font");
    BitmapFont.BitmapFontData _data_1 = _font_1.getData();
    _data_1.setScale(0.5f, 0.5f);
  }
  
  private int calcFontSize(final float origFontSize) {
    return ((int) origFontSize);
  }
  
  @Override
  public Image getImage(final String filename) {
    this.testLoaded(filename);
    final Texture texture = this.manager.<Texture>get(filename, Texture.class);
    Validate.notNull(texture);
    return new Image(texture);
  }
  
  @Override
  public void loadFonts() {
    int _calcFontSize = this.calcFontSize(14);
    this.loadFont(AssetManagerImpl.FONT_FILENAME, AssetManagerImpl.NORMAL_FONT, _calcFontSize);
    int _calcFontSize_1 = this.calcFontSize(18);
    this.loadFont(AssetManagerImpl.FONT_FILENAME, AssetManagerImpl.SMALL_FONT, _calcFontSize_1);
  }
  
  private void loadFont(final String filename, final String fontname, final int size) {
    FreetypeFontLoader.FreeTypeFontLoaderParameter sizeParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
    sizeParams.fontFileName = filename;
    sizeParams.fontParameters.size = size;
    sizeParams.fontParameters.characters = ClientConstants.ALL_CHARACTERS;
    sizeParams.fontParameters.genMipMaps = true;
    this.manager.<BitmapFont>load(fontname, BitmapFont.class, sizeParams);
  }
  
  @Override
  public void waitForAssets() {
    this.manager.finishLoading();
  }
  
  @Override
  public BitmapFont getNormalFont() {
    return this.getFont(AssetManagerImpl.NORMAL_FONT);
  }
  
  private void testLoaded(final String filename) {
    boolean _isLoaded = this.manager.isLoaded(filename);
    boolean _not = (!_isLoaded);
    if (_not) {
      this.waitForAssets();
    }
  }
  
  private BitmapFont getFont(final String name) {
    BitmapFont _xblockexpression = null;
    {
      this.testLoaded(name);
      _xblockexpression = this.manager.<BitmapFont>get(name, BitmapFont.class);
    }
    return _xblockexpression;
  }
  
  @Override
  public BitmapFont getSmallFont() {
    return this.getFont(AssetManagerImpl.SMALL_FONT);
  }
  
  @Override
  public Texture getTexture(final String filename) {
    this.testLoaded(filename);
    final Texture texture = this.manager.<Texture>get(filename, Texture.class);
    Validate.notNull(texture);
    return texture;
  }
  
  @Override
  public String loadTextfile(final String filename) {
    String _xblockexpression = null;
    {
      final FileHandle handle = Gdx.files.internal(filename);
      _xblockexpression = handle.readString();
    }
    return _xblockexpression;
  }
  
  @Override
  public Skin getSkin() {
    return this.skin;
  }
  
  public static int assumedWidth = 480;
  
  public static int assumedHeight = 800;
  
  private static Resolutions usedDpi;
  
  private void fixAssetResolutions() {
    int _xifexpression = (int) 0;
    int _width = Gdx.graphics.getWidth();
    int _height = Gdx.graphics.getHeight();
    boolean _greaterThan = (_width > _height);
    if (_greaterThan) {
      _xifexpression = Gdx.graphics.getWidth();
    } else {
      _xifexpression = Gdx.graphics.getHeight();
    }
    final int landscapeWidth = _xifexpression;
    final float screenDensity = Gdx.graphics.getDensity();
    if ((landscapeWidth <= 320)) {
      if ((screenDensity <= 1.00f)) {
        AssetManagerImpl.usedDpi = Resolutions.ldpi;
      } else {
        AssetManagerImpl.usedDpi = Resolutions.mdpi;
      }
    } else {
      if ((landscapeWidth <= 480)) {
        if ((screenDensity < 1.00f)) {
          AssetManagerImpl.usedDpi = Resolutions.ldpi;
        } else {
          if ((screenDensity == 1.00f)) {
            AssetManagerImpl.usedDpi = Resolutions.mdpi;
          } else {
            if ((screenDensity >= 2.00f)) {
              AssetManagerImpl.usedDpi = Resolutions.xhdpi;
            } else {
              AssetManagerImpl.usedDpi = Resolutions.mdpi;
            }
          }
        }
      } else {
        if ((landscapeWidth <= 854)) {
          if ((screenDensity <= 1.00f)) {
            AssetManagerImpl.usedDpi = Resolutions.hdpi;
          } else {
            AssetManagerImpl.usedDpi = Resolutions.xhdpi;
          }
        } else {
          if ((screenDensity < 1.00f)) {
            AssetManagerImpl.usedDpi = Resolutions.hdpi;
          } else {
            AssetManagerImpl.usedDpi = Resolutions.xhdpi;
          }
        }
      }
    }
  }
  
  public Class<AssetManager> getInterface() {
    return AssetManager.class;
  }
}
