package net.npg.abattle.server.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import net.npg.abattle.common.configuration.GameConfigurationData;
import net.npg.abattle.common.model.GameConfiguration;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.service.common.BooleanResult;
import net.npg.abattle.communication.service.common.ClientInfo;
import net.npg.abattle.communication.service.common.ClientInfoResult;
import net.npg.abattle.communication.service.common.GameInfo;
import net.npg.abattle.communication.service.common.GameInfoResult;
import net.npg.abattle.communication.service.common.GameInfoResultBuilder;
import net.npg.abattle.communication.service.common.GameParameterInfo;
import net.npg.abattle.communication.service.common.GameParameterInfoBuilder;
import net.npg.abattle.communication.service.common.MutableIntPoint;
import net.npg.abattle.server.ServerConstants;
import net.npg.abattle.server.game.GameEnvironment;
import net.npg.abattle.server.model.ServerGame;
import net.npg.abattle.server.model.ServerPlayer;
import org.eclipse.xtext.xbase.lib.Conversions;

@SuppressWarnings("all")
public class ResultBuilder {
  public static BooleanResult buildBooleanResultError(final String message) {
    BooleanResult _xblockexpression = null;
    {
      ServerConstants.LOG.debug(message);
      _xblockexpression = new BooleanResult(message, false);
    }
    return _xblockexpression;
  }
  
  public static BooleanResult buildBooleanResultSuccess() {
    return new BooleanResult("", true);
  }
  
  public static ClientInfoResult buildClientInfoResultError(final String message) {
    return new ClientInfoResult(null, message, false);
  }
  
  public static GameInfoResult buildGameInfoResultError(final String message) {
    return new GameInfoResult(null, 0, message, false);
  }
  
  public static GameInfoResult buildGameInfoResultSuccess(final GameEnvironment game) {
    GameInfo _convert = ResultBuilder.convert(game);
    int _id = game.getId();
    return new GameInfoResult(_convert, _id, "", true);
  }
  
  public static BooleanResult buildResult(final boolean success, final String errorMessage) {
    BooleanResult _xifexpression = null;
    if (success) {
      _xifexpression = ResultBuilder.buildBooleanResultSuccess();
    } else {
      _xifexpression = ResultBuilder.buildBooleanResultError(errorMessage);
    }
    return _xifexpression;
  }
  
  public static ClientInfo[] convert(final Collection<ServerPlayer> players) {
    ArrayList<ClientInfo> _xblockexpression = null;
    {
      final ArrayList<ClientInfo> clientInfos = new ArrayList<ClientInfo>();
      for (final ServerPlayer player : players) {
        ClientInfo _convert = ResultBuilder.convert(player);
        clientInfos.add(_convert);
      }
      _xblockexpression = clientInfos;
    }
    return ((ClientInfo[])Conversions.unwrapArray(_xblockexpression, ClientInfo.class));
  }
  
  public static GameInfo convert(final GameEnvironment gameEnvironment) {
    GameInfo _xblockexpression = null;
    {
      final ServerGame game = gameEnvironment.getServerGame();
      final GameInfo gameInfo = new GameInfo();
      Collection<ServerPlayer> _players = game.getPlayers();
      int _size = _players.size();
      gameInfo.currentPlayers = _size;
      int _maxPlayers = game.getMaxPlayers();
      gameInfo.maxPlayers = _maxPlayers;
      Collection<ServerPlayer> _players_1 = game.getPlayers();
      ClientInfo[] _convert = ResultBuilder.convert(_players_1);
      gameInfo.players = _convert;
      GameParameterInfoBuilder _gameParameterInfoBuilder = new GameParameterInfoBuilder();
      GameConfiguration _gameConfiguration = game.getGameConfiguration();
      GameConfigurationData _configuration = _gameConfiguration.getConfiguration();
      int _maxCellHeight = _configuration.getMaxCellHeight();
      GameParameterInfoBuilder _maxCellHeight_1 = _gameParameterInfoBuilder.maxCellHeight(_maxCellHeight);
      GameConfiguration _gameConfiguration_1 = game.getGameConfiguration();
      GameConfigurationData _configuration_1 = _gameConfiguration_1.getConfiguration();
      int _maxCellStrength = _configuration_1.getMaxCellStrength();
      GameParameterInfoBuilder _maxCellStrength_1 = _maxCellHeight_1.maxCellStrength(_maxCellStrength);
      GameConfiguration _gameConfiguration_2 = game.getGameConfiguration();
      GameConfigurationData _configuration_2 = _gameConfiguration_2.getConfiguration();
      int _maxMovement = _configuration_2.getMaxMovement();
      GameParameterInfoBuilder _maxMovement_1 = _maxCellStrength_1.maxMovement(_maxMovement);
      ServerGame _serverGame = gameEnvironment.getServerGame();
      IntPoint _size_1 = _serverGame.getSize();
      MutableIntPoint _from = MutableIntPoint.from(_size_1);
      GameParameterInfoBuilder _size_2 = _maxMovement_1.size(_from);
      GameParameterInfo _build = _size_2.build();
      gameInfo.parameters = _build;
      _xblockexpression = gameInfo;
    }
    return _xblockexpression;
  }
  
  public static ClientInfo convert(final ServerPlayer player) {
    ClientInfo _xblockexpression = null;
    {
      final ClientInfo clientInfo = new ClientInfo();
      int _id = player.getId();
      clientInfo.id = _id;
      String _name = player.getName();
      clientInfo.name = _name;
      _xblockexpression = clientInfo;
    }
    return _xblockexpression;
  }
  
  public static BooleanResult unknownGame(final int gameId) {
    return ResultBuilder.buildBooleanResultError(("Unknown Game:" + Integer.valueOf(gameId)));
  }
  
  public static GameInfoResult toGameInfoResult(final BooleanResult result) {
    Validate.isFalse(result.success);
    GameInfoResultBuilder _gameInfoResultBuilder = new GameInfoResultBuilder();
    GameInfoResultBuilder _success = _gameInfoResultBuilder.success(false);
    GameInfoResultBuilder _errorMessage = _success.errorMessage(result.errorMessage);
    return _errorMessage.build();
  }
  
  public static BooleanResult unknownPlayer(final int playerId) {
    return ResultBuilder.buildBooleanResultError(("Unknown Player:" + Integer.valueOf(playerId)));
  }
}
