package net.npg.abattle.server.logic;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.hex.Directions;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.utils.FieldLoop;
import net.npg.abattle.common.utils.IntHolder;
import net.npg.abattle.server.logic.Logic;
import net.npg.abattle.server.logic.LogicHelper;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

@SuppressWarnings("all")
public class XBattleDistribute implements Logic {
  private final ServerBoard board;
  
  private final Random rand;
  
  private final GameConfigurationData configuration;
  
  public XBattleDistribute() {
    this.configuration = null;
    this.board = null;
    this.rand = null;
  }
  
  public XBattleDistribute(final ServerBoard board, final GameConfiguration configuration) {
    this.board = board;
    GameConfigurationData _configuration = configuration.getConfiguration();
    this.configuration = _configuration;
    long _currentTimeMillis = System.currentTimeMillis();
    Random _random = new Random(_currentTimeMillis);
    this.rand = _random;
  }
  
  @Override
  public void run() {
    int _xSize = this.board.getXSize();
    int _ySize = this.board.getYSize();
    final Procedure2<Integer, Integer> _function = new Procedure2<Integer, Integer>() {
      @Override
      public void apply(final Integer x, final Integer y) {
        ServerCell _cellAt = XBattleDistribute.this.board.getCellAt((x).intValue(), (y).intValue());
        _cellAt.setBattle(false);
      }
    };
    FieldLoop.visitAllFields(_xSize, _ySize, _function);
    final ArrayList<ServerCell> cells = this.shuffleCells();
    this.resetFight(cells);
    for (final ServerCell cell : cells) {
      {
        LogicHelper.updateCellGrowth(cell, this.configuration);
        this.updateCellFights(cell);
        this.updateCellMovement(cell);
      }
    }
  }
  
  public void resetFight(final List<ServerCell> cells) {
    final Procedure1<ServerCell> _function = new Procedure1<ServerCell>() {
      @Override
      public void apply(final ServerCell it) {
        it.setBattle(false);
      }
    };
    IterableExtensions.<ServerCell>forEach(cells, _function);
  }
  
  private void updateCellMovement(final ServerCell cell) {
    int _strength = cell.getStrength();
    boolean _equals = (_strength == 0);
    if(_equals) return;;
    Optional<Player> _owner = cell.<Player>getOwner();
    boolean _isPresent = _owner.isPresent();
    boolean _not = (!_isPresent);
    if(_not) return;;
    final List<ServerCell> destinationCells = this.findFriendlyCells(cell);
    boolean _isEmpty = destinationCells.isEmpty();
    if(_isEmpty) return;;
    final Procedure1<ServerCell> _function = new Procedure1<ServerCell>() {
      @Override
      public void apply(final ServerCell it) {
        XBattleDistribute.this.moveArmies(cell, it);
      }
    };
    IterableExtensions.<ServerCell>forEach(destinationCells, _function);
  }
  
  private void moveArmies(final ServerCell source, final ServerCell destination) {
    final double maxMoving = LogicHelper.calcMaxMovement(source, destination, this.configuration);
    int _maxCellStrength = this.configuration.getMaxCellStrength();
    int _strength = destination.getStrength();
    final int destinationSpace = (_maxCellStrength - _strength);
    int _strength_1 = source.getStrength();
    double _min = Math.min(maxMoving, _strength_1);
    double _min_1 = Math.min(_min, destinationSpace);
    final int reallyMoving = ((int) _min_1);
    if((reallyMoving <= 0)) return;;
    int _strength_2 = destination.getStrength();
    int _plus = (_strength_2 + reallyMoving);
    destination.setStrength(_plus);
    Optional<Player> _owner = source.<Player>getOwner();
    Player _get = _owner.get();
    destination.setOwner(_get);
    int _strength_3 = source.getStrength();
    int _minus = (_strength_3 - reallyMoving);
    source.setStrength(_minus);
    destination.setBattle(false);
  }
  
