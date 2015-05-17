package net.npg.abattle.communication.service.common;

import net.npg.abattle.common.utils.TransferData;
import net.npg.abattle.communication.service.common.BooleanResult;
import net.npg.abattle.communication.service.common.GameInfo;

@TransferData
@SuppressWarnings("all")
public class GameInfoResult extends BooleanResult {
  public GameInfo gameInfo;
  
  /**
   * id of game
   */
  public int id;
  
  public GameInfoResult(final GameInfo gameInfo, final int id, final String errorMessage, final boolean success) {
    this.gameInfo = gameInfo;
    this.id = id;
    this.errorMessage = errorMessage;
    this.success = success;
  }
  
  public GameInfoResult() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("gameInfo",gameInfo)
    .add("id",id)
    .add("errorMessage",errorMessage)
    .add("success",success)
    .addValue(super.toString())
    .toString();
  }
}
