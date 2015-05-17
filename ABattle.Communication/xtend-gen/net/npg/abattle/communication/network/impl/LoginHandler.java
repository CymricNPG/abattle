package net.npg.abattle.communication.network.impl;

import com.esotericsoftware.kryonet.Connection;
import com.google.common.base.Objects;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.ReceiveHandler;
import net.npg.abattle.communication.command.commands.LoginCommand;
import net.npg.abattle.communication.command.commands.LoginResultCommand;
import net.npg.abattle.communication.network.impl.MyConnection;
import net.npg.abattle.communication.service.ServerService;
import net.npg.abattle.communication.service.common.ClientInfoResult;

/**
 * connects a connection with a client id, registered at the server, waiting for the login from the client
 */
@SuppressWarnings("all")
public class LoginHandler implements ReceiveHandler<LoginCommand> {
  private ServerService service;
  
  public LoginHandler(final ServerService service) {
    Validate.notNull(service);
    this.service = service;
  }
  
  @Override
  public void handle(final LoginCommand object, final Connection connection) {
    boolean _equals = Objects.equal(object, null);
    if(_equals) return;;
    if((!(connection instanceof MyConnection))) return;;
    final ClientInfoResult result = this.service.login(object.name);
    if (result.success) {
      ((MyConnection) connection).setClientId(result.clientInfo.id);
    }
    LoginResultCommand _loginResultCommand = new LoginResultCommand(result);
    connection.sendTCP(_loginResultCommand);
  }
  
  @Override
  public boolean canHandle(final Object object) {
    return (object instanceof LoginCommand);
  }
}
