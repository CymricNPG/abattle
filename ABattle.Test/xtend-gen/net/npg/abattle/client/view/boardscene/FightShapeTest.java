package net.npg.abattle.client.view.boardscene;

import com.google.common.base.Optional;
import java.util.Properties;
import net.npg.abattle.client.view.boardscene.FightShape;
import net.npg.abattle.common.configuration.GraphicsConfigurationData;
import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.hex.HexBase;
import net.npg.abattle.common.model.Cell;
import net.npg.abattle.common.model.CellTypes;
import net.npg.abattle.common.model.ModelVisitor;
import net.npg.abattle.common.model.Player;
import net.npg.abattle.common.model.client.ClientCell;
import net.npg.abattle.common.utils.IntPoint;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class FightShapeTest {
  @Test
  public void testDrawable() {
    abstract class __FightShapeTest_1 implements ClientCell {
      boolean battle;
    }
    
    final HexBase hexBase = new HexBase(1);
    final __FightShapeTest_1 cell = new __FightShapeTest_1() {
      @Override
      public boolean isVisible() {
        return true;
      }
      
      @Override
      public void setCellType(final CellTypes type) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public void setHeight(final int height) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public void setVisible(final boolean visible) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public IntPoint getBoardCoordinate() {
        return IntPoint.from(0, 0);
      }
      
      @Override
      public CellTypes getCellType() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public int getHeight() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public Optional<Player> getOwner() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public int getStrength() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public boolean hasBattle() {
        return this.battle;
      }
      
      @Override
      public boolean hasStructure() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public <CELLTYPE extends Cell> boolean isAdjacentTo(final CELLTYPE testCell) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public void setBattle(final boolean hasBattle) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public <PLAYERTYPE extends Player> void setOwner(final PLAYERTYPE player) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public void setStrength(final int newStrength) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public long getCreationTime() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public int getId() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public void accept(final ModelVisitor visitor) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
      
      @Override
      public <PLAYERTYPE extends Player> boolean isOwner(final PLAYERTYPE player) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
      }
    };
    IntPoint _boardCoordinate = cell.getBoardCoordinate();
    Hex _hex = new Hex(_boardCoordinate, hexBase);
    Properties _properties = new Properties();
    GraphicsConfigurationData _graphicsConfigurationData = new GraphicsConfigurationData(_properties);
    final FightShape fight = new FightShape(cell, _hex, _graphicsConfigurationData);
    cell.battle = false;
    boolean _hasBattle = cell.hasBattle();
    Assert.assertFalse(_hasBattle);
    boolean _isDrawable = fight.isDrawable();
    Assert.assertFalse(_isDrawable);
    cell.battle = true;
    boolean _hasBattle_1 = cell.hasBattle();
    Assert.assertTrue(_hasBattle_1);
    boolean _isDrawable_1 = fight.isDrawable();
    Assert.assertTrue(_isDrawable_1);
    for (int i = 0; (i < 100); i++) {
      boolean _isDrawable_2 = fight.isDrawable();
      Assert.assertTrue(_isDrawable_2);
    }
    cell.battle = false;
    boolean _hasBattle_2 = cell.hasBattle();
    Assert.assertFalse(_hasBattle_2);
    boolean _isDrawable_2 = fight.isDrawable();
    Assert.assertTrue(_isDrawable_2);
    for (int i = 0; (i < 19); i++) {
      boolean _isDrawable_3 = fight.isDrawable();
      Assert.assertTrue(("Iteration:" + Integer.valueOf(i)), _isDrawable_3);
    }
    boolean _hasBattle_3 = cell.hasBattle();
    Assert.assertFalse(_hasBattle_3);
    boolean _isDrawable_3 = fight.isDrawable();
    Assert.assertFalse(_isDrawable_3);
    cell.battle = true;
    boolean _hasBattle_4 = cell.hasBattle();
    Assert.assertTrue(_hasBattle_4);
    boolean _isDrawable_4 = fight.isDrawable();
    Assert.assertTrue(_isDrawable_4);
    cell.battle = false;
    boolean _hasBattle_5 = cell.hasBattle();
    Assert.assertFalse(_hasBattle_5);
    boolean _isDrawable_5 = fight.isDrawable();
    Assert.assertTrue(_isDrawable_5);
    cell.battle = true;
    boolean _hasBattle_6 = cell.hasBattle();
    Assert.assertTrue(_hasBattle_6);
    boolean _isDrawable_6 = fight.isDrawable();
    Assert.assertTrue(_isDrawable_6);
  }
}
