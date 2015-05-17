package net.npg.abattle.client.commands;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import net.npg.abattle.client.ClientConstants;
import net.npg.abattle.client.ClientException;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.model.client.ClientGame;
import net.npg.abattle.common.model.client.ClientLinks;
import net.npg.abattle.common.model.client.ClientPlayer;
import net.npg.abattle.common.model.impl.LinkImpl;
import net.npg.abattle.common.utils.FieldLoop;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandProcessor;
import net.npg.abattle.communication.command.CommandUpdateNotifier;
import net.npg.abattle.communication.command.ErrorCommand;
import net.npg.abattle.communication.command.commands.BoardUpdateCommand;
import net.npg.abattle.communication.command.data.CellUpdateData;
import net.npg.abattle.communication.command.data.LinkUpdateData;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

@SuppressWarnings("all")
public class BoardUpdateCommandProcessor implements CommandProcessor<BoardUpdateCommand> {
  private ClientGame game;
  
  private CommandUpdateNotifier notifier;
  
  public BoardUpdateCommandProcessor(final ClientGame game, final CommandUpdateNotifier notifier) {
    Validate.notNulls(game, notifier);
    this.game = game;
    this.notifier = notifier;
  }
  
  @Override
  public Optional<ErrorCommand> execute(final BoardUpdateCommand command, final int destination) {
    try {
      Optional<ErrorCommand> _xblockexpression = null;
      {
        Validate.notNull(command);
        int _id = this.game.getId();
        boolean _notEquals = (command.game != _id);
        if (_notEquals) {
          int _id_1 = this.game.getId();
          String _plus = ((("Received an update for a different game:" + Integer.valueOf(command.game)) + " My game is:") + Integer.valueOf(_id_1));
          ClientConstants.LOG.error(_plus);
          return Optional.<ErrorCommand>absent();
        }
        final CellUpdateData[][] cellUpdates = command.boardUpdate.cellUpdates;
        Board<ClientPlayer, ClientCell, ClientLinks> _board = this.game.getBoard();
        final ClientBoard board = ((ClientBoard) _board);
        boolean _isUpdateValid = this.isUpdateValid(board, cellUpdates);
        boolean _not = (!_isUpdateValid);
        if (_not) {
          throw new ClientException("Server send wrong board update");
        }
        this.updateBoard(board, cellUpdates);
        this.updateLinks(board, command.boardUpdate.linkUpdates);
        this.notifier.receivedCommand(command);
        _xblockexpression = Optional.<ErrorCommand>absent();
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void updateLinks(final ClientBoard board, final LinkUpdateData[] links) {
    final Function1<LinkUpdateData, Integer> _function = new Function1<LinkUpdateData, Integer>() {
      @Override
      public Integer apply(final LinkUpdateData it) {
        return Integer.valueOf(it.id);
      }
    };
    List<Integer> _map = ListExtensions.<LinkUpdateData, Integer>map(((List<LinkUpdateData>)Conversions.doWrapArray(links)), _function);
    final HashSet<Integer> newLinks = new HashSet<Integer>(_map);
    final ClientLinks linksComponent = board.getLinks();
    linksComponent.preserverLinks(newLinks);
    final Function1<LinkUpdateData, Boolean> _function_1 = new Function1<LinkUpdateData, Boolean>() {
      @Override
      public Boolean apply(final LinkUpdateData it) {
        boolean _hasLink = linksComponent.hasLink(it.id);
        return Boolean.valueOf((!_hasLink));
      }
    };
    Iterable<LinkUpdateData> _filter = IterableExtensions.<LinkUpdateData>filter(((Iterable<LinkUpdateData>)Conversions.doWrapArray(links)), _function_1);
    final Procedure1<LinkUpdateData> _function_2 = new Procedure1<LinkUpdateData>() {
      @Override
      public void apply(final LinkUpdateData it) {
        BoardUpdateCommandProcessor.this.addLink(board, it);
      }
    };
    IterableExtensions.<LinkUpdateData>forEach(_filter, _function_2);
  }
  
  private void addLink(final ClientBoard board, final LinkUpdateData link) {
    final ClientCell startCell = board.getCell(link.startCellId);
    final ClientCell endCell = board.getCell(link.endCellId);
    final LinkImpl<ClientCell> clientLink = new LinkImpl<ClientCell>(link.id, startCell, endCell);
    ClientLinks _links = board.getLinks();
    _links.addLink(clientLink);
  }
  
  private void updateBoard(final Board<ClientPlayer, ClientCell, ClientLinks> board, final CellUpdateData[][] cellUpdates) {
    int _xSize = board.getXSize();
    int _ySize = board.getYSize();
    final Procedure2<Integer, Integer> _function = new Procedure2<Integer, Integer>() {
      @Override
      public void apply(final Integer x, final Integer y) {
        ClientCell _cellAt = board.getCellAt((x).intValue(), (y).intValue());
        CellUpdateData[] _get = cellUpdates[(x).intValue()];
        CellUpdateData _get_1 = _get[(y).intValue()];
        BoardUpdateCommandProcessor.this.updateCell(_cellAt, _get_1);
      }
    };
    FieldLoop.visitAllFields(_xSize, _ySize, _function);
  }
  
  private void updateCell(final ClientCell cell, final CellUpdateData update) {
    boolean _equals = Objects.equal(update, null);
    if (_equals) {
      this.updateVisible(cell, false);
    } else {
      this.checkId(cell, update.id);
      this.updateStrength(cell, update.strength);
      this.updateBattle(cell, update.battle);
      this.updateOwner(cell, update.owner);
      this.updateVisible(cell, true);
    }
  }
  
  private void checkId(final ClientCell cell, final int id) {
    int _id = cell.getId();
    boolean _notEquals = (_id != id);
    if (_notEquals) {
      int _id_1 = cell.getId();
      String _plus = ("Ids dont match:" + Integer.valueOf(_id_1));
      String _plus_1 = (_plus + "!=");
      String _plus_2 = (_plus_1 + Integer.valueOf(id));
      throw new IllegalArgumentException(_plus_2);
    }
  }
  
  private void updateVisible(final ClientCell cell, final boolean visible) {
    boolean _isVisible = cell.isVisible();
    boolean _equals = (_isVisible == visible);
    if (_equals) {
      return;
    }
    cell.setVisible(visible);
  }
  
  private void updateOwner(final ClientCell cell, final int newOwnerId) {
    Optional<Player> _owner = cell.<Player>getOwner();
    boolean _equals = Objects.equal(_owner, null);
    if (_equals) {
      cell.setOwner(null);
    }
    if ((newOwnerId == 0)) {
      Optional<Player> _owner_1 = cell.<Player>getOwner();
      boolean _isPresent = _owner_1.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        return;
      }
      cell.<Player>setOwner(null);
    } else {
      Optional<Player> _owner_2 = cell.<Player>getOwner();
      boolean _isPresent_1 = _owner_2.isPresent();
      boolean _not_1 = (!_isPresent_1);
      if (_not_1) {
        ClientPlayer _player = this.getPlayer(newOwnerId);
        cell.<ClientPlayer>setOwner(_player);
      } else {
        Optional<Player> _owner_3 = cell.<Player>getOwner();
        Player _get = _owner_3.get();
        int _id = _get.getId();
        boolean _equals_1 = (_id == newOwnerId);
        if (_equals_1) {
          return;
        }
      }
      ClientPlayer _player_1 = this.getPlayer(newOwnerId);
      cell.<ClientPlayer>setOwner(_player_1);
    }
  }
  
  private ClientPlayer getPlayer(final int playerId) {
    try {
      Collection<ClientPlayer> _players = this.game.getPlayers();
      final Function1<ClientPlayer, Boolean> _function = new Function1<ClientPlayer, Boolean>() {
        @Override
        public Boolean apply(final ClientPlayer it) {
          int _id = it.getId();
          return Boolean.valueOf((_id == playerId));
        }
      };
      final ClientPlayer player = IterableExtensions.<ClientPlayer>findFirst(_players, _function);
      boolean _equals = Objects.equal(player, null);
      if (_equals) {
        throw new ClientException(("Server send wrong player:" + Integer.valueOf(playerId)));
      }
      return player;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void updateBattle(final ClientCell cell, final boolean newBattle) {
    boolean _hasBattle = cell.hasBattle();
    boolean _equals = (_hasBattle == newBattle);
    if (_equals) {
      return;
    }
    cell.setBattle(newBattle);
  }
  
  private void updateStrength(final ClientCell cell, final int newStrength) {
    int _strength = cell.getStrength();
    boolean _equals = (_strength == newStrength);
    if (_equals) {
      return;
    }
    cell.setStrength(newStrength);
  }
  
  private boolean isUpdateValid(final Board<ClientPlayer, ClientCell, ClientLinks> board, final CellUpdateData[][] cellUpdates) {
    int _xSize = board.getXSize();
    int _length = cellUpdates.length;
    boolean _equals = (_xSize == _length);
    if (_equals) {
      int _ySize = board.getYSize();
      CellUpdateData[] _get = cellUpdates[0];
      int _length_1 = _get.length;
      return (_ySize == _length_1);
    }
    return false;
  }
}
