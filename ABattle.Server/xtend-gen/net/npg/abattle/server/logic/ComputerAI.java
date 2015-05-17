package net.npg.abattle.server.logic;

import com.google.common.base.Objects;
import java.util.Collection;
import java.util.Random;
import net.npg.abattle.common.hex.Directions;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ComputerAI implements Runnable {
  private ServerGame game;
  
  private ServerBoard board;
  
  private static Random rand = new Random(12L);
  
  public ComputerAI(final ServerGame game) {
    Validate.notNulls(game);
    this.game = game;
    Board<ServerPlayer, ServerCell, ServerLinks> _board = game.getBoard();
    this.board = ((ServerBoard) _board);
  }
  
  @Override
  public void run() {
    Collection<ServerPlayer> _players = this.game.getPlayers();
    final Function1<ServerPlayer, Boolean> _function = new Function1<ServerPlayer, Boolean>() {
      @Override
      public Boolean apply(final ServerPlayer it) {
        return Boolean.valueOf(it.isComputer());
      }
    };
    Iterable<ServerPlayer> _filter = IterableExtensions.<ServerPlayer>filter(_players, _function);
    for (final ServerPlayer player : _filter) {
      {
        IntPoint _randomCoordinate = this.randomCoordinate();
        final ServerCell cell = this.board.getCellAt(_randomCoordinate);
        boolean _isOwner = cell.<ServerPlayer>isOwner(player);
        if (_isOwner) {
          this.makeLink(cell, player);
        }
      }
    }
  }
  
  public void makeLink(final ServerCell cell, final ServerPlayer player) {
    for (final Directions direction : Directions.cachedValues) {
      {
        final ServerCell endCell = this.board.getAdjacentCell(cell, direction);
        boolean _and = false;
        boolean _notEquals = (!Objects.equal(endCell, null));
        if (!_notEquals) {
          _and = false;
        } else {
          ServerLinks _links = this.board.getLinks();
          boolean _hasLink = _links.hasLink(cell, endCell, player);
          boolean _not = (!_hasLink);
          _and = _not;
        }
        if (_and) {
          ServerLinks _links_1 = this.board.getLinks();
          _links_1.toggleOutgoingLink(cell, endCell, player);
          return;
        }
      }
    }
  }
  
  private IntPoint randomCoordinate() {
    int _xSize = this.board.getXSize();
    final int x = ComputerAI.rand.nextInt(_xSize);
    int _ySize = this.board.getYSize();
    final int y = ComputerAI.rand.nextInt(_ySize);
    return IntPoint.from(x, y);
  }
}
