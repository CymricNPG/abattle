package net.npg.abattle.communication.network.impl;

import com.google.common.base.Optional;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.CommandProcessor;
import net.npg.abattle.communication.command.ErrorCommand;
import net.npg.abattle.communication.command.GameCommand;
import net.npg.abattle.communication.network.NetworkServer;
import net.npg.abattle.communication.network.NetworkService;
import net.npg.abattle.communication.service.ServerService;

@SuppressWarnings("all")
public class NetworkServerProcessor implements CommandProcessor<GameCommand> {
  private NetworkServer connection;
  
  public NetworkServerProcessor(final NetworkServer connection) {
    Validate.notNulls(connection);
    this.connection = connection;
  }
  
  @Override
  public Optional<ErrorCommand> execute(final GameCommand command, final int destination) {
    Optional<ErrorCommand> _xblockexpression = null;
    {
      boolean _sendTo = this.connection.sendTo(command, destination);
      boolean _not = (!_sendTo);
      if (_not) {
        NetworkService _service = this.connection.<NetworkService>getService(ServerService.SERVER_SERVICE_ID);
        final ServerService service = ((ServerService) _service);
        service.leaveGames(destination);
      }
      _xblockexpression = Optional.<ErrorCommand>absent();
    }
    return _xblockexpression;
  }
}
