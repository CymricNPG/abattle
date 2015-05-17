package net.npg.abattle.server.logic;

import net.npg.abattle.common.model.Board;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.server.logic.ComputeWinSituation;
import net.npg.abattle.server.logic.ComputerAI;
import net.npg.abattle.server.logic.Logic;
import net.npg.abattle.server.logic.SimpleDistributeArmies;
import net.npg.abattle.server.model.ServerBoard;
import net.npg.abattle.server.model.ServerCell;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerLinks;
import net.npg.abattle.server.model.ServerPlayer;

@SuppressWarnings("all")
public class SimpleGameLogic {
  public static void run(final ServerGame game) {
    Board<ServerPlayer, ServerCell, ServerLinks> _board = game.getBoard();
    final ServerBoard board = ((ServerBoard) _board);
    SimpleDistributeArmies _simpleDistributeArmies = new SimpleDistributeArmies();
    GameConfiguration _gameConfiguration = game.getGameConfiguration();
    Logic _instance = _simpleDistributeArmies.getInstance(board, _gameConfiguration);
    _instance.run();
    ComputerAI _computerAI = new ComputerAI(game);
    _computerAI.run();
    ComputeWinSituation.run(game);
  }
}
