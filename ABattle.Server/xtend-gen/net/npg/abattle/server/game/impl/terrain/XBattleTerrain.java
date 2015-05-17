package net.npg.abattle.server.game.impl.terrain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.utils.FieldLoop;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.IntPoint3;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.server.ServerConstants;
import net.npg.abattle.server.game.TerrainCreator;
import net.npg.abattle.server.game.impl.terrain.BoardHelper;
import net.npg.abattle.server.model.impl.ServerBoardImpl;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

@SuppressWarnings("all")
public class XBattleTerrain implements TerrainCreator {
  public final static String NAME = "terrain.xbattle";
  
  private final Random rand = new Random(System.currentTimeMillis());
  
  private final GameConfigurationData configuration;
  
  public XBattleTerrain() {
    ComponentLookup _instance = ComponentLookup.getInstance();
    ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
    GameConfigurationData _gameConfiguration = _component.getGameConfiguration();
    this.configuration = _gameConfiguration;
  }
  
  @Override
  public void createBoard(final ServerBoardImpl board, final IntPoint size, final CheckModelElement checker) {
    Validate.notNull(size);
    Validate.notNulls(checker);
    Validate.exclusiveBetween(ServerConstants.MIN_XSIZE, ServerConstants.MAX_XSIZE, size.x);
    Validate.exclusiveBetween(ServerConstants.MIN_YSIZE, ServerConstants.MAX_YSIZE, size.y);
    final List<IntPoint3> peaks = this.calculatePeaks(size);
    final Double[][] heightField = this.calculateHeights(size, peaks);
    final Function2<Integer, Integer, Integer> _function = new Function2<Integer, Integer, Integer>() {
      @Override
      public Integer apply(final Integer x, final Integer y) {
        Double[] _get = heightField[(x).intValue()];
        Double _get_1 = _get[(y).intValue()];
        return Integer.valueOf(_get_1.intValue());
      }
    };
    BoardHelper.fillBoard(board, size, checker, _function);
  }
  
  public Double[][] calculateHeights(final IntPoint size, final List<IntPoint3> peaks) {
    final Double[][] heights = new Double[size.x][size.y];
    final Procedure2<Integer, Integer> _function = new Procedure2<Integer, Integer>() {
      @Override
      public void apply(final Integer x, final Integer y) {
        Double[] _get = heights[(x).intValue()];
        double _calcHeight = XBattleTerrain.this.calcHeight((x).intValue(), (y).intValue(), peaks);
        _get[(y).intValue()] = Double.valueOf(_calcHeight);
      }
    };
    FieldLoop.visitAllFields(size, _function);
    return heights;
  }
  
  public double calcHeight(final int x, final int y, final List<IntPoint3> peaks) {
    int count = 0;
    double height = 0.0;
    for (final IntPoint3 peak : peaks) {
      {
        double _pow = Math.pow((peak.x - x), 2);
        double _pow_1 = Math.pow((peak.y - y), 2);
        double _plus = (_pow + _pow_1);
        final double distance = Math.sqrt(_plus);
        if ((distance < 0.1f)) {
          int _maxCellHeight = this.configuration.getMaxCellHeight();
          return (_maxCellHeight - 1);
        }
        int _maxCellHeight_1 = this.configuration.getMaxCellHeight();
        boolean _greaterThan = (distance > _maxCellHeight_1);
        if(_greaterThan) continue;;
        int _maxCellHeight_2 = this.configuration.getMaxCellHeight();
        double _minus = (_maxCellHeight_2 - distance);
        double _plus_1 = (_minus + 1);
        double _multiply = (((double) peak.z) * _plus_1);
        int _maxCellHeight_3 = this.configuration.getMaxCellHeight();
        double peakHeight = (_multiply / _maxCellHeight_3);
        double _height = height;
        height = (_height + peakHeight);
        count++;
      }
    }
    return this.uniformHeight((height / count));
  }
  
  private double uniformHeight(final double h) {
    double _max = Math.max(h, 0.0);
    int _maxCellHeight = this.configuration.getMaxCellHeight();
    int _minus = (_maxCellHeight - 1);
    return Math.min(_max, _minus);
  }
  
  private List<IntPoint3> calculatePeaks(final IntPoint size) {
    ArrayList<IntPoint3> _xblockexpression = null;
    {
      int _peakCount = this.configuration.getPeakCount();
      int _multiply = (_peakCount * size.x);
      int _multiply_1 = (_multiply * size.y);
      int _divide = (_multiply_1 / 100);
      final int count = Math.min(_divide, 100);
      if ((count == 0)) {
        return Collections.<IntPoint3>emptyList();
      }
      final ArrayList<IntPoint3> peaks = CollectionLiterals.<IntPoint3>newArrayList();
      for (int i = 0; (i < count); i++) {
        int _nextInt = this.rand.nextInt(size.x);
        int _nextInt_1 = this.rand.nextInt(size.y);
        int _maxCellHeight = this.configuration.getMaxCellHeight();
        IntPoint3 _intPoint3 = new IntPoint3(_nextInt, _nextInt_1, _maxCellHeight);
        peaks.add(_intPoint3);
      }
      _xblockexpression = peaks;
    }
    return _xblockexpression;
  }
  
  @Override
  public String getName() {
    return XBattleTerrain.NAME;
  }
}
