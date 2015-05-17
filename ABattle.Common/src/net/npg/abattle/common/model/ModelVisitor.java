/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.common.model;

/**
 * @author spatzenegger
 * 
 */
public interface ModelVisitor {

	@SuppressWarnings("rawtypes")
	public void visit(Board board);

	public void visit(Cell cell);

	@SuppressWarnings("rawtypes")
	public void visit(Game game);

	@SuppressWarnings("rawtypes")
	public void visit(Link link);

	public void visit(Player player);

	public void visit(GameConfiguration configuration);

}
