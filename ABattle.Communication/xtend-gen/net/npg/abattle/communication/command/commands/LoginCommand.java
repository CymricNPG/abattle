package net.npg.abattle.communication.command.commands;

import net.npg.abattle.common.utils.TransferData;

@TransferData
@SuppressWarnings("all")
public class LoginCommand {
  public String name;
  
  public LoginCommand(final String name) {
    this.name = name;
  }
  
  public LoginCommand() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("name",name)
    .addValue(super.toString())
    .toString();
  }
}
