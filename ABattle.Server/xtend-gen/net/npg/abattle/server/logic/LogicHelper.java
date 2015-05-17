package net.npg.abattle.server.logic;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.server.model.ServerCell;

@SuppressWarnings("all")
public class LogicHelper {
  protected static void updateCellGrowth(final ServerCell cell, final GameConfigurationData configuration) {
    int _xifexpression = (int) 0;
    CellTypes _cellType = cell.getCellType();
    boolean _equals = Objects.equal(_cellType, CellTypes.BASE);
    if (_equals) {
      _xifexpression = configuration.getBaseGrowthPerTick();
    } else {
      _xifexpression = configuration.getTownGrowthPerTick();
    }
    final int structureGrowth = _xifexpression;
    boolean _or = false;
    boolean _hasStructure = cell.hasStructure();
    boolean _not = (!_hasStructure);
    if (_not) {
      _or = true;
    } else {
      Optional<Player> _owner = cell.<Player>getOwner();
      boolean _isPresent = _owner.isPresent();
      boolean _not_1 = (!_isPresent);
      _or = _not_1;
    }
    if(_or) return;;
    final int oldStrength = cell.getStrength();
    int _maxCellStrength = configuration.getMaxCellStrength();
    boolean _greaterEqualsThan = (oldStrength >= _maxCellStrength);
    if(_greaterEqualsThan) return;;
    int _maxCellStrength_1 = configuration.getMaxCellStrength();
    int _minus = (_maxCellStrength_1 - oldStrength);
    final int strengthGrow = Math.min(_minus, structureGrowth);
    cell.addStrength(strengthGrow);
  }
  
  protected static double calcMaxMovement(final ServerCell source, final ServerCell destination, final GameConfigurationData configuration) {
    double _xblockexpression = (double) 0;
    {
      int _height = source.getHeight();
      int _height_1 = destination.getHeight();
      int _minus = (_height - _height_1);
      int _maxCellHeight = configuration.getMaxCellHeight();
      final double gradient = (((double) _minus) / _maxCellHeight);
      int _maxMovement = configuration.getMaxMovement();
      int _terrainInfluence = configuration.getTerrainInfluence();
      double _divide = (_terrainInfluence / 100.0);
      double _multiply = (_divide * gradient);
      double _plus = (0.5 + _multiply);
      final double movement = (_maxMovement * _plus);
      int _strength = source.getStrength();
      double _min = Math.min(movement, _strength);
      _xblockexpression = Math.max(0, _min);
    }
    return _xblockexpression;
  }
}
