package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.service.common.ClientInfoResult;

@TransferData
@SuppressWarnings("all")
public class LoginResultCommand {
  public ClientInfoResult result;
  
  public LoginResultCommand(final ClientInfoResult result) {
    this.result = result;
  }
  
  public LoginResultCommand() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("result",result)
    .addValue(super.toString())
    .toString();
  }
}
