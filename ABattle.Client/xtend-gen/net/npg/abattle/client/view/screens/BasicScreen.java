package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import net.npg.abattle.client.ClientConstants;
import net.npg.abattle.client.view.screens.BackgroundRender;
import net.npg.abattle.client.view.screens.Icons;
import net.npg.abattle.client.view.screens.Layout;
import net.npg.abattle.client.view.screens.MyScreen;
import net.npg.abattle.client.view.screens.MyStage;
import net.npg.abattle.client.view.screens.ScreenSwitcher;
import net.npg.abattle.client.view.screens.Screens;
import net.npg.abattle.client.view.screens.Widgets;
import net.npg.abattle.common.utils.LifecycleControl;
import net.npg.abattle.common.utils.Validate;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public abstract class BasicScreen implements MyScreen, MyStage.OnHardKeyListener {
  public final static int VIRTUAL_WIDTH = 480;
  
  public final static int VIRTUAL_HEIGHT = 800;
  
  public static int lastHeight = 0;
  
  public static int lastWidth = 0;
  
  public final static int XD = (BasicScreen.VIRTUAL_WIDTH / 2);
  
  public final static int YD = (BasicScreen.VIRTUAL_HEIGHT / 2);
  
  private boolean paused;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private MyStage stage;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private ScreenSwitcher switcher;
  
  private boolean initialized = false;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private Widgets widgets;
  
  private BackgroundRender backgroundRenderer;
  
  private FitViewport viewport;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private Label helpLabel;
  
  public BasicScreen() {
    MyStage _myStage = new MyStage();
    this.stage = _myStage;
    FitViewport _fitViewport = new FitViewport(BasicScreen.VIRTUAL_WIDTH, BasicScreen.VIRTUAL_HEIGHT);
    this.viewport = _fitViewport;
    this.stage.setViewport(this.viewport);
    this.stage.setHardKeyListener(this);
    Widgets _widgets = new Widgets(this.stage);
    this.widgets = _widgets;
    this.addLogo();
    this.addHelpLine();
    this.create();
    Camera _camera = this.stage.getCamera();
    BackgroundRender _backgroundRender = new BackgroundRender(_camera);
    this.backgroundRenderer = _backgroundRender;
  }
  
  public void addHelpLine() {
    this.widgets.addImage(Layout.MENU_MBOX.x, Layout.MENU_MBOX.y, Icons.Message);
    String _defaultText = this.getDefaultText();
    Label _addLabel = this.widgets.addLabel(Layout.MENU_MTEXT.x, Layout.MENU_MTEXT.y, _defaultText);
    this.helpLabel = _addLabel;
    String _defaultText_1 = this.getDefaultText();
    this.widgets.setHelp(this.helpLabel, _defaultText_1);
  }
  
  public String getDefaultText() {
    return "--";
  }
  
  @Override
  public void onHardKey(final int keyCode, final int state) {
    if (((keyCode == Input.Keys.BACK) && (state == 0))) {
      this.backButton();
    }
    if (((keyCode == Input.Keys.MENU) && (state == 0))) {
      this.backButton();
    }
    if (((keyCode == Input.Keys.ESCAPE) && (state == 0))) {
      this.backButton();
    }
  }
  
  public abstract void backButton();
  
  public void menuButton() {
  }
  
  @Override
  public void instantiate(final ScreenSwitcher switcher) {
    Validate.notNull(switcher);
    this.switcher = switcher;
  }
  
  public abstract void create();
  
  public void addLogo() {
    this.widgets.addImage(Layout.Menu_A1.x, Layout.Menu_A1.y, Icons.A);
    this.widgets.addImage(Layout.Menu_B.x, Layout.Menu_B.y, Icons.B);
    this.widgets.addImage(Layout.Menu_A2.x, Layout.Menu_A2.y, Icons.A);
    this.widgets.addImage(Layout.Menu_T1.x, Layout.Menu_T1.y, Icons.T);
    this.widgets.addImage(Layout.Menu_T2.x, Layout.Menu_T2.y, Icons.T);
    this.widgets.addImage(Layout.Menu_L.x, Layout.Menu_L.y, Icons.L);
    this.widgets.addImage(Layout.Menu_E.x, Layout.Menu_E.y, Icons.E);
  }
  
  public boolean init() {
    boolean _xifexpression = false;
    if ((!this.initialized)) {
      boolean _xblockexpression = false;
      {
        Class<? extends BasicScreen> _class = this.getClass();
        String _simpleName = _class.getSimpleName();
        String _plus = ("Init screen:" + _simpleName);
        ClientConstants.LOG.info(_plus);
        _xblockexpression = this.initialized = true;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
  
  @Override
  public void dispose(final boolean isSwitchedDispose) {
    this.dispose();
  }
  
  @Override
  public void dispose() {
    Class<? extends BasicScreen> _class = this.getClass();
    String _simpleName = _class.getSimpleName();
    String _plus = ("Disposed screen:" + _simpleName);
    ClientConstants.LOG.info(_plus);
    this.paused = true;
    this.backgroundRenderer.dispose();
    this.stage.dispose();
  }
  
  @Override
  public void hide() {
    Class<? extends BasicScreen> _class = this.getClass();
    String _simpleName = _class.getSimpleName();
    String _plus = ("Hide screen:" + _simpleName);
    ClientConstants.LOG.info(_plus);
    this.paused = true;
    this.widgets.hide();
  }
  
  public void saveScreeenSize() {
    boolean _or = false;
    int _width = Gdx.graphics.getWidth();
    boolean _lessEqualsThan = (_width <= 0);
    if (_lessEqualsThan) {
      _or = true;
    } else {
      int _height = Gdx.graphics.getHeight();
      boolean _lessEqualsThan_1 = (_height <= 0);
      _or = _lessEqualsThan_1;
    }
    if(_or) return;;
    boolean _or_1 = false;
    int _width_1 = Gdx.graphics.getWidth();
    boolean _notEquals = (BasicScreen.lastWidth != _width_1);
    if (_notEquals) {
      _or_1 = true;
    } else {
      int _height_1 = Gdx.graphics.getHeight();
      boolean _notEquals_1 = (BasicScreen.lastHeight != _height_1);
      _or_1 = _notEquals_1;
    }
    if (_or_1) {
      int _width_2 = Gdx.graphics.getWidth();
      BasicScreen.lastWidth = _width_2;
      int _height_2 = Gdx.graphics.getHeight();
      BasicScreen.lastHeight = _height_2;
    }
  }
  
  @Override
  public void pause() {
    Class<? extends BasicScreen> _class = this.getClass();
    String _simpleName = _class.getSimpleName();
    String _plus = ("Paused screen:" + _simpleName);
    ClientConstants.LOG.info(_plus);
    this.paused = true;
  }
  
  @Override
  public void render(final float delta) {
    try {
      if(this.paused) return;;
      this.labelFix();
      Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      this.backgroundRenderer.drawHexField();
      this.viewport.apply(false);
      float _deltaTime = Gdx.graphics.getDeltaTime();
      float _min = Math.min(_deltaTime, (1 / 30f));
      this.stage.act(_min);
      this.stage.draw();
      try {
        this.render();
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception e = (Exception)_t;
          String _message = e.getMessage();
          ClientConstants.LOG.error(_message, e);
          LifecycleControl _control = LifecycleControl.getControl();
          _control.stopApplication();
          throw e;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void labelFix() {
    boolean _or = false;
    com.badlogic.gdx.utils.StringBuilder _text = this.helpLabel.getText();
    boolean _equals = Objects.equal(_text, null);
    if (_equals) {
      _or = true;
    } else {
      com.badlogic.gdx.utils.StringBuilder _text_1 = this.helpLabel.getText();
      String _string = _text_1.toString();
      boolean _isNullOrEmpty = Strings.isNullOrEmpty(_string);
      _or = _isNullOrEmpty;
    }
    if (_or) {
      String _defaultText = this.getDefaultText();
      this.helpLabel.setText(_defaultText);
    }
  }
  
  public abstract void render();
  
  @Override
  public void resize(final int width, final int height) {
    Viewport _viewport = this.stage.getViewport();
    _viewport.update(width, height, false);
    Class<? extends BasicScreen> _class = this.getClass();
    String _simpleName = _class.getSimpleName();
    String _plus = ((((("Resized to:" + Integer.valueOf(width)) + ",") + Integer.valueOf(height)) + " in ") + _simpleName);
    ClientConstants.LOG.info(_plus);
    this.saveScreeenSize();
  }
  
  @Override
  public void resume() {
    Class<? extends BasicScreen> _class = this.getClass();
    String _simpleName = _class.getSimpleName();
    String _plus = ("Resume game:" + _simpleName);
    ClientConstants.LOG.info(_plus);
    this.paused = false;
  }
  
  @Override
  public void show() {
    Class<? extends BasicScreen> _class = this.getClass();
    String _simpleName = _class.getSimpleName();
    String _plus = ("Show screen:" + _simpleName);
    ClientConstants.LOG.info(_plus);
    this.paused = false;
    this.widgets.show();
    Gdx.input.setInputProcessor(this.stage);
    Gdx.input.setCatchBackKey(true);
  }
  
  public abstract Screens getType();
  
  @Pure
  public MyStage getStage() {
    return this.stage;
  }
  
  @Pure
  public ScreenSwitcher getSwitcher() {
    return this.switcher;
  }
  
  @Pure
  public Widgets getWidgets() {
    return this.widgets;
  }
  
  @Pure
  public Label getHelpLabel() {
    return this.helpLabel;
  }
}
