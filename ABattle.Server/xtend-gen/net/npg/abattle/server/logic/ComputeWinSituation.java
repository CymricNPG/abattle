package net.npg.abattle.server.logic;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import java.util.Collection;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.utils.FieldLoop;
import net.npg.abattle.common.utils.LongHolder;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

@SuppressWarnings("all")
public class ComputeWinSituation {
  public static void run(final ServerGame game) {
    ComputeWinSituation.cleanPlayerStrength(game);
    final LongHolder totalStrength = new LongHolder();
    Board<ServerPlayer, ServerCell, ServerLinks> _board = game.getBoard();
    int _xSize = _board.getXSize();
    Board<ServerPlayer, ServerCell, ServerLinks> _board_1 = game.getBoard();
    int _ySize = _board_1.getYSize();
    final Procedure2<Integer, Integer> _function = new Procedure2<Integer, Integer>() {
      @Override
      public void apply(final Integer x, final Integer y) {
        Board<ServerPlayer, ServerCell, ServerLinks> _board = game.getBoard();
        ServerCell _cellAt = _board.getCellAt((x).intValue(), (y).intValue());
        ComputeWinSituation.calcStrength(_cellAt, totalStrength);
      }
    };
    FieldLoop.visitAllFields(_xSize, _ySize, _function);
    GameConfiguration _gameConfiguration = game.getGameConfiguration();
    GameConfigurationData _configuration = _gameConfiguration.getConfiguration();
    int _winCondition = _configuration.getWinCondition();
    float _multiply = (((float) totalStrength.value) * _winCondition);
    double _divide = (_multiply / 100.0);
    final int winStrength = ((int) _divide);
    Collection<ServerPlayer> _players = game.getPlayers();
    final Function1<ServerPlayer, Boolean> _function_1 = new Function1<ServerPlayer, Boolean>() {
      @Override
      public Boolean apply(final ServerPlayer it) {
        int _strength = it.getStrength();
        return Boolean.valueOf((_strength >= winStrength));
      }
    };
    final ServerPlayer winPlayer = IterableExtensions.<ServerPlayer>findFirst(_players, _function_1);
    boolean _equals = Objects.equal(winPlayer, null);
    if(_equals) return;;
    game.stopGame();
  }
  
  public static void calcStrength(final ServerCell cell, final LongHolder totalStrength) {
    Optional<Player> _owner = cell.<Player>getOwner();
    boolean _isPresent = _owner.isPresent();
    if(!_isPresent) return;;
    Optional<Player> _owner_1 = cell.<Player>getOwner();
    Player _get = _owner_1.get();
    Optional<Player> _owner_2 = cell.<Player>getOwner();
    Player _get_1 = _owner_2.get();
    int _strength = _get_1.getStrength();
    int _strength_1 = cell.getStrength();
    int _plus = (_strength + _strength_1);
    ((ServerPlayer) _get).setStrength(_plus);
    int _strength_2 = cell.getStrength();
    totalStrength.add(_strength_2);
  }
  
  private static void cleanPlayerStrength(final ServerGame game) {
    Collection<ServerPlayer> _players = game.getPlayers();
    for (final ServerPlayer player : _players) {
      player.setStrength(0);
    }
  }
}
