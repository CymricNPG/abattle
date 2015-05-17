package net.npg.abattle.client.view.boardscene.debug;

import net.npg.abattle.client.view.boardscene.CellShape;
import net.npg.abattle.client.view.boardscene.debug.DebugCell;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

@SuppressWarnings("all")
public class NoDebug implements DebugCell {
  @Override
  public Function1<CellShape, String> getTextCreator() {
    final Function1<CellShape, String> _function = new Function1<CellShape, String>() {
      @Override
      public String apply(final CellShape shape) {
        return "";
      }
    };
    return _function;
  }
  
  protected final static String NAME = "nodebug";
  
  @Override
  public String getName() {
    return NoDebug.NAME;
  }
}
