package net.npg.abattle.server.logic;

import com.google.common.base.Optional;
import java.util.Collection;
import java.util.Set;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.BoardCreator;
import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.model.impl.ColorImpl;
import net.npg.abattle.common.model.impl.GameConfigurationImpl;
import net.npg.abattle.common.utils.FieldLoop;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.LongHolder;
import net.npg.abattle.server.logic.SimpleGameLogic;
import net.npg.abattle.server.model.BoardInitializedNotifier;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerLink;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;
import net.npg.abattle.server.model.impl.ServerBoardImpl;
import net.npg.abattle.server.model.impl.ServerCellImpl;
import net.npg.abattle.server.model.impl.ServerGameImpl;
import net.npg.abattle.server.model.impl.ServerPlayerImpl;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class SimpleGameLogicTest {
  private static int XSIZE = 8;
  
  private static int YSIZE = 8;
  
  private int id = 0;
  
  private BoardInitializedNotifier createNotifier() {
    return new BoardInitializedNotifier() {
      @Override
      public void boardCreated(final Board<ServerPlayer, ServerCell, ServerLinks> board) {
      }
    };
  }
  
  @Test
  public void testFight() {
    try {
      final ServerGameImpl game = this.createGame(2);
      Set<ServerPlayer> _players = game.getPlayers();
      final ServerPlayer player = ((ServerPlayer[])Conversions.unwrapArray(_players, ServerPlayer.class))[0];
      final ServerPlayerImpl enemy = new ServerPlayerImpl("test2", 1, ColorImpl.BLACK, false);
      game.addPlayer(enemy);
      BoardInitializedNotifier _createNotifier = this.createNotifier();
      game.startGame(_createNotifier);
      SimpleGameLogic.run(game);
      this.testTotalStrength(0, game);
      Board<ServerPlayer, ServerCell, ServerLinks> _board = game.getBoard();
      final ServerCell cell1 = _board.getCellAt(0, 0);
      Board<ServerPlayer, ServerCell, ServerLinks> _board_1 = game.getBoard();
      final ServerCell cell2 = _board_1.getCellAt(0, 1);
      this.makeLink(0, 0, 0, 1, game, player);
      this.makeLink(0, 1, 0, 0, game, enemy);
      cell1.setStrength(20);
      cell2.setStrength(20);
      SimpleGameLogic.run(game);
      this.testTotalStrength(36, game);
      for (int i = 0; (i < 8); i++) {
        SimpleGameLogic.run(game);
      }
      SimpleGameLogic.run(game);
      this.testTotalStrength(0, game);
      Optional<Player> _owner = cell1.<Player>getOwner();
      boolean _isPresent = _owner.isPresent();
      Assert.assertFalse(_isPresent);
      Optional<Player> _owner_1 = cell2.<Player>getOwner();
      Player _get = _owner_1.get();
      Assert.assertEquals(enemy, _get);
      Board<ServerPlayer, ServerCell, ServerLinks> _board_2 = game.getBoard();
      ServerLinks _links = _board_2.getLinks();
      Collection<ServerLink> _links_1 = _links.getLinks();
      int _size = _links_1.size();
      Assert.assertEquals(1, _size);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testRemoveLink() {
    try {
      final ServerGameImpl game = this.createGame(2);
      Set<ServerPlayer> _players = game.getPlayers();
      final ServerPlayer player = ((ServerPlayer[])Conversions.unwrapArray(_players, ServerPlayer.class))[0];
      final ServerPlayerImpl enemy = new ServerPlayerImpl("test2", 1, ColorImpl.BLACK, false);
      game.addPlayer(enemy);
      BoardInitializedNotifier _createNotifier = this.createNotifier();
      game.startGame(_createNotifier);
      SimpleGameLogic.run(game);
      this.testTotalStrength(0, game);
      Board<ServerPlayer, ServerCell, ServerLinks> _board = game.getBoard();
      final ServerCell cell1 = _board.getCellAt(0, 0);
      Board<ServerPlayer, ServerCell, ServerLinks> _board_1 = game.getBoard();
      final ServerCell cell2 = _board_1.getCellAt(0, 1);
      this.makeLink(0, 0, 0, 1, game, player);
      cell1.setStrength(20);
      SimpleGameLogic.run(game);
      this.testTotalStrength(20, game);
      Optional<Player> _owner = cell1.<Player>getOwner();
      Player _get = _owner.get();
      Assert.assertEquals(player, _get);
      Optional<Player> _owner_1 = cell2.<Player>getOwner();
      Player _get_1 = _owner_1.get();
      Assert.assertEquals(player, _get_1);
      Board<ServerPlayer, ServerCell, ServerLinks> _board_2 = game.getBoard();
      ServerLinks _links = _board_2.getLinks();
      Collection<ServerLink> _links_1 = _links.getLinks();
      int _size = _links_1.size();
      Assert.assertEquals(1, _size);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testCircle() {
    try {
      final ServerGameImpl game = this.createGame(1);
      BoardInitializedNotifier _createNotifier = this.createNotifier();
      game.startGame(_createNotifier);
      Set<ServerPlayer> _players = game.getPlayers();
      final ServerPlayer player = ((ServerPlayer[])Conversions.unwrapArray(_players, ServerPlayer.class))[0];
      SimpleGameLogic.run(game);
      this.testTotalStrength(0, game);
      this.makeLinks(game, player);
      this.testTotalStrength(0, game);
      SimpleGameLogic.run(game);
      this.testTotalStrength(0, game);
      Board<ServerPlayer, ServerCell, ServerLinks> _board = game.getBoard();
      IntPoint _from = IntPoint.from(0, 0);
      final ServerCell cell1 = _board.getCellAt(_from);
      Board<ServerPlayer, ServerCell, ServerLinks> _board_1 = game.getBoard();
      IntPoint _from_1 = IntPoint.from(0, 1);
      final ServerCell cell2 = _board_1.getCellAt(_from_1);
      Board<ServerPlayer, ServerCell, ServerLinks> _board_2 = game.getBoard();
      IntPoint _from_2 = IntPoint.from(1, 1);
      final ServerCell cell3 = _board_2.getCellAt(_from_2);
      Board<ServerPlayer, ServerCell, ServerLinks> _board_3 = game.getBoard();
      IntPoint _from_3 = IntPoint.from(1, 0);
      final ServerCell cell4 = _board_3.getCellAt(_from_3);
      boolean _isAdjacentTo = cell1.<ServerCell>isAdjacentTo(cell2);
      Assert.assertTrue(_isAdjacentTo);
      boolean _isAdjacentTo_1 = cell2.<ServerCell>isAdjacentTo(cell3);
      Assert.assertTrue(_isAdjacentTo_1);
      boolean _isAdjacentTo_2 = cell3.<ServerCell>isAdjacentTo(cell4);
      Assert.assertTrue(_isAdjacentTo_2);
      boolean _isAdjacentTo_3 = cell4.<ServerCell>isAdjacentTo(cell1);
      Assert.assertTrue(_isAdjacentTo_3);
      cell1.setStrength(50);
      this.testTotalStrength(50, game);
      SimpleGameLogic.run(game);
      SimpleGameLogic.run(game);
      SimpleGameLogic.run(game);
      SimpleGameLogic.run(game);
      SimpleGameLogic.run(game);
      SimpleGameLogic.run(game);
      SimpleGameLogic.run(game);
      this.testTotalStrength(50, game);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public ServerGameImpl createGame(final int players) {
    try {
      ServerGameImpl _xblockexpression = null;
      {
        final ServerPlayerImpl player = new ServerPlayerImpl("test", 0, ColorImpl.BLACK, false);
        final GameConfigurationImpl conf = new GameConfigurationImpl();
        GameConfigurationData _gameConfigurationData = new GameConfigurationData();
        conf.setConfiguration(_gameConfigurationData);
        GameConfigurationData _configuration = conf.getConfiguration();
        _configuration.setBaseGrowthPerTick(10);
        GameConfigurationData _configuration_1 = conf.getConfiguration();
        _configuration_1.setMaxCellStrength(50);
        GameConfigurationData _configuration_2 = conf.getConfiguration();
        _configuration_2.setMaxCellHeight(1);
        GameConfigurationData _configuration_3 = conf.getConfiguration();
        _configuration_3.setMaxMovement(10);
        conf.setXSize(SimpleGameLogicTest.XSIZE);
        conf.setYSize(SimpleGameLogicTest.YSIZE);
        GameConfigurationData _configuration_4 = conf.getConfiguration();
        _configuration_4.setWinCondition(2);
        final BoardCreator<Board<ServerPlayer, ServerCell, ServerLinks>> creator = new BoardCreator<Board<ServerPlayer, ServerCell, ServerLinks>>() {
          @Override
          public Board<ServerPlayer, ServerCell, ServerLinks> getBoard() {
            final ServerBoardImpl board = new ServerBoardImpl(SimpleGameLogicTest.XSIZE, SimpleGameLogicTest.YSIZE);
            final Procedure2<Integer, Integer> _function = new Procedure2<Integer, Integer>() {
              @Override
              public void apply(final Integer x, final Integer y) {
                int _plusPlus = SimpleGameLogicTest.this.id++;
                IntPoint _from = IntPoint.from((x).intValue(), (y).intValue());
                CheckModelElement _checker = conf.getChecker();
                ServerCellImpl _serverCellImpl = new ServerCellImpl(_plusPlus, _from, 0, CellTypes.PLAIN, _checker);
                board.setCellAt(_serverCellImpl);
              }
            };
            FieldLoop.visitAllFields(SimpleGameLogicTest.XSIZE, SimpleGameLogicTest.YSIZE, _function);
            return board;
          }
          
          @Override
          public void run(final Collection players) throws BaseException {
          }
        };
        final ServerGameImpl game = new ServerGameImpl(players, creator, conf);
        game.addPlayer(player);
        _xblockexpression = game;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void testTotalStrength(final int expectedStrength, final ServerGameImpl game) {
    final LongHolder strength = new LongHolder();
    final Procedure2<Integer, Integer> _function = new Procedure2<Integer, Integer>() {
      @Override
      public void apply(final Integer x, final Integer y) {
        Board<ServerPlayer, ServerCell, ServerLinks> _board = game.getBoard();
        ServerCell _cellAt = _board.getCellAt((x).intValue(), (y).intValue());
        int _strength = _cellAt.getStrength();
        strength.add(_strength);
      }
    };
    FieldLoop.visitAllFields(SimpleGameLogicTest.XSIZE, SimpleGameLogicTest.YSIZE, _function);
    Assert.assertEquals(expectedStrength, ((int) strength.value));
  }
  
  public void makeLinks(final ServerGame game, final ServerPlayer player) {
    this.makeLink(0, 0, 0, 1, game, player);
    this.makeLink(0, 1, 1, 1, game, player);
    this.makeLink(1, 1, 1, 0, game, player);
    this.makeLink(1, 0, 0, 0, game, player);
  }
  
  public void makeLink(final int xs, final int ys, final int xe, final int ye, final ServerGame game, final ServerPlayer player) {
    Board<ServerPlayer, ServerCell, ServerLinks> _board = game.getBoard();
    IntPoint _from = IntPoint.from(xs, ys);
    final ServerCell startCell = _board.getCellAt(_from);
    Board<ServerPlayer, ServerCell, ServerLinks> _board_1 = game.getBoard();
    IntPoint _from_1 = IntPoint.from(xe, ye);
    final ServerCell endCell = _board_1.getCellAt(_from_1);
    Board<ServerPlayer, ServerCell, ServerLinks> _board_2 = game.getBoard();
    ServerLinks _links = _board_2.getLinks();
    _links.toggleOutgoingLink(startCell, endCell, player);
    startCell.setOwner(player);
  }
}
