package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.npg.abattle.client.asset.AssetManager;
import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.screens.Layout;
import net.npg.abattle.client.view.screens.MyChangeListener;
import net.npg.abattle.client.view.screens.MyTextButton;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.i18n.I18N;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

@SuppressWarnings("all")
public class Widgets {
  private final Stage stage;
  
  private final List<TextButton> buttons;
  
  private AssetManager manager;
  
  private boolean hidden = true;
  
  private String defaultHelpText;
  
  private Label helpLabel;
  
  public Widgets(final Stage stage) {
    this.stage = stage;
    ArrayList<TextButton> _newArrayList = CollectionLiterals.<TextButton>newArrayList();
    this.buttons = _newArrayList;
    ComponentLookup _instance = ComponentLookup.getInstance();
    AssetManager _component = _instance.<AssetManager>getComponent(AssetManager.class);
    this.manager = _component;
  }
  
  public String setHelp(final Label helpLabel, final String defaultHelpText) {
    String _xblockexpression = null;
    {
      this.helpLabel = helpLabel;
      _xblockexpression = this.defaultHelpText = defaultHelpText;
    }
    return _xblockexpression;
  }
  
  public Label addLabel(final float x, final float y, final String message) {
    Label _xblockexpression = null;
    {
      final Label label = this.createLabel(message);
      label.setPosition(x, y);
      this.stage.addActor(label);
      _xblockexpression = label;
    }
    return _xblockexpression;
  }
  
  public void addImage(final float x, final float y, final Icons icon) {
    final Image image = this.createImage(icon);
    image.setPosition(x, y);
    this.stage.addActor(image);
  }
  
  public MyTextButton addButton(final Layout position, final Icons icon, final Procedure0 changeProcedure) {
    MyTextButton _xblockexpression = null;
    {
      final Procedure0 _function = new Procedure0() {
        @Override
        public void apply() {
          if ((!Widgets.this.hidden)) {
            changeProcedure.apply();
          }
        }
      };
      final MyTextButton button = this.createButton(icon, _function);
      button.setPosition(position.x, position.y);
      final Procedure1<Boolean> _function_1 = new Procedure1<Boolean>() {
        @Override
        public void apply(final Boolean it) {
          Widgets.this.changeText(it, icon, Widgets.this.helpLabel, Widgets.this.defaultHelpText);
        }
      };
      button.addHoverListener(_function_1);
      this.stage.addActor(button);
      this.buttons.add(button);
      _xblockexpression = button;
    }
    return _xblockexpression;
  }
  
  private void changeText(final Boolean over, final Icons icon, final Label helpLabel, final String defaultText) {
    boolean _equals = Objects.equal(helpLabel, null);
    if(_equals) return;;
    if ((over).booleanValue()) {
      String _get = I18N.get(("menu_" + icon));
      helpLabel.setText(_get);
    } else {
      helpLabel.setText(defaultText);
    }
  }
  
  public void resetButtons() {
    final Procedure1<TextButton> _function = new Procedure1<TextButton>() {
      @Override
      public void apply(final TextButton it) {
        it.setChecked(false);
      }
    };
    IterableExtensions.<TextButton>forEach(this.buttons, _function);
  }
  
  public Slider addSliderWithoutLabel(final int min, final int max, final float x, final float y) {
    return this.addSliderWithoutLabel(min, max, x, y, 1.0f);
  }
  
  public Slider addSliderWithoutLabel(final int min, final int max, final float x, final float y, final float step) {
    Slider _xblockexpression = null;
    {
      Skin _skin = this.manager.getSkin();
      final Slider slider = new Slider(min, max, step, false, _skin);
      slider.setPosition(x, y);
      slider.setValue(min);
      slider.setWidth(200);
      this.stage.addActor(slider);
      _xblockexpression = slider;
    }
    return _xblockexpression;
  }
  
  public Slider addSlider(final int min, final int max, final float x, final float y) {
    Slider _xblockexpression = null;
    {
      final Slider slider = this.addSliderWithoutLabel(min, max, x, y);
      final Label label = this.addLabel((x + 150), (y + 5), ("" + Integer.valueOf(min)));
      final Procedure2<ChangeListener.ChangeEvent, Actor> _function = new Procedure2<ChangeListener.ChangeEvent, Actor>() {
        @Override
        public void apply(final ChangeListener.ChangeEvent $0, final Actor $1) {
          float _value = slider.getValue();
          int _intValue = Float.valueOf(_value).intValue();
          String _plus = ("" + Integer.valueOf(_intValue));
          label.setText(_plus);
        }
      };
      MyChangeListener _myChangeListener = new MyChangeListener(_function);
      slider.addListener(_myChangeListener);
      _xblockexpression = slider;
    }
    return _xblockexpression;
  }
  
  public boolean show() {
    boolean _xblockexpression = false;
    {
      this.resetButtons();
      _xblockexpression = this.hidden = false;
    }
    return _xblockexpression;
  }
  
