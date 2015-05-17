package net.npg.abattle.client.local;

import net.npg.abattle.client.GameBaseParameters;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@Data
@SuppressWarnings("all")
public class GameBaseParametersImpl implements GameBaseParameters {
  private final int AIPlayers;
  
  private final int humanPlayers;
  
  private final int xSize;
  
  private final int ySize;
  
  private final String name;
  
  public GameBaseParametersImpl(final int AIPlayers, final int humanPlayers, final int xSize, final int ySize, final String name) {
    super();
    this.AIPlayers = AIPlayers;
    this.humanPlayers = humanPlayers;
    this.xSize = xSize;
    this.ySize = ySize;
    this.name = name;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.AIPlayers;
    result = prime * result + this.humanPlayers;
    result = prime * result + this.xSize;
    result = prime * result + this.ySize;
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    return result;
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    GameBaseParametersImpl other = (GameBaseParametersImpl) obj;
    if (other.AIPlayers != this.AIPlayers)
      return false;
    if (other.humanPlayers != this.humanPlayers)
      return false;
    if (other.xSize != this.xSize)
      return false;
    if (other.ySize != this.ySize)
      return false;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("AIPlayers", this.AIPlayers);
    b.add("humanPlayers", this.humanPlayers);
    b.add("xSize", this.xSize);
    b.add("ySize", this.ySize);
    b.add("name", this.name);
    return b.toString();
  }
  
  @Pure
  public int getAIPlayers() {
    return this.AIPlayers;
  }
  
  @Pure
  public int getHumanPlayers() {
    return this.humanPlayers;
  }
  
  @Pure
  public int getXSize() {
    return this.xSize;
  }
  
  @Pure
  public int getYSize() {
    return this.ySize;
  }
  
  @Pure
  public String getName() {
    return this.name;
  }
}
