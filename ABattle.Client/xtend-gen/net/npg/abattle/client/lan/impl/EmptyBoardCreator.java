package net.npg.abattle.client.lan.impl;

import java.util.Collection;
import net.npg.abattle.common.error.BaseException;
import net.npg.abattle.common.model.BoardCreator;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.model.client.ClientBoard;
import net.npg.abattle.common.model.client.impl.ClientBoardImpl;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.Validate;

@SuppressWarnings("all")
public class EmptyBoardCreator implements BoardCreator<ClientBoard> {
  private IntPoint size;
  
  private CheckModelElement checker;
  
  private ClientBoardImpl board;
  
  public EmptyBoardCreator(final IntPoint size, final CheckModelElement checker) {
    Validate.notNull(size);
    Validate.notNull(checker);
    this.size = size;
    this.checker = checker;
    ClientBoardImpl _clientBoardImpl = new ClientBoardImpl(size.x, size.y);
    this.board = _clientBoardImpl;
  }
  
  @Override
  public ClientBoard getBoard() {
    return this.board;
  }
  
  @Override
  public void run(final Collection<? extends Player> players) throws BaseException {
  }
}
