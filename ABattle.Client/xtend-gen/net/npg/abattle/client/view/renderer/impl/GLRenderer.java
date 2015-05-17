/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.renderer.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import net.npg.abattle.client.asset.AssetManager;
import net.npg.abattle.client.view.boardscene.RendererUtils;
import net.npg.abattle.client.view.renderer.Camera;
import net.npg.abattle.client.view.renderer.Renderer;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.utils.FloatPoint;
import net.npg.abattle.common.utils.MutableFloatPoint;
import net.npg.abattle.common.utils.Validate;

/**
 * @author spatzenegger
 */
@SuppressWarnings("all")
public class GLRenderer implements Renderer {
  private Camera camera;
  
  private BitmapFont font;
  
  public ImmediateModeRenderer20 immediateModeRenderer;
  
  private MutableFloatPoint tmpPoint = new MutableFloatPoint(0f, 0f);
  
  private MutableFloatPoint tmpPoint2 = new MutableFloatPoint(0f, 0f);
  
  private SpriteBatch spriteBatch;
  
  private final Vector2 tmp = new Vector2();
  
  public GLRenderer(final Camera camera) {
    Validate.notNull(camera);
    this.camera = camera;
    ImmediateModeRenderer20 _immediateModeRenderer20 = new ImmediateModeRenderer20(50000, false, true, 0);
    this.immediateModeRenderer = _immediateModeRenderer20;
    SpriteBatch _spriteBatch = new SpriteBatch();
    this.spriteBatch = _spriteBatch;
    ComponentLookup _instance = ComponentLookup.getInstance();
    AssetManager _component = _instance.<AssetManager>getComponent(AssetManager.class);
    BitmapFont _smallFont = _component.getSmallFont();
    this.font = _smallFont;
  }
  
  private void addColoredVertex(final Color color, final float x, final float y) {
    this.immediateModeRenderer.color(color);
    this.immediateModeRenderer.vertex(x, y, 0);
  }
  
  private void addColoredVertex(final Color color, final FloatPoint coordinate) {
    this.addColoredVertex(color, coordinate.x, coordinate.y);
  }
  
  @Override
  public void begin() {
    Matrix4 _combined = this.camera.getCombined();
    this.spriteBatch.setProjectionMatrix(_combined);
    this.enableAlphaBlending();
    this.clearBackground();
  }
  
  private void clearBackground() {
    Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }
  
  @Override
  public void drawArrow(final FloatPoint start, final FloatPoint end, final Color color) {
    this.drawColoredLine(start, end, color);
    this.drawArrowTriangle(end, start);
  }
  
  public void drawArrowTriangle(final FloatPoint end, final FloatPoint start) {
    final float xdiff = (end.x - start.x);
    final float ydiff = (end.y - start.y);
    final float xm = (start.x + (xdiff / 2));
    final float ym = (start.y + (ydiff / 2));
    final float xs = (xm + (xdiff * 0.1f));
    final float ys = (ym + (ydiff * 0.1f));
    final float xab1 = (((-ydiff) * 0.1f) + xm);
    final float yab1 = ((xdiff * 0.1f) + ym);
    final float xab2 = ((ydiff * 0.1f) + xm);
    final float yab2 = (((-xdiff) * 0.1f) + ym);
    Matrix4 _combined = this.camera.getCombined();
    this.immediateModeRenderer.begin(_combined, GL20.GL_TRIANGLES);
    this.addColoredVertex(Color.BLACK, xab1, yab1);
    this.addColoredVertex(Color.BLACK, xs, ys);
    this.addColoredVertex(Color.BLACK, xab2, yab2);
    this.immediateModeRenderer.end();
    Matrix4 _combined_1 = this.camera.getCombined();
    this.immediateModeRenderer.begin(_combined_1, GL20.GL_LINE_LOOP);
    this.addColoredVertex(Color.WHITE, xab1, yab1);
    this.addColoredVertex(Color.WHITE, xs, ys);
    this.addColoredVertex(Color.WHITE, xab2, yab2);
    this.immediateModeRenderer.end();
  }
  
