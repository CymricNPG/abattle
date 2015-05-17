package net.npg.abattle.server.game.impl;

import com.google.common.base.Objects;
import java.util.Collection;
import java.util.List;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.configuration.GameLoopConfigurationData;
import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.model.GameStatus;
import net.npg.abattle.common.utils.MyRunnable;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandQueueServer;
import net.npg.abattle.communication.command.CommandType;
import net.npg.abattle.communication.command.commands.GameFinishedCommand;
import net.npg.abattle.server.ServerConstants;
import net.npg.abattle.server.game.GameEnvironment;
import net.npg.abattle.server.game.impl.GameSender;
import net.npg.abattle.server.game.impl.StatisticsSender;
import net.npg.abattle.server.logic.ComputeWinSituation;
import net.npg.abattle.server.logic.ComputerAI;
import net.npg.abattle.server.logic.Logic;
import net.npg.abattle.server.logic.Logics;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ServerGameRunner extends MyRunnable {
  private final GameEnvironment gameEnvironment;
  
  private final ServerGame game;
  
  private long lastTick;
  
  private long lastLog;
  
  private final long fps;
  
  private final GameSender gameSender;
  
  private final StatisticsSender statisticsSender;
  
  private final CommandQueueServer commandQueue;
  
  public ServerGameRunner(final GameEnvironment gameEnvironment, final CommandQueueServer commandQueue) {
    super("ServerGame");
    Validate.notNull(gameEnvironment);
    Validate.notNull(commandQueue);
    this.gameEnvironment = gameEnvironment;
    ServerGame _serverGame = gameEnvironment.getServerGame();
    this.game = _serverGame;
    long _currentTimeMillis = System.currentTimeMillis();
    this.lastTick = _currentTimeMillis;
    this.lastLog = 0L;
    GameSender _gameSender = new GameSender(gameEnvironment, commandQueue);
    this.gameSender = _gameSender;
    StatisticsSender _statisticsSender = new StatisticsSender(gameEnvironment, commandQueue);
    this.statisticsSender = _statisticsSender;
    this.commandQueue = commandQueue;
    ComponentLookup _instance = ComponentLookup.getInstance();
    ConfigurationComponent _component = _instance.<ConfigurationComponent>getComponent(ConfigurationComponent.class);
    GameLoopConfigurationData _gameLoopConfiguration = _component.getGameLoopConfiguration();
    long _logicUpdatesPerSecond = _gameLoopConfiguration.getLogicUpdatesPerSecond();
    this.fps = _logicUpdatesPerSecond;
  }
  
  @Override
  public void execute() {
    try {
      int _id = this.game.getId();
      String _plus = (Integer.valueOf(_id) + ": Logic started.");
      ServerConstants.LOG.info(_plus);
      while ((!Objects.equal(GameStatus.FINISHED, this.game.getStatus()))) {
        {
          this.waitForTick();
          this.doGameLogic();
          this.lifeCycleWait();
        }
      }
      int _id_1 = this.game.getId();
      String _plus_1 = (Integer.valueOf(_id_1) + ": Thread finished.");
      ServerConstants.LOG.info(_plus_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void waitForTick() {
    try {
      final long nextTick = (this.lastTick + (1000L / this.fps));
      final long currentTick = System.currentTimeMillis();
      if ((currentTick > nextTick)) {
        this.lastTick = currentTick;
        this.logServerToSlow((currentTick - nextTick));
        return;
      }
      Thread.sleep((nextTick - currentTick));
      long _currentTimeMillis = System.currentTimeMillis();
      this.lastTick = _currentTimeMillis;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void logServerToSlow(final long diff) {
    final long currentTick = System.currentTimeMillis();
    if (((this.lastLog + (1000L * 10)) > currentTick)) {
      return;
    }
    this.lastLog = currentTick;
    int _id = this.game.getId();
    String _plus = (Integer.valueOf(_id) + ": Server to slow: ");
    String _plus_1 = (_plus + Long.valueOf(diff));
    String _plus_2 = (_plus_1 + "ms");
    ServerConstants.LOG.error(_plus_2);
  }
  
  public void doGameLogic() {
    GameStatus _status = this.game.getStatus();
    boolean _notEquals = (!Objects.equal(GameStatus.RUNNING, _status));
    if(_notEquals) return;;
    this.makeLogic();
    this.sendUpdates();
    this.sendFinishedGame();
  }
  
  public void sendFinishedGame() {
    GameStatus _status = this.game.getStatus();
    boolean _notEquals = (!Objects.equal(GameStatus.FINISHED, _status));
    if(_notEquals) return;;
    Collection<ServerPlayer> _players = this.game.getPlayers();
    final Function1<ServerPlayer, Integer> _function = new Function1<ServerPlayer, Integer>() {
      @Override
      public Integer apply(final ServerPlayer it) {
        return Integer.valueOf(it.getId());
      }
    };
    Iterable<Integer> _map = IterableExtensions.<ServerPlayer, Integer>map(_players, _function);
    final List<Integer> destinations = IterableExtensions.<Integer>toList(_map);
    int _id = this.game.getId();
    final GameFinishedCommand data = new GameFinishedCommand(false, _id);
    this.commandQueue.addCommand(data, CommandType.TOCLIENT, destinations);
  }
  
  public void sendUpdates() {
    this.gameSender.sendGameData();
    this.statisticsSender.sendGameData();
  }
  
  public void makeLogic() {
    Logic _selectedClass = Logics.logicMap.getSelectedClass();
    Board<ServerPlayer, ServerCell, ServerLinks> _board = this.game.getBoard();
    GameConfiguration _gameConfiguration = this.game.getGameConfiguration();
    final Logic logic = _selectedClass.getInstance(((ServerBoard) _board), _gameConfiguration);
    logic.run();
    ComputerAI _computerAI = new ComputerAI(this.game);
    _computerAI.run();
    ComputeWinSituation.run(this.game);
  }
}
