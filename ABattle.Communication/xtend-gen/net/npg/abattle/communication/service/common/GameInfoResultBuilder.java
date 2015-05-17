package net.npg.abattle.communication.service.common;

import net.npg.abattle.communication.service.common.GameInfo;
import net.npg.abattle.communication.service.common.GameInfoResult;

@SuppressWarnings("all")
public class GameInfoResultBuilder {
  private GameInfo gameInfo;
  
  public GameInfoResultBuilder gameInfo(final GameInfo gameInfo) {
    this.gameInfo=gameInfo;
    return this;
  }
  
  private int id;
  
  public GameInfoResultBuilder id(final int id) {
    this.id=id;
    return this;
  }
  
  private String errorMessage;
  
  public GameInfoResultBuilder errorMessage(final String errorMessage) {
    this.errorMessage=errorMessage;
    return this;
  }
  
  private boolean success;
  
  public GameInfoResultBuilder success(final boolean success) {
    this.success=success;
    return this;
  }
  
  public GameInfoResult build() {
    return new GameInfoResult(
    gameInfo,id,errorMessage,success
    );
  }
}