  public void drawColoredLine(final FloatPoint start, final FloatPoint end, final Color color) {
    final Color[] colors = new Color[7];
    final float[] x = new float[7];
    final float[] y = new float[7];
    final float vx = ((end.x - start.x) / 5);
    final float vy = ((end.y - start.y) / 5);
    final int animPos = RendererUtils.getAnimationNumber(6, 10);
    final float offx = ((vx * (animPos % 5)) / 5.0f);
    final float offy = ((vy * (animPos % 5)) / 5.0f);
    int i = (-1);
    int _xifexpression = (int) 0;
    if ((animPos >= 5)) {
      _xifexpression = 1;
    } else {
      _xifexpression = 0;
    }
    final int off = _xifexpression;
    Color _cpy = Color.WHITE.cpy();
    final Color white = _cpy.sub(color);
    white.a = 0.5f;
    Color _colorArrow = this.colorArrow(color, white, (i + off));
    colors[(i + 1)] = _colorArrow;
    x[(i + 1)] = start.x;
    y[(i + 1)] = start.y;
    for (i++; (i <= 4); i++) {
      {
        Color _colorArrow_1 = this.colorArrow(color, white, (i + off));
        colors[(i + 1)] = _colorArrow_1;
        x[(i + 1)] = ((start.x + (vx * i)) + offx);
        y[(i + 1)] = ((start.y + (vy * i)) + offy);
      }
    }
    Color _colorArrow_1 = this.colorArrow(color, white, (i + off));
    colors[(i + 1)] = _colorArrow_1;
    x[(i + 1)] = end.x;
    y[(i + 1)] = end.y;
    for (int pos = 0; (pos < 6); pos++) {
      {
        float _get = x[pos];
        float _get_1 = y[pos];
        this.tmpPoint.set(_get, _get_1);
        float _get_2 = x[(pos + 1)];
        float _get_3 = y[(pos + 1)];
        this.tmpPoint2.set(_get_2, _get_3);
        Color _get_4 = colors[pos];
        this.rectLine(this.tmpPoint, this.tmpPoint2, 0.1f, _get_4);
      }
    }
  }
  
  private Color colorArrow(final Color one, final Color two, final int i) {
    Color _xifexpression = null;
    if (((i % 2) == 0)) {
      _xifexpression = one;
    } else {
      _xifexpression = two;
    }
    return _xifexpression;
  }
  
  /**
   * Draws a line using a rotated rectangle, where with one edge is centered at x1, y1 and the opposite edge centered at x2, y2.
   */
  private void rectLine(final MutableFloatPoint s, final MutableFloatPoint e, final float width, final Color color) {
    Matrix4 _combined = this.camera.getCombined();
    this.immediateModeRenderer.begin(_combined, GL20.GL_TRIANGLES);
    Vector2 _set = this.tmp.set((e.y - s.y), (s.x - e.x));
    final Vector2 t = _set.nor();
    final float tx = ((t.x * width) * 0.5f);
    final float ty = ((t.y * width) * 0.5f);
    this.addColoredVertex(color, (s.x + tx), (s.y + ty));
    this.addColoredVertex(color, (s.x - tx), (s.y - ty));
    this.addColoredVertex(color, (e.x + tx), (e.y + ty));
    this.addColoredVertex(color, (e.x - tx), (e.y - ty));
    this.addColoredVertex(color, (e.x + tx), (e.y + ty));
    this.addColoredVertex(color, (s.x - tx), (s.y - ty));
    this.immediateModeRenderer.end();
  }
  
