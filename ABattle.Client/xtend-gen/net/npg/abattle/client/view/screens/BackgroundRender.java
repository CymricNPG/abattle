package net.npg.abattle.client.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import java.util.List;
import net.npg.abattle.client.view.boardscene.RendererUtils;
import net.npg.abattle.client.view.renderer.impl.CameraImpl;
import net.npg.abattle.client.view.renderer.impl.GLRenderer;
import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.utils.FieldLoop;
import net.npg.abattle.common.utils.FloatPoint;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Transformation;
import net.npg.abattle.common.utils.impl.DisposeableImpl;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

@SuppressWarnings("all")
public class BackgroundRender extends DisposeableImpl {
  private final static int HEX_SIZE = 32;
  
  private HexBase hexBase;
  
  private GLRenderer renderer;
  
  public BackgroundRender(final Camera camera) {
    HexBase _hexBase = new HexBase(BackgroundRender.HEX_SIZE);
    this.hexBase = _hexBase;
    final float x = (17.0f * this.hexBase.side);
    final float y = (17.0f * this.hexBase.height);
    final CameraImpl cam = new CameraImpl();
    cam.setNewViewSize(x, y);
    GLRenderer _gLRenderer = new GLRenderer(cam);
    this.renderer = _gLRenderer;
  }
  
  public void drawHexField() {
    int _width = Gdx.graphics.getWidth();
    int _height = Gdx.graphics.getHeight();
    Gdx.gl.glViewport(0, 0, _width, _height);
    final Procedure2<Integer, Integer> _function = new Procedure2<Integer, Integer>() {
      @Override
      public void apply(final Integer x, final Integer y) {
        BackgroundRender.this.renderHex((x).intValue(), (y).intValue());
      }
    };
    FieldLoop.visitAllFields(16, 16, _function);
  }
  
  private void renderHex(final int x, final int y) {
    IntPoint _from = IntPoint.from(x, y);
    final Hex hex = new Hex(_from, this.hexBase);
    FloatPoint[] _computeCorners = hex.computeCorners();
    final Function1<FloatPoint, FloatPoint> _function = new Function1<FloatPoint, FloatPoint>() {
      @Override
      public FloatPoint apply(final FloatPoint c) {
        return Transformation.translate(c, 0, 0);
      }
    };
    List<FloatPoint> _map = ListExtensions.<FloatPoint, FloatPoint>map(((List<FloatPoint>)Conversions.doWrapArray(_computeCorners)), _function);
    final List<FloatPoint> corners = _map.subList(0, 4);
    Color[] _computeColors = this.computeColors(((FloatPoint[])Conversions.unwrapArray(corners, FloatPoint.class)));
    this.renderer.drawLines(((FloatPoint[])Conversions.unwrapArray(corners, FloatPoint.class)), _computeColors);
  }
  
  private Color[] computeColors(final FloatPoint[] corners) {
    final Function1<FloatPoint, Color> _function = new Function1<FloatPoint, Color>() {
      @Override
      public Color apply(final FloatPoint corner) {
        return BackgroundRender.this.computeColor(corner.x, corner.y);
      }
    };
    return ((Color[])Conversions.unwrapArray(ListExtensions.<FloatPoint, Color>map(((List<FloatPoint>)Conversions.doWrapArray(corners)), _function), Color.class));
  }
  
  private Color computeColor(final float x, final float y) {
    Color _xblockexpression = null;
    {
      final int off = RendererUtils.getAnimationNumber(50, 360);
      float _sinDeg = MathUtils.sinDeg((x + off));
      float _cosDeg = MathUtils.cosDeg((y + off));
      float _multiply = (_sinDeg * _cosDeg);
      final double v = (_multiply * 0.8);
      _xblockexpression = new Color(0, 0, ((float) v), 0);
    }
    return _xblockexpression;
  }
  
  @Override
  public void dispose() {
    super.dispose();
    this.renderer.dispose();
  }
}
