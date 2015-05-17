package net.npg.abattle.client.view.boardscene;

import java.util.Collection;
import java.util.List;
import net.npg.abattle.client.view.boardscene.SceneRenderer;
import net.npg.abattle.client.view.boardscene.Shape;
import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.Color;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientLinks;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.utils.FloatPoint;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.LongHolder;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class ComplexBarShape extends Shape {
  private ClientGame game;
  
  private Hex startHex;
  
  private Hex endHex;
  
  public ComplexBarShape(final ClientGame game, final HexBase hexBase) {
    super();
    this.game = game;
    Board<ClientPlayer, ClientCell, ClientLinks> _board = game.getBoard();
    int _ySize = _board.getYSize();
    int _minus = (_ySize - 1);
    IntPoint _from = IntPoint.from(1, _minus);
    Hex _hex = new Hex(_from, hexBase);
    this.startHex = _hex;
    Board<ClientPlayer, ClientCell, ClientLinks> _board_1 = game.getBoard();
    int _xSize = _board_1.getXSize();
    int _minus_1 = (_xSize - 2);
    Board<ClientPlayer, ClientCell, ClientLinks> _board_2 = game.getBoard();
    int _ySize_1 = _board_2.getYSize();
    int _minus_2 = (_ySize_1 - 1);
    IntPoint _from_1 = IntPoint.from(_minus_1, _minus_2);
    Hex _hex_1 = new Hex(_from_1, hexBase);
    this.endHex = _hex_1;
  }
  
  @Override
  public Hex getHex() {
    return this.startHex;
  }
  
  public FloatPoint getStartCoordinate() {
    float _centerX = this.startHex.getCenterX();
    float _bottom = this.startHex.getBottom();
    double _plus = (_bottom + 0.3);
    return new FloatPoint(_centerX, _plus);
  }
  
  public FloatPoint getEndCoordinate() {
    float _centerX = this.endHex.getCenterX();
    float _bottom = this.startHex.getBottom();
    double _plus = (_bottom + 0.2);
    return new FloatPoint(_centerX, _plus);
  }
  
  @Override
  public boolean isDrawable() {
    return true;
  }
  
  @Override
  public void accept(final SceneRenderer visitor) {
    visitor.visit(this);
  }
  
  public List<Float> getSizes() {
    List<Float> _xblockexpression = null;
    {
      final LongHolder maxStrength = new LongHolder();
      Collection<ClientPlayer> _players = this.game.getPlayers();
      final Procedure1<ClientPlayer> _function = new Procedure1<ClientPlayer>() {
        @Override
        public void apply(final ClientPlayer it) {
          int _strength = it.getStrength();
          maxStrength.add(_strength);
        }
      };
      IterableExtensions.<ClientPlayer>forEach(_players, _function);
      if ((maxStrength.value == 0)) {
        maxStrength.value = 1L;
      }
      Collection<ClientPlayer> _players_1 = this.game.getPlayers();
      final Function1<ClientPlayer, Float> _function_1 = new Function1<ClientPlayer, Float>() {
        @Override
        public Float apply(final ClientPlayer it) {
          int _strength = it.getStrength();
          float _float = maxStrength.toFloat();
          return Float.valueOf((((float) _strength) / _float));
        }
      };
      Iterable<Float> _map = IterableExtensions.<ClientPlayer, Float>map(_players_1, _function_1);
      _xblockexpression = IterableExtensions.<Float>toList(_map);
    }
    return _xblockexpression;
  }
  
  public Iterable<Color> getColors() {
    Collection<ClientPlayer> _players = this.game.getPlayers();
    final Function1<ClientPlayer, Color> _function = new Function1<ClientPlayer, Color>() {
      @Override
      public Color apply(final ClientPlayer it) {
        return it.getColor();
      }
    };
    return IterableExtensions.<ClientPlayer, Color>map(_players, _function);
  }
}