  @Override
  public void drawUnCircle(final FloatPoint center, final float radius, final Color color) {
    boolean maxRadius = true;
    final int step = (360 / 18);
    {
      int angle = 0;
      boolean _while = (angle < 360);
      while (_while) {
        {
          float _xifexpression = (float) 0;
          if (maxRadius) {
            _xifexpression = 1.0f;
          } else {
            _xifexpression = 0.8f;
          }
          float _multiply = (radius * _xifexpression);
          this.calcCirclePoint(this.tmpPoint, angle, center, _multiply);
          float _xifexpression_1 = (float) 0;
          if ((!maxRadius)) {
            _xifexpression_1 = 1.0f;
          } else {
            _xifexpression_1 = 0.8f;
          }
          float _multiply_1 = (radius * _xifexpression_1);
          this.calcCirclePoint(this.tmpPoint2, (angle + step), center, _multiply_1);
          this.rectLine(this.tmpPoint, this.tmpPoint2, 0.1f, color);
          maxRadius = (!maxRadius);
        }
        int _angle = angle;
        angle = (_angle + step);
        _while = (angle < 360);
      }
    }
  }
  
  @Override
  public void drawCircle(final FloatPoint center, final float radius, final Color color, final boolean bold) {
    if (bold) {
      this.drawBoldCircle(center, radius, color);
    } else {
      this.drawFineCircle(center, radius, color);
    }
  }
  
  public void drawBoldCircle(final FloatPoint center, final float radius, final Color color) {
    final int step = (360 / 18);
    {
      int angle = 0;
      boolean _while = (angle < 360);
      while (_while) {
        {
          this.calcCirclePoint(this.tmpPoint, angle, center, radius);
          this.calcCirclePoint(this.tmpPoint2, (angle + step), center, radius);
          this.rectLine(this.tmpPoint, this.tmpPoint2, 0.1f, color);
        }
        int _angle = angle;
        angle = (_angle + step);
        _while = (angle < 360);
      }
    }
  }
  
  private void drawFineCircle(final FloatPoint center, final float radius, final Color color) {
    Matrix4 _combined = this.camera.getCombined();
    this.immediateModeRenderer.begin(_combined, GL20.GL_LINE_LOOP);
    this.addCircleVertices(center, radius, color, false);
    this.immediateModeRenderer.end();
  }
  
  private void addCircleVertices(final FloatPoint center, final float radius, final Color color, final boolean addFirstTwice) {
    final int step = (360 / 18);
    {
      int angle = 0;
      int _xifexpression = (int) 0;
      if (addFirstTwice) {
        _xifexpression = 1;
      } else {
        _xifexpression = 0;
      }
      int _plus = (360 + _xifexpression);
      boolean _lessThan = (angle < _plus);
      boolean _while = _lessThan;
      while (_while) {
        {
          this.calcCirclePoint(this.tmpPoint, angle, center, radius);
          this.addColoredVertex(color, this.tmpPoint.x, this.tmpPoint.y);
        }
        int _angle = angle;
        angle = (_angle + step);
        int _xifexpression_1 = (int) 0;
        if (addFirstTwice) {
          _xifexpression_1 = 1;
        } else {
          _xifexpression_1 = 0;
        }
        int _plus_1 = (360 + _xifexpression_1);
        boolean _lessThan_1 = (angle < _plus_1);
        _while = _lessThan_1;
      }
    }
  }
  
  private void calcCirclePoint(final MutableFloatPoint returnPoint, final float angle, final FloatPoint center, final float radius) {
    final double radiansAngle = Math.toRadians(angle);
    double _cos = Math.cos(radiansAngle);
    double _multiply = (radius * _cos);
    double _plus = (_multiply + center.x);
    returnPoint.x = ((float) _plus);
    double _sin = Math.sin(radiansAngle);
    double _multiply_1 = (radius * _sin);
    double _plus_1 = (_multiply_1 + center.y);
    returnPoint.y = ((float) _plus_1);
  }
  
  @Override
  public void drawFilledCircle(final FloatPoint center, final float radius, final Color color, final int segments) {
    Matrix4 _combined = this.camera.getCombined();
    this.immediateModeRenderer.begin(_combined, GL20.GL_TRIANGLE_FAN);
    this.addColoredVertex(color, center);
    this.addCircleVertices(center, radius, color, true);
    this.immediateModeRenderer.end();
  }
  
