package net.npg.abattle.client.view.boardscene;

import java.util.Iterator;
import java.util.List;
import net.npg.abattle.client.view.boardscene.ComplexBarShape;
import net.npg.abattle.client.view.renderer.Renderer;
import net.npg.abattle.common.model.Color;
import net.npg.abattle.common.utils.ColorConvert;
import net.npg.abattle.common.utils.FloatHolder;
import net.npg.abattle.common.utils.FloatPoint;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class DrawComplexBarShape {
  public static void draw(final Renderer renderer, final ComplexBarShape shape) {
    final List<Float> sizes = shape.getSizes();
    final FloatPoint startCoordinate = shape.getStartCoordinate();
    final FloatPoint endCoordinate = shape.getEndCoordinate();
    Iterable<Float> _startCoordinates = DrawComplexBarShape.getStartCoordinates(sizes);
    final Iterator<Float> startCoordinates = _startCoordinates.iterator();
    Iterable<Float> _endCoordinates = DrawComplexBarShape.getEndCoordinates(sizes);
    final Iterator<Float> endCoordinates = _endCoordinates.iterator();
    Iterable<Color> _colors = shape.getColors();
    final Iterator<Color> colors = _colors.iterator();
    final float distance = (endCoordinate.x - startCoordinate.x);
    int _size = sizes.size();
    int _minus = (_size - 1);
    IntegerRange _upTo = new IntegerRange(0, _minus);
    for (final int i : _upTo) {
      {
        Color _next = colors.next();
        final com.badlogic.gdx.graphics.Color color = ColorConvert.convert(_next);
        Float _next_1 = startCoordinates.next();
        float _multiply = (distance * (_next_1).floatValue());
        final float spos = (startCoordinate.x + _multiply);
        Float _next_2 = endCoordinates.next();
        float _multiply_1 = (distance * (_next_2).floatValue());
        final float epos = (startCoordinate.x + _multiply_1);
        final FloatPoint start = new FloatPoint(spos, startCoordinate.y);
        final FloatPoint end = new FloatPoint(epos, endCoordinate.y);
        renderer.drawBox(start, end, color);
      }
    }
  }
  
  private static Iterable<Float> getStartCoordinates(final Iterable<Float> sizes) {
    Iterable<Float> _xblockexpression = null;
    {
      final FloatHolder startPos = new FloatHolder();
      final Function1<Float, Float> _function = new Function1<Float, Float>() {
        @Override
        public Float apply(final Float it) {
          return Float.valueOf(startPos.replace((startPos.value + (it).floatValue())));
        }
      };
      _xblockexpression = IterableExtensions.<Float, Float>map(sizes, _function);
    }
    return _xblockexpression;
  }
  
  private static Iterable<Float> getEndCoordinates(final Iterable<Float> sizes) {
    Iterable<Float> _xblockexpression = null;
    {
      final FloatHolder endPos = new FloatHolder();
      final Function1<Float, Float> _function = new Function1<Float, Float>() {
        @Override
        public Float apply(final Float it) {
          return Float.valueOf(endPos.add((it).floatValue()));
        }
      };
      _xblockexpression = IterableExtensions.<Float, Float>map(sizes, _function);
    }
    return _xblockexpression;
  }
}