  public boolean hide() {
    return this.hidden = true;
  }
  
  public MyTextButton createButton(final Icons icon) {
    return this.createButton(icon, 1.0f, null, false);
  }
  
  public MyTextButton createButton(final Icons icon, final Procedure0 buttonPressed) {
    return this.createButton(icon, 1.0f, buttonPressed, false);
  }
  
  public MyTextButton createButton(final Icons icon, final Procedure0 buttonPressed, final boolean disabled) {
    return this.createButton(icon, 1.0f, buttonPressed, disabled);
  }
  
  public MyTextButton createButton(final Icons icon, final float scale) {
    return this.createButton(icon, scale, null, false);
  }
  
  public TextField createTextfield(final String text) {
    TextField _xblockexpression = null;
    {
      Skin _skin = this.manager.getSkin();
      final TextField textfield = new TextField(text, _skin);
      textfield.setMaxLength(16);
      textfield.selectAll();
      _xblockexpression = textfield;
    }
    return _xblockexpression;
  }
  
  public MyTextButton createButton(final Icons icon, final float scale, final Procedure0 buttonPressed, final boolean disabled) {
    MyTextButton _xblockexpression = null;
    {
      final Texture texture = this.manager.getTexture(icon.filename);
      final TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
      SpriteDrawable _buildSprite = this.buildSprite(texture, Color.LIGHT_GRAY);
      textButtonStyle.up = _buildSprite;
      if ((!disabled)) {
        SpriteDrawable _buildSprite_1 = this.buildSprite(texture, Color.WHITE);
        textButtonStyle.down = _buildSprite_1;
        SpriteDrawable _buildSprite_2 = this.buildSprite(texture, Color.WHITE);
        textButtonStyle.over = _buildSprite_2;
        SpriteDrawable _buildSprite_3 = this.buildSprite(texture, Color.BLUE);
        textButtonStyle.checked = _buildSprite_3;
      } else {
        SpriteDrawable _buildSprite_4 = this.buildSprite(texture, Color.LIGHT_GRAY);
        textButtonStyle.down = _buildSprite_4;
        SpriteDrawable _buildSprite_5 = this.buildSprite(texture, Color.LIGHT_GRAY);
        textButtonStyle.over = _buildSprite_5;
        SpriteDrawable _buildSprite_6 = this.buildSprite(texture, Color.LIGHT_GRAY);
        textButtonStyle.checked = _buildSprite_6;
      }
      BitmapFont _normalFont = this.manager.getNormalFont();
      textButtonStyle.font = _normalFont;
      final MyTextButton button = new MyTextButton("", textButtonStyle);
      boolean _and = false;
      boolean _notEquals = (!Objects.equal(buttonPressed, null));
      if (!_notEquals) {
        _and = false;
      } else {
        _and = (!disabled);
      }
      if (_and) {
        MyChangeListener _myChangeListener = new MyChangeListener(buttonPressed);
        button.addListener(_myChangeListener);
      }
      String _string = icon.toString();
      button.setName(_string);
      _xblockexpression = button;
    }
    return _xblockexpression;
  }
  
  public SpriteDrawable buildSprite(final Texture texture, final Color tint) {
    SpriteDrawable _xblockexpression = null;
    {
      final Sprite sprite = new Sprite(texture);
      sprite.setColor(tint);
      _xblockexpression = new SpriteDrawable(sprite);
    }
    return _xblockexpression;
  }
  
  public Image createImage(final Icons icon) {
    return this.manager.getImage(icon.filename);
  }
  
  public Label createLabel(final String message) {
    Label _xblockexpression = null;
    {
      BitmapFont _smallFont = this.manager.getSmallFont();
      Color _color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
      final Label.LabelStyle labelStyle = new Label.LabelStyle(_smallFont, _color);
      _xblockexpression = new Label(message, labelStyle);
    }
    return _xblockexpression;
  }
  
  public Label createLabel(final String message, final Color color) {
    Label _xblockexpression = null;
    {
      BitmapFont _smallFont = this.manager.getSmallFont();
      final Label.LabelStyle labelStyle = new Label.LabelStyle(_smallFont, color);
      _xblockexpression = new Label(message, labelStyle);
    }
    return _xblockexpression;
  }
  
  public CheckBox createCheckBox() {
    Skin _skin = this.manager.getSkin();
    return new CheckBox("", _skin);
  }
  
  public SelectBox<String> createDropDown(final String selected, final Collection<String> items) {
    Skin _skin = this.manager.getSkin();
    final SelectBox<String> selectBox = new SelectBox<String>(_skin);
    selectBox.setItems(((String[])Conversions.unwrapArray(items, String.class)));
    selectBox.setSelected(selected);
    return selectBox;
  }
  
  public Skin getSkin() {
    return this.manager.getSkin();
  }
}
