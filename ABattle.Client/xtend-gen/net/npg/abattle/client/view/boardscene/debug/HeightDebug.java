package net.npg.abattle.client.view.boardscene.debug;

import net.npg.abattle.client.view.boardscene.CellShape;
import net.npg.abattle.client.view.boardscene.debug.DebugCell;
import net.npg.abattle.common.model.client.ClientCell;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

@SuppressWarnings("all")
public class HeightDebug implements DebugCell {
  @Override
  public Function1<CellShape, String> getTextCreator() {
    final Function1<CellShape, String> _function = new Function1<CellShape, String>() {
      @Override
      public String apply(final CellShape shape) {
        ClientCell _cell = shape.getCell();
        int _height = _cell.getHeight();
        return Integer.valueOf(_height).toString();
      }
    };
    return _function;
  }
  
  protected final static String NAME = "heightdebug";
  
  @Override
  public String getName() {
    return HeightDebug.NAME;
  }
}
