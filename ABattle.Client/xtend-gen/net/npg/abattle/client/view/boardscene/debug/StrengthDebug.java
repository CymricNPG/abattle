package net.npg.abattle.client.view.boardscene.debug;

import net.npg.abattle.client.view.boardscene.CellShape;
import net.npg.abattle.client.view.boardscene.debug.DebugCell;
import net.npg.abattle.common.model.client.ClientCell;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

@SuppressWarnings("all")
public class StrengthDebug implements DebugCell {
  @Override
  public Function1<CellShape, String> getTextCreator() {
    final Function1<CellShape, String> _function = new Function1<CellShape, String>() {
      @Override
      public String apply(final CellShape shape) {
        ClientCell _cell = shape.getCell();
        int _strength = _cell.getStrength();
        return Integer.valueOf(_strength).toString();
      }
    };
    return _function;
  }
  
  protected final static String NAME = "strengthdebug";
  
  @Override
  public String getName() {
    return StrengthDebug.NAME;
  }
}
