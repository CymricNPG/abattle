package net.npg.abattle.communication.service.common;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.service.common.BooleanResult;
import net.npg.abattle.communication.service.common.ClientInfo;

@TransferData
@SuppressWarnings("all")
public class ClientInfoResult extends BooleanResult {
  public ClientInfo clientInfo;
  
  public ClientInfoResult(final ClientInfo clientInfo, final String errorMessage, final boolean success) {
    this.clientInfo = clientInfo;
    this.errorMessage = errorMessage;
    this.success = success;
  }
  
  public ClientInfoResult() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("clientInfo",clientInfo)
    .add("errorMessage",errorMessage)
    .add("success",success)
    .addValue(super.toString())
    .toString();
  }
}
