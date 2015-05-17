package net.npg.abattle.communication.network.impl;

import com.esotericsoftware.kryonet.Connection;
import net.npg.abattle.common.model.client.ClientIdRegister;
import net.npg.abattle.common.utils.Validate;
import net.npg.abattle.communication.command.ReceiveHandler;
import net.npg.abattle.communication.command.commands.LoginResultCommand;

/**
 * receive a login result
 */
@SuppressWarnings("all")
public class LoginResultHandler implements ReceiveHandler<LoginResultCommand> {
  private ClientIdRegister register;
  
  public LoginResultHandler(final ClientIdRegister register) {
    Validate.notNull(register);
    this.register = register;
  }
  
  @Override
  public void handle(final LoginResultCommand result, final Connection connection) {
    if (result.result.success) {
      final int id = result.result.clientInfo.id;
      this.register.setClientId(id);
      Thread _currentThread = Thread.currentThread();
      _currentThread.setName(("Client:" + Integer.valueOf(id)));
    } else {
      this.register.setFailed(result.result.errorMessage);
    }
  }
  
  @Override
  public boolean canHandle(final Object object) {
    return (object instanceof LoginResultCommand);
  }
}
