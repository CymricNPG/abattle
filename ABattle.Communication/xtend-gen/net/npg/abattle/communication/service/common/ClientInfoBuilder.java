package net.npg.abattle.communication.service.common;

import net.npg.abattle.communication.service.common.ClientInfo;

@SuppressWarnings("all")
public class ClientInfoBuilder {
  private int id;
  
  public ClientInfoBuilder id(final int id) {
    this.id=id;
    return this;
  }
  
  private String name;
  
  public ClientInfoBuilder name(final String name) {
    this.name=name;
    return this;
  }
  
  public ClientInfo build() {
    return new ClientInfo(
    id,name
    );
  }
}
