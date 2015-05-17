package net.npg.abattle.server.logic;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.utils.FieldLoop;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.server.logic.Logic;
import net.npg.abattle.server.logic.LogicHelper;
import net.npg.abattle.server.logic.SimpleMoveDistribution;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

@SuppressWarnings("all")
public class SimpleDistributeArmies implements Logic {
  private final GameConfiguration configuration;
  
  private final ServerBoard board;
  
  public SimpleDistributeArmies() {
    this.configuration = null;
    this.board = null;
  }
  
  private SimpleDistributeArmies(final ServerBoard board, final GameConfiguration configuration) {
    Validate.notNulls(board, configuration);
    this.board = board;
    this.configuration = configuration;
  }
  
  @Override
  public void run() {
    Validate.notNulls(this.board, this.configuration);
    int _xSize = this.board.getXSize();
    int _ySize = this.board.getYSize();
    final Procedure2<Integer, Integer> _function = new Procedure2<Integer, Integer>() {
      @Override
      public void apply(final Integer x, final Integer y) {
        ServerCell _cellAt = SimpleDistributeArmies.this.board.getCellAt((x).intValue(), (y).intValue());
        _cellAt.setBattle(false);
      }
    };
    FieldLoop.visitAllFields(_xSize, _ySize, _function);
    int _xSize_1 = this.board.getXSize();
    int _ySize_1 = this.board.getYSize();
    final Procedure2<Integer, Integer> _function_1 = new Procedure2<Integer, Integer>() {
      @Override
      public void apply(final Integer x, final Integer y) {
        SimpleDistributeArmies.this.visitCell((x).intValue(), (y).intValue());
      }
    };
    FieldLoop.visitAllFields(_xSize_1, _ySize_1, _function_1);
  }
  
  private void visitCell(final int x, final int y) {
    final ServerCell cell = this.board.getCellAt(x, y);
    this.doCellMarch(cell);
    GameConfigurationData _configuration = this.configuration.getConfiguration();
    LogicHelper.updateCellGrowth(cell, _configuration);
  }
  
  private void doCellMarch(final ServerCell cell) {
    final List<SimpleMoveDistribution> maxMoveToList = this.calcMaxToMoveArmies(cell);
    this.moveArmies(cell, maxMoveToList);
  }
  
  private void moveArmies(final ServerCell cell, final List<SimpleMoveDistribution> maxMoveToList) {
    boolean armyMoved = false;
    do {
      {
        armyMoved = false;
        for (final SimpleMoveDistribution maxMoveTo : maxMoveToList) {
          {
            boolean _or = false;
            int _strength = cell.getStrength();
            boolean _equals = (_strength == 0);
            if (_equals) {
              _or = true;
            } else {
              _or = (maxMoveTo.maxMoveArmiesTo == 0);
            }
            if(_or) break;;
            if (maxMoveTo.hasFight) {
              boolean _doFight = this.doFight(cell, maxMoveTo);
              armyMoved = _doFight;
            } else {
              boolean _doMarch = this.doMarch(cell, maxMoveTo);
              armyMoved = _doMarch;
            }
            if (armyMoved) {
              Optional<Player> _owner = cell.<Player>getOwner();
              Player _get = _owner.get();
              this.changeOwner(maxMoveTo, _get);
            }
          }
        }
      }
    } while(armyMoved);
  }
  
