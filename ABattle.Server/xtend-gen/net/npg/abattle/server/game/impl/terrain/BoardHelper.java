package net.npg.abattle.server.game.impl.terrain;

import net.npg.abattle.common.IDGenerator;
import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.CheckModelElement;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.utils.FieldLoop;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.server.model.impl.ServerBoardImpl;
import net.npg.abattle.server.model.impl.ServerCellImpl;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

@SuppressWarnings("all")
public final class BoardHelper {
  public static void fillBoard(final ServerBoardImpl board, final IntPoint size, final CheckModelElement checker, final Function2<Integer, Integer, Integer> heightFunction) {
    final Procedure2<Integer, Integer> _function = new Procedure2<Integer, Integer>() {
      @Override
      public void apply(final Integer x, final Integer y) {
        final int id = IDGenerator.generateId();
        final IntPoint boardCoordinate = IntPoint.from((x).intValue(), (y).intValue());
        final int height = (heightFunction.apply(x, y)).intValue();
        final CellTypes cellType = CellTypes.PLAIN;
        final ServerCellImpl cell = new ServerCellImpl(id, boardCoordinate, height, cellType, checker);
        cell.setBattle(false);
        cell.setStrength(0);
        cell.<Player>setOwner(null);
        board.setCellAt(cell);
      }
    };
    FieldLoop.visitAllFields(size.x, size.y, _function);
  }
}
