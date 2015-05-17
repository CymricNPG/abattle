package net.npg.abattle.communication.command.commands;

import net.npg.abattle.communication.command.commands.LoginCommand;

@SuppressWarnings("all")
public class LoginCommandBuilder {
  private String name;
  
  public LoginCommandBuilder name(final String name) {
    this.name=name;
    return this;
  }
  
  public LoginCommand build() {
    return new LoginCommand(
    name
    );
  }
}