  private List<ServerCell> findFriendlyCells(final ServerCell cell) {
    List<ServerCell> _xblockexpression = null;
    {
      ServerLinks _links = this.board.getLinks();
      Optional<ServerPlayer> _owner = cell.<ServerPlayer>getOwner();
      ServerPlayer _get = _owner.get();
      List<ServerCell> _outgoingLinks = _links.getOutgoingLinks(_get, cell);
      final Function1<ServerCell, Boolean> _function = new Function1<ServerCell, Boolean>() {
        @Override
        public Boolean apply(final ServerCell it) {
          boolean _or = false;
          boolean _and = false;
          Optional<Player> _owner = it.<Player>getOwner();
          boolean _isPresent = _owner.isPresent();
          if (!_isPresent) {
            _and = false;
          } else {
            Optional<Player> _owner_1 = it.<Player>getOwner();
            Player _get = _owner_1.get();
            Optional<Player> _owner_2 = cell.<Player>getOwner();
            Player _get_1 = _owner_2.get();
            boolean _equals = Objects.equal(_get, _get_1);
            _and = _equals;
          }
          if (_and) {
            _or = true;
          } else {
            Optional<Player> _owner_3 = it.<Player>getOwner();
            boolean _isPresent_1 = _owner_3.isPresent();
            boolean _not = (!_isPresent_1);
            _or = _not;
          }
          return Boolean.valueOf(_or);
        }
      };
      Iterable<ServerCell> _filter = IterableExtensions.<ServerCell>filter(_outgoingLinks, _function);
      final List<ServerCell> cells = IterableExtensions.<ServerCell>toList(_filter);
      Collections.shuffle(cells);
      _xblockexpression = cells;
    }
    return _xblockexpression;
  }
  
  private void updateCellFights(final ServerCell cell) {
    int _strength = cell.getStrength();
    boolean _equals = (_strength == 0);
    if(_equals) return;;
    Optional<Player> _owner = cell.<Player>getOwner();
    boolean _isPresent = _owner.isPresent();
    boolean _not = (!_isPresent);
    if(_not) return;;
    final List<ServerCell> linkedCells = this.findEnemyLinkedCells(cell);
    boolean _isEmpty = linkedCells.isEmpty();
    if(_isEmpty) return;;
    final Procedure1<ServerCell> _function = new Procedure1<ServerCell>() {
      @Override
      public void apply(final ServerCell it) {
        XBattleDistribute.this.doFight(cell, it);
      }
    };
    IterableExtensions.<ServerCell>forEach(linkedCells, _function);
  }
  
