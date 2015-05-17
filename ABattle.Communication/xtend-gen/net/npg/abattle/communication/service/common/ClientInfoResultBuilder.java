package net.npg.abattle.communication.service.common;

import net.npg.abattle.communication.service.common.ClientInfo;
import net.npg.abattle.communication.service.common.ClientInfoResult;

@SuppressWarnings("all")
public class ClientInfoResultBuilder {
  private ClientInfo clientInfo;
  
  public ClientInfoResultBuilder clientInfo(final ClientInfo clientInfo) {
    this.clientInfo=clientInfo;
    return this;
  }
  
  private String errorMessage;
  
  public ClientInfoResultBuilder errorMessage(final String errorMessage) {
    this.errorMessage=errorMessage;
    return this;
  }
  
  private boolean success;
  
  public ClientInfoResultBuilder success(final boolean success) {
    this.success=success;
    return this;
  }
  
  public ClientInfoResult build() {
    return new ClientInfoResult(
    clientInfo,errorMessage,success
    );
  }
}
