package net.npg.abattle.common.utils;

@SuppressWarnings("all")
public class IntPoint3 {
  public final int x;
  
  public final int y;
  
  public final int z;
  
  public IntPoint3(final int x, final int y, final int z) {
    super();
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  @Override
  public String toString() {
    String _plus = (Integer.valueOf(this.x) + "/");
    String _plus_1 = (_plus + Integer.valueOf(this.y));
    String _plus_2 = (_plus_1 + "/");
    return (_plus_2 + Integer.valueOf(this.z));
  }
}
