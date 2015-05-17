package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.google.common.base.Objects;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.npg.abattle.client.view.screens.Layout;
import net.npg.abattle.client.view.screens.MyChangeListener;
import net.npg.abattle.client.view.screens.Widgets;
import net.npg.abattle.common.i18n.I18N;
import net.npg.abattle.common.utils.Validate;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class OptionTable {
  private Table table;
  
  private final static float CELLPAD = 4;
  
  private Set<Procedure0> updateProcedures;
  
  private final Widgets widgets;
  
  private final Stage stage;
  
  public OptionTable(final Widgets widgets, final Stage stage) {
    Validate.notNulls(widgets, stage);
    this.widgets = widgets;
    this.stage = stage;
  }
  
  public void create() {
    HashSet<Procedure0> _newHashSet = CollectionLiterals.<Procedure0>newHashSet();
    this.updateProcedures = _newHashSet;
    Table _table = new Table();
    this.table = _table;
    this.table.setFillParent(false);
    Skin _skin = this.widgets.getSkin();
    final ScrollPane scrollpane = new ScrollPane(this.table, _skin);
    scrollpane.setSize(Layout.OPTION_SIZE.x, Layout.OPTION_SIZE.y);
    scrollpane.setPosition(Layout.OPTION_SCROLL.x, Layout.OPTION_SCROLL.y);
    scrollpane.setCancelTouchFocus(false);
    scrollpane.setFadeScrollBars(false);
    this.stage.addActor(scrollpane);
  }
  
  public void finish() {
    final Procedure1<Procedure0> _function = new Procedure1<Procedure0>() {
      @Override
      public void apply(final Procedure0 it) {
        it.apply();
      }
    };
    IterableExtensions.<Procedure0>forEach(this.updateProcedures, _function);
  }
  
  public void fillHeader() {
    this.updateProcedures.clear();
    this.table.clear();
  }
  
  public boolean addTextField(final String label, final String text, final String defaultText, final Procedure1<String> update) {
    boolean _xblockexpression = false;
    {
      Label _createLabel = this.widgets.createLabel(label);
      Cell<Label> _add = this.table.<Label>add(_createLabel);
      this.defaultCellOptions(_add);
      this.table.row();
      String _xifexpression = null;
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(text);
      if (_isNullOrEmpty) {
        _xifexpression = defaultText;
      } else {
        _xifexpression = text;
      }
      final TextField textfield = this.widgets.createTextfield(_xifexpression);
      Cell<TextField> _add_1 = this.table.<TextField>add(textfield);
      Cell _defaultCellOptions = this.defaultCellOptions(_add_1);
      _defaultCellOptions.width(250);
      this.table.row();
      final Procedure0 _function = new Procedure0() {
        @Override
        public void apply() {
          String _text = textfield.getText();
          boolean _notEquals = (!Objects.equal(_text, defaultText));
          if (_notEquals) {
            String _text_1 = textfield.getText();
            update.apply(_text_1);
          }
        }
      };
      this.updateProcedures.add(_function);
      _xblockexpression = textfield.addListener(
        new ClickListener() {
          @Override
          public boolean touchDown(final InputEvent event, final float x, final float y, final int pointer, final int button) {
            final boolean ret = super.touchDown(event, x, y, pointer, button);
            boolean _and = false;
            if (!ret) {
              _and = false;
            } else {
              String _text = textfield.getText();
              boolean _equals = Objects.equal(_text, defaultText);
              _and = _equals;
            }
            if (_and) {
              textfield.setText("");
            }
            return ret;
          }
        });
    }
    return _xblockexpression;
  }
  
  public boolean addSlider(final String label, final int min, final int max, final int selected, final Procedure1<Integer> update, final float step) {
    return this.addSlider(label, min, max, selected, update, step, "");
  }
  
  public boolean addSlider(final String label, final int min, final int max, final int selected, final Procedure1<Integer> update, final float step, final String textSuffix) {
    boolean _xblockexpression = false;
    {
      Label _createLabel = this.widgets.createLabel(label);
      Cell<Label> _add = this.table.<Label>add(_createLabel);
      this.defaultCellOptions(_add);
      this.table.row();
      final Slider slider = this.widgets.addSliderWithoutLabel(min, max, Layout.Game_Para_X.x, Layout.Game_Para_X.y, step);
      Cell<Slider> _add_1 = this.table.<Slider>add(slider);
      Cell _defaultCellOptions = this.defaultCellOptions(_add_1);
      _defaultCellOptions.width(250);
      slider.setValue(selected);
      String _string = Integer.valueOf(selected).toString();
      String _plus = (_string + textSuffix);
      final Label labelWidget = this.widgets.addLabel(0, 0, _plus);
      final Procedure2<ChangeListener.ChangeEvent, Actor> _function = new Procedure2<ChangeListener.ChangeEvent, Actor>() {
        @Override
        public void apply(final ChangeListener.ChangeEvent $0, final Actor $1) {
          float _value = slider.getValue();
          int _intValue = Float.valueOf(_value).intValue();
          String _plus = ("" + Integer.valueOf(_intValue));
          String _plus_1 = (_plus + textSuffix);
          labelWidget.setText(_plus_1);
        }
      };
      MyChangeListener _myChangeListener = new MyChangeListener(_function);
      slider.addListener(_myChangeListener);
      Cell<Label> _add_2 = this.table.<Label>add(labelWidget);
      this.defaultCellOptions(_add_2);
      this.table.row();
      final Procedure0 _function_1 = new Procedure0() {
        @Override
        public void apply() {
          float _value = slider.getValue();
          int _intValue = Float.valueOf(_value).intValue();
          update.apply(Integer.valueOf(_intValue));
        }
      };
      _xblockexpression = this.updateProcedures.add(_function_1);
    }
    return _xblockexpression;
  }
  
  public boolean addSlider(final String label, final int min, final int max, final int selected, final Procedure1<Integer> update) {
    return this.addSlider(label, min, max, selected, update, 1.0f);
  }
  
  public boolean addDropDown(final String label, final Collection<String> items, final String selected, final Procedure1<String> update) {
    boolean _xblockexpression = false;
    {
      Label _createLabel = this.widgets.createLabel(label);
      Cell<Label> _add = this.table.<Label>add(_createLabel);
      this.defaultCellOptions(_add);
      this.table.row();
      final Function1<String, String> _function = new Function1<String, String>() {
        @Override
        public String apply(final String it) {
          return I18N.get(it);
        }
      };
      Iterable<String> _map = IterableExtensions.<String, String>map(items, _function);
      final List<String> i18nItems = IterableExtensions.<String>toList(_map);
      String _get = I18N.get(selected);
      final SelectBox<String> dropDown = this.widgets.createDropDown(_get, i18nItems);
      Cell<SelectBox<String>> _add_1 = this.table.<SelectBox<String>>add(dropDown);
      this.defaultCellOptions(_add_1);
      this.table.row();
      final Procedure0 _function_1 = new Procedure0() {
        @Override
        public void apply() {
          final Function1<String, Boolean> _function = new Function1<String, Boolean>() {
            @Override
            public Boolean apply(final String it) {
              String _get = I18N.get(it);
              String _selected = dropDown.getSelected();
              return Boolean.valueOf(_get.equals(_selected));
            }
          };
          String _findFirst = IterableExtensions.<String>findFirst(items, _function);
          update.apply(_findFirst);
        }
      };
      _xblockexpression = this.updateProcedures.add(_function_1);
    }
    return _xblockexpression;
  }
  
  public boolean addCheckBox(final String label, final boolean checked, final Procedure1<Boolean> update) {
    boolean _xblockexpression = false;
    {
      Label _createLabel = this.widgets.createLabel(label);
      Cell<Label> _add = this.table.<Label>add(_createLabel);
      this.defaultCellOptions(_add);
      final CheckBox box = this.widgets.createCheckBox();
      box.setChecked(checked);
      Cell<CheckBox> _add_1 = this.table.<CheckBox>add(box);
      this.defaultCellOptions(_add_1);
      this.table.row();
      final Procedure0 _function = new Procedure0() {
        @Override
        public void apply() {
          boolean _isChecked = box.isChecked();
          update.apply(Boolean.valueOf(_isChecked));
        }
      };
      _xblockexpression = this.updateProcedures.add(_function);
    }
    return _xblockexpression;
  }
  
  private Cell defaultCellOptions(final Cell cell) {
    Cell _pad = cell.pad(OptionTable.CELLPAD);
    return _pad.align(Align.left);
  }
}
