package net.npg.abattle.server.game.impl.terrain;

import java.util.List;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.utils.IntPoint3;
import net.npg.abattle.server.game.impl.terrain.XBattleTerrain;

@SuppressWarnings("all")
public class PeakTerrain extends XBattleTerrain {
  private final GameConfigurationData configuration;
  
  public PeakTerrain() {
    ComponentLookup _instance = ComponentLookup.getInstance();
    ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
    GameConfigurationData _gameConfiguration = _component.getGameConfiguration();
    this.configuration = _gameConfiguration;
  }
  
  @Override
  public double calcHeight(final int x, final int y, final List<IntPoint3> peaks) {
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
        double _multiply = (((double) peak.z) * _minus);
        int _maxCellHeight_3 = this.configuration.getMaxCellHeight();
        double _divide = (_multiply / _maxCellHeight_3);
        double peakHeight = (_divide / 1.5f);
        boolean _and = false;
        double _floor = Math.floor(peakHeight);
        double _floor_1 = Math.floor(height);
        boolean _equals = (_floor == _floor_1);
        if (!_equals) {
          _and = false;
        } else {
          int _maxCellHeight_4 = this.configuration.getMaxCellHeight();
          int _divide_1 = (_maxCellHeight_4 / 2);
          boolean _greaterThan_1 = (peakHeight > _divide_1);
          _and = _greaterThan_1;
        }
        if (_and) {
          double _height = height;
          height = (_height + 2f);
        } else {
          double _max = Math.max(peakHeight, height);
          height = _max;
        }
      }
    }
    return this.uniformHeight(height);
  }
  
  private double uniformHeight(final double h) {
    double _max = Math.max(h, 0.0);
    int _maxCellHeight = this.configuration.getMaxCellHeight();
    int _minus = (_maxCellHeight - 1);
    return Math.min(_max, _minus);
  }
  
  @Override
  public String getName() {
    return "terrain.peak";
  }
}
