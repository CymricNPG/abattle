package net.npg.abattle.communication.service.common;

import com.google.common.base.Objects;
import net.npg.abattle.common.utils.IntPoint;
import net.npg.abattle.common.utils.TransferData;

/**
 * a transferable intpoint
 */
@TransferData
@SuppressWarnings("all")
public class MutableIntPoint {
  public int x;
  
  public int y;
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = ((prime * result) + this.x);
    result = ((prime * result) + this.y);
    return result;
  }
  
  @Override
  public boolean equals(final Object obj) {
    boolean _equals = Objects.equal(this, obj);
    if (_equals) {
      return true;
    }
    boolean _equals_1 = Objects.equal(obj, null);
    if (_equals_1) {
      return false;
    }
    if ((!(obj instanceof MutableIntPoint))) {
      return false;
    }
    final MutableIntPoint other = ((MutableIntPoint) obj);
    if ((this.x != other.x)) {
      return false;
    }
    if ((this.y != other.y)) {
      return false;
    }
    return true;
  }
  
  public static MutableIntPoint from(final int x, final int y) {
    final MutableIntPoint point = new MutableIntPoint();
    point.x = x;
    point.y = y;
    return point;
  }
  
  public static MutableIntPoint from(final IntPoint ipoint) {
    final MutableIntPoint point = new MutableIntPoint();
    point.x = ipoint.x;
    point.y = ipoint.y;
    return point;
  }
  
  public IntPoint to() {
    return IntPoint.from(this.x, this.y);
  }
  
  public MutableIntPoint(final int x, final int y) {
    this.x = x;
    this.y = y;
  }
  
  public MutableIntPoint() {
  }
  
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
    .add("x",x)
    .add("y",y)
    .addValue(super.toString())
    .toString();
  }
}