  @Override
  public void drawFilledPoly(final FloatPoint center, final FloatPoint[] corners, final Color color) {
    Matrix4 _combined = this.camera.getCombined();
    this.immediateModeRenderer.begin(_combined, GL20.GL_TRIANGLE_FAN);
    this.addColoredVertex(color, center);
    for (final FloatPoint corner : corners) {
      this.addColoredVertex(color, corner);
    }
    FloatPoint _get = corners[0];
    this.addColoredVertex(color, _get);
    this.immediateModeRenderer.end();
  }
  
  @Override
  public void drawFilledPoly(final FloatPoint center, final FloatPoint[] corners, final Color centerColor, final Color[] colors) {
    Matrix4 _combined = this.camera.getCombined();
    this.immediateModeRenderer.begin(_combined, GL20.GL_TRIANGLE_FAN);
    this.addColoredVertex(centerColor, center);
    for (int i = 0; (i < corners.length); i++) {
      Color _get = colors[i];
      FloatPoint _get_1 = corners[i];
      this.addColoredVertex(_get, _get_1);
    }
    Color _get = colors[0];
    FloatPoint _get_1 = corners[0];
    this.addColoredVertex(_get, _get_1);
    this.immediateModeRenderer.end();
  }
  
  @Override
  public void drawPoly(final FloatPoint[] corners, final Color color) {
    Matrix4 _combined = this.camera.getCombined();
    this.immediateModeRenderer.begin(_combined, GL20.GL_LINE_LOOP);
    for (final FloatPoint corner : corners) {
      this.addColoredVertex(color, corner);
    }
    this.immediateModeRenderer.end();
  }
  
  @Override
  public void drawLines(final FloatPoint[] corners, final Color[] colors) {
    Matrix4 _combined = this.camera.getCombined();
    this.immediateModeRenderer.begin(_combined, GL20.GL_LINE_STRIP);
    for (int i = 0; (i < corners.length); i++) {
      Color _get = colors[i];
      FloatPoint _get_1 = corners[i];
      this.addColoredVertex(_get, _get_1);
    }
    this.immediateModeRenderer.end();
  }
  
  @Override
  public void drawLine(final FloatPoint start, final FloatPoint end, final Color color) {
    Matrix4 _combined = this.camera.getCombined();
    this.immediateModeRenderer.begin(_combined, GL20.GL_LINE_STRIP);
    this.addColoredVertex(color, start);
    this.addColoredVertex(color, end);
    this.immediateModeRenderer.end();
  }
  
  @Override
  public void drawText(final String text, final FloatPoint base, final Color color) {
    this.spriteBatch.begin();
    this.font.setColor(color);
    BitmapFont.BitmapFontData _data = this.font.getData();
    _data.setScale(0.02f);
    this.font.setUseIntegerPositions(false);
    this.font.draw(this.spriteBatch, text, base.x, base.y);
    this.spriteBatch.end();
  }
  
  private void enableAlphaBlending() {
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
  }
  
  @Override
  public void end() {
  }
  
  @Override
  public void drawBox(final FloatPoint start, final FloatPoint end, final Color color) {
    Matrix4 _combined = this.camera.getCombined();
    this.immediateModeRenderer.begin(_combined, GL20.GL_TRIANGLE_STRIP);
    this.addColoredVertex(color, start);
    this.addColoredVertex(color, start.x, end.y);
    this.addColoredVertex(color, end.x, start.y);
    this.addColoredVertex(color, end);
    this.immediateModeRenderer.end();
  }
  
  public ImmediateModeRenderer20 dispose() {
    ImmediateModeRenderer20 _xblockexpression = null;
    {
      this.immediateModeRenderer.dispose();
      _xblockexpression = this.immediateModeRenderer = null;
    }
    return _xblockexpression;
  }
}