  private void doFight(final ServerCell source, final ServerCell destination) {
    int _strength = source.getStrength();
    boolean _equals = (_strength == 0);
    if(_equals) return;;
    final double damageFactor = this.calcDamageFactor(source, destination);
    double _calcMaxMovement = LogicHelper.calcMaxMovement(source, destination, this.configuration);
    final int attackingArmies = ((int) _calcMaxMovement);
    int _strength_1 = source.getStrength();
    int _minus = (_strength_1 - attackingArmies);
    source.setStrength(_minus);
    int _strength_2 = destination.getStrength();
    final double newDestinationStrength = (_strength_2 - (((double) attackingArmies) * damageFactor));
    if ((newDestinationStrength < 1.0)) {
      int _strength_3 = destination.getStrength();
      int _minus_1 = (attackingArmies - _strength_3);
      int _abs = Math.abs(_minus_1);
      final double newOwnerStrength = Math.max(1.0, _abs);
      destination.setBattle(false);
      this.removeLinks(destination);
      Optional<ServerPlayer> _owner = source.<ServerPlayer>getOwner();
      ServerPlayer _get = _owner.get();
      this.swapOwner(_get, destination, newOwnerStrength);
    } else {
      destination.setStrength(((int) newDestinationStrength));
      destination.setBattle(true);
    }
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
        ServerLinks _links = XBattleDistribute.this.board.getLinks();
        Optional<ServerPlayer> _owner = cell.<ServerPlayer>getOwner();
        ServerPlayer _get = _owner.get();
        _links.toggleOutgoingLink(cell, it, _get);
      }
    };
    IterableExtensions.<ServerCell>forEach(links, _function);
  }
  
  private void swapOwner(final ServerPlayer newOwner, final ServerCell destination, final double newStrength) {
    destination.setOwner(newOwner);
    destination.setStrength(((int) newStrength));
  }
  
  private List<ServerCell> findEnemyLinkedCells(final ServerCell cell) {
    List<ServerCell> _xblockexpression = null;
    {
      ServerLinks _links = this.board.getLinks();
      Optional<ServerPlayer> _owner = cell.<ServerPlayer>getOwner();
      ServerPlayer _get = _owner.get();
      List<ServerCell> _outgoingLinks = _links.getOutgoingLinks(_get, cell);
      final Function1<ServerCell, Boolean> _function = new Function1<ServerCell, Boolean>() {
        @Override
        public Boolean apply(final ServerCell it) {
          boolean _and = false;
          Optional<Player> _owner = it.<Player>getOwner();
          boolean _isPresent = _owner.isPresent();
          if (!_isPresent) {
            _and = false;
          } else {
            Optional<Player> _owner_1 = it.<Player>getOwner();
            Player _get = _owner_1.get();
            Optional<Player> _owner_2 = cell.<Player>getOwner();
            Player _get_1 = _owner_2.get();
            boolean _notEquals = (!Objects.equal(_get, _get_1));
            _and = _notEquals;
          }
          return Boolean.valueOf(_and);
        }
      };
      Iterable<ServerCell> _filter = IterableExtensions.<ServerCell>filter(_outgoingLinks, _function);
      final List<ServerCell> cells = IterableExtensions.<ServerCell>toList(_filter);
      Collections.shuffle(cells);
      _xblockexpression = cells;
    }
    return _xblockexpression;
  }
  
  public double calcDamageFactor(final ServerCell source, final ServerCell destination) {
    double _xblockexpression = (double) 0;
    {
      final IntHolder strength = new IntHolder(0);
      Optional<ServerPlayer> _owner = source.<ServerPlayer>getOwner();
      ServerPlayer _get = _owner.get();
      Iterable<ServerCell> _incomingLinks = this.getIncomingLinks(_get, destination);
      final Procedure1<ServerCell> _function = new Procedure1<ServerCell>() {
        @Override
        public void apply(final ServerCell it) {
          int _value = strength.value;
          int _strength = it.getStrength();
          strength.value = (_value + _strength);
        }
      };
      IterableExtensions.<ServerCell>forEach(_incomingLinks, _function);
      _xblockexpression = ((((double) strength.value) / ((double) destination.getStrength())) * 0.5f);
    }
    return _xblockexpression;
  }
  
  public Iterable<ServerCell> getIncomingLinks(final ServerPlayer player, final ServerCell destination) {
    final Function1<Directions, ServerCell> _function = new Function1<Directions, ServerCell>() {
      @Override
      public ServerCell apply(final Directions it) {
        return XBattleDistribute.this.board.getAdjacentCell(destination, it);
      }
    };
    List<ServerCell> _map = ListExtensions.<Directions, ServerCell>map(((List<Directions>)Conversions.doWrapArray(Directions.cachedValues)), _function);
    final Function1<ServerCell, Boolean> _function_1 = new Function1<ServerCell, Boolean>() {
      @Override
      public Boolean apply(final ServerCell it) {
        ServerLinks _links = XBattleDistribute.this.board.getLinks();
        return Boolean.valueOf(_links.hasLink(it, destination, player));
      }
    };
    return IterableExtensions.<ServerCell>filter(_map, _function_1);
  }
  
  private ArrayList<ServerCell> shuffleCells() {
    ArrayList<ServerCell> _xblockexpression = null;
    {
      final ArrayList<ServerCell> cells = CollectionLiterals.<ServerCell>newArrayList();
      int _xSize = this.board.getXSize();
      int _ySize = this.board.getYSize();
      final Procedure2<Integer, Integer> _function = new Procedure2<Integer, Integer>() {
        @Override
        public void apply(final Integer x, final Integer y) {
          ServerCell _cellAt = XBattleDistribute.this.board.getCellAt((x).intValue(), (y).intValue());
          cells.add(_cellAt);
        }
      };
      FieldLoop.visitAllFields(_xSize, _ySize, _function);
      for (int i = 0; (i < cells.size()); i++) {
        {
          int _size = cells.size();
          final int swapPos = this.rand.nextInt(_size);
          final ServerCell old = cells.get(i);
          ServerCell _get = cells.get(swapPos);
          cells.set(i, _get);
          cells.set(swapPos, old);
        }
      }
      _xblockexpression = cells;
    }
    return _xblockexpression;
  }
  
  protected final static String NAME = "distribute.random";
  
  @Override
  public String getName() {
    return XBattleDistribute.NAME;
  }
  
  @Override
  public Logic getInstance(final ServerBoard board, final GameConfiguration configuration) {
    return new XBattleDistribute(board, configuration);
  }
}
