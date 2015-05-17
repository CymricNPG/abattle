package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.LoginResultCommand;
import net.npg.abattle.communication.service.common.ClientInfoResult;

@SuppressWarnings("all")
public class LoginResultCommandBuilder {
  private ClientInfoResult result;
  
  public LoginResultCommandBuilder result(final ClientInfoResult result) {
    this.result=result;
    return this;
  }
  
  public LoginResultCommand build() {
    return new LoginResultCommand(
    result
    );
  }
}