  private boolean doMarch(final ServerCell cell, final SimpleMoveDistribution maxMoveTo) {
    boolean _xifexpression = false;
    boolean _and = false;
    boolean _and_1 = false;
    int _strength = cell.getStrength();
    boolean _greaterThan = (_strength > 0);
    if (!_greaterThan) {
      _and_1 = false;
    } else {
      _and_1 = (maxMoveTo.maxMoveArmiesTo > 0);
    }
    if (!_and_1) {
      _and = false;
    } else {
      int _strength_1 = maxMoveTo.destinationCell.getStrength();
      GameConfigurationData _configuration = this.configuration.getConfiguration();
      int _maxCellStrength = _configuration.getMaxCellStrength();
      boolean _lessThan = (_strength_1 < _maxCellStrength);
      _and = _lessThan;
    }
    if (_and) {
      boolean _xblockexpression = false;
      {
        this.doSingleMarch(cell, maxMoveTo);
        _xblockexpression = true;
      }
      _xifexpression = _xblockexpression;
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }
  
  private boolean doFight(final ServerCell cell, final SimpleMoveDistribution maxMoveTo) {
    boolean _xifexpression = false;
    boolean _and = false;
    int _strength = cell.getStrength();
    boolean _greaterThan = (_strength > 0);
    if (!_greaterThan) {
      _and = false;
    } else {
      _and = (maxMoveTo.maxMoveArmiesTo > 0);
    }
    if (_and) {
      boolean _xblockexpression = false;
      {
        int _strength_1 = maxMoveTo.destinationCell.getStrength();
        boolean _equals = (_strength_1 == 0);
        if (_equals) {
          this.doSingleMarch(cell, maxMoveTo);
          maxMoveTo.hasFight = false;
          this.removeLinks(maxMoveTo.destinationCell);
          maxMoveTo.destinationCell.setBattle(false);
          Player _xifexpression_1 = null;
          Optional<Player> _owner = cell.<Player>getOwner();
          boolean _isPresent = _owner.isPresent();
          if (_isPresent) {
            Optional<Player> _owner_1 = cell.<Player>getOwner();
            _xifexpression_1 = _owner_1.get();
          } else {
            _xifexpression_1 = null;
          }
          maxMoveTo.destinationCell.setOwner(_xifexpression_1);
        } else {
          this.doSingleFight(cell, maxMoveTo);
        }
        _xblockexpression = true;
      }
      _xifexpression = _xblockexpression;
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }
  
  private int doSingleMarch(final ServerCell cell, final SimpleMoveDistribution maxMoveTo) {
    int _xblockexpression = (int) 0;
    {
      cell.addStrength((-1));
      maxMoveTo.destinationCell.addStrength(1);
      _xblockexpression = maxMoveTo.maxMoveArmiesTo--;
    }
    return _xblockexpression;
  }
  
  private int doSingleFight(final ServerCell cell, final SimpleMoveDistribution maxMoveTo) {
    int _xblockexpression = (int) 0;
    {
      cell.addStrength((-1));
      maxMoveTo.destinationCell.addStrength((-1));
      _xblockexpression = maxMoveTo.maxMoveArmiesTo--;
    }
    return _xblockexpression;
  }
  
  private void changeOwner(final SimpleMoveDistribution maxMoveTo, final Player player) {
    final ServerCell destinationCell = maxMoveTo.destinationCell;
    boolean _and = false;
    int _strength = destinationCell.getStrength();
    boolean _equals = (_strength == 0);
    if (!_equals) {
      _and = false;
    } else {
      _and = maxMoveTo.hasFight;
    }
    if (_and) {
      maxMoveTo.hasFight = false;
      this.removeLinks(destinationCell);
      destinationCell.setOwner(null);
      destinationCell.setBattle(false);
    } else {
      boolean _and_1 = false;
      boolean _and_2 = false;
      if (!(!maxMoveTo.hasFight)) {
        _and_2 = false;
      } else {
        int _strength_1 = destinationCell.getStrength();
        boolean _greaterThan = (_strength_1 > 0);
        _and_2 = _greaterThan;
      }
      if (!_and_2) {
        _and_1 = false;
      } else {
        boolean _isOwner = destinationCell.<Player>isOwner(player);
        boolean _not = (!_isOwner);
        _and_1 = _not;
      }
      if (_and_1) {
        destinationCell.setOwner(player);
        destinationCell.setBattle(false);
      } else {
        boolean _and_3 = false;
        if (!maxMoveTo.hasFight) {
          _and_3 = false;
        } else {
          boolean _isOwner_1 = destinationCell.<Player>isOwner(player);
          boolean _not_1 = (!_isOwner_1);
          _and_3 = _not_1;
        }
        if (_and_3) {
          destinationCell.setBattle(true);
        }
      }
    }
  }
  
  private List<SimpleMoveDistribution> calcMaxToMoveArmies(final ServerCell source) {
    Optional<Player> _owner = source.<Player>getOwner();
    boolean _isPresent = _owner.isPresent();
    boolean _not = (!_isPresent);
    if (_not) {
      return Collections.<SimpleMoveDistribution>emptyList();
    }
    ServerLinks _links = this.board.getLinks();
    Optional<ServerPlayer> _owner_1 = source.<ServerPlayer>getOwner();
    ServerPlayer _get = _owner_1.get();
    final List<ServerCell> directions = _links.getOutgoingLinks(_get, source);
    final ArrayList<SimpleMoveDistribution> maxMoveToList = new ArrayList<SimpleMoveDistribution>(6);
    for (final ServerCell destination : directions) {
      {
        GameConfigurationData _configuration = this.configuration.getConfiguration();
        final double gradient = LogicHelper.calcMaxMovement(source, destination, _configuration);
        boolean _and = false;
        Optional<Player> _owner_2 = destination.<Player>getOwner();
        boolean _notEquals = (!Objects.equal(_owner_2, null));
        if (!_notEquals) {
          _and = false;
        } else {
          Optional<Player> _owner_3 = source.<Player>getOwner();
          Optional<Player> _owner_4 = destination.<Player>getOwner();
          boolean _notEquals_1 = (!Objects.equal(_owner_3, _owner_4));
          _and = _notEquals_1;
        }
        final boolean hasFight = _and;
        GameConfigurationData _configuration_1 = this.configuration.getConfiguration();
        int _maxCellStrength = _configuration_1.getMaxCellStrength();
        int _xifexpression = (int) 0;
        if (hasFight) {
          _xifexpression = 0;
        } else {
          _xifexpression = destination.getStrength();
        }
        final int space = (_maxCellStrength - _xifexpression);
        double _min = Math.min(gradient, space);
        final int maxMoveArmiesTo = ((int) _min);
        if ((maxMoveArmiesTo > 0)) {
          SimpleMoveDistribution _simpleMoveDistribution = new SimpleMoveDistribution(maxMoveArmiesTo, destination, hasFight);
          maxMoveToList.add(_simpleMoveDistribution);
        }
      }
    }
    return maxMoveToList;
  }
  
  private void removeLinks(final ServerCell cell) {
    Optional<Player> _owner = cell.<Player>getOwner();
    boolean _isPresent = _owner.isPresent();
    boolean _not = (!_isPresent);
    if(_not) return;;
    ServerLinks _links = this.board.getLinks();
    Optional<ServerPlayer> _owner_1 = cell.<ServerPlayer>getOwner();
    ServerPlayer _get = _owner_1.get();
    final List<ServerCell> links = _links.getOutgoingLinks(_get, cell);
    final Procedure1<ServerCell> _function = new Procedure1<ServerCell>() {
      @Override
      public void apply(final ServerCell it) {
        ServerLinks _links = SimpleDistributeArmies.this.board.getLinks();
        Optional<ServerPlayer> _owner = cell.<ServerPlayer>getOwner();
        ServerPlayer _get = _owner.get();
        _links.toggleOutgoingLink(cell, it, _get);
      }
    };
    IterableExtensions.<ServerCell>forEach(links, _function);
  }
  
  @Override
  public String getName() {
    return SimpleDistributeArmies.NAME;
  }
  
  @Override
  public Logic getInstance(final ServerBoard board, final GameConfiguration configuration) {
    return new SimpleDistributeArmies(board, configuration);
  }
  
  protected final static String NAME = "distribute.simple";
}
