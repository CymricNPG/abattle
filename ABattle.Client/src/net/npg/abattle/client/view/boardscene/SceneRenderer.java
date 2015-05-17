/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

/**
 * @author cymric
 *
 */
public interface SceneRenderer {

	public void visit(final ErrorMessage errorMessage);

	public void visit(final BoardViewModel board);

	public void visit(CellTextShape cellTextShape);

	public void visit(final CircleShape circleShape);

	public void visit(CursorHexShape cursorHexShape);

	public void visit(final FightShape fightShape);

	public void visit(final FilledCircleShape filledCircleShape);

	public void visit(final FilledHexShape filledHexShape);

	public void visit(final HexShape hexShape);

	public void visit(final LayerModel layer);

	public void visit(OutgoingLinkShape outgoingLinkShape);

	public void visit(final ComplexBarShape complexBarShape);

}
