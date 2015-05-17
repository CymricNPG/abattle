/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import net.npg.abattle.client.view.renderer.Renderer;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.configuration.ConfigurationComponent;
import net.npg.abattle.common.hex.Hex;
import net.npg.abattle.common.utils.FloatPoint;
import net.npg.abattle.common.utils.MutableFloatPoint3;
import net.npg.abattle.common.utils.Validate;

import com.badlogic.gdx.graphics.Color;

/**
 * @author cymric
 *
 */
public class SceneRendererImpl implements SceneRenderer {

	private final Renderer renderer;
	private final boolean cellShading;

	public SceneRendererImpl(final Renderer renderer) {
		Validate.notNull(renderer);
		this.renderer = renderer;
		cellShading = ComponentLookup.getInstance().getComponent(ConfigurationComponent.class).getGraphicsConfiguration().isCellShading();
	}

	public void begin() {
		renderer.begin();
	}

	public void end() {
		renderer.end();
	}

	@Override
	public void visit(final BoardViewModel board) {
		Validate.notNull(board);
		begin();
		for (final LayerModel layer : board.getLayers()) {
			layer.accept(this);
		}
		board.getErrorMessage().accept(this);
		end();
	}

	@Override
	public void visit(final CellTextShape cellTextShape) {
		renderer.drawText(cellTextShape.getText(), cellTextShape.getHex().getCenter(), Color.WHITE);
	}

	@Override
	public void visit(final CircleShape circleShape) {
		if (circleShape.isIrregular()) {
			renderer.drawUnCircle(circleShape.getHex().getCenter(), circleShape.getRadius(), Color.BLACK);
		} else {
			renderer.drawCircle(circleShape.getHex().getCenter(), circleShape.getRadius(), Color.BLACK, circleShape.isBold());
		}
	}

	@Override
	public void visit(final CursorHexShape cursorHexShape) {
		final Hex hex = cursorHexShape.getHex();
		final int count = RendererUtils.getAnimationNumber(200, 255);
		final Color color = new Color(0.5f + count / 512f, 0, 0, 0.6f);
		renderer.drawFilledPoly(hex.getCenter(), hex.computeCorners(), color);
	}

	@Override
	public void visit(final FightShape fightShape) {
		fightShape.grow();
		final float centerRadius = fightShape.getHex().getRadius();
		for (final MutableFloatPoint3 bomb : fightShape.getBombs()) {
			final float radius = centerRadius * (20 - bomb.z) / 100;
			final FloatPoint position = new FloatPoint(bomb.x, bomb.y);
			renderer.drawFilledCircle(position, radius, Color.BLACK, 10);
		}
	}

	@Override
	public void visit(final FilledCircleShape filledCircleShape) {
		if (filledCircleShape.isFilled()) {
			renderer.drawFilledCircle(filledCircleShape.getHex().getCenter(), filledCircleShape.getRadius(), filledCircleShape.getColor(), 20);
		} else {
			renderer.drawCircle(filledCircleShape.getHex().getCenter(), filledCircleShape.getRadius(), filledCircleShape.getColor(), false);
		}
	}

	@Override
	public void visit(final FilledHexShape filledHexShape) {
		final Hex hex = filledHexShape.getHex();
		final Color color = filledHexShape.getColor();

		final Color[] cornerColors = filledHexShape.getCornerColors();
		if (cellShading) {
			renderer.drawFilledPoly(hex.getCenter(), hex.computeCorners(), color, cornerColors);
		} else {
			renderer.drawFilledPoly(hex.getCenter(), hex.computeCorners(), color);
		}
	}

	@Override
	public void visit(final HexShape hexShape) {
		final Hex hex = hexShape.getHex();
		renderer.drawPoly(hex.computeCorners(), Color.WHITE);
	}

	@Override
	public void visit(final LayerModel layer) {
		for (final Shape shape : layer.getShapes()) {
			if (shape.isDrawable()) {
				shape.accept(this);
			}
		}
	}

	@Override
	public void visit(final OutgoingLinkShape outgoingLinkShape) {
		final FloatPoint start = outgoingLinkShape.getStartCoordinate();
		final FloatPoint end = outgoingLinkShape.getEndCoordinate();
		renderer.drawArrow(start, end, outgoingLinkShape.getColor());
	}

	@Override
	public void visit(final ComplexBarShape complexBarShape) {
		DrawComplexBarShape.draw(renderer, complexBarShape);
	}

	@Override
	public void visit(final ErrorMessage errorMessage) {
		if (errorMessage.isVisible()) {
			renderer.drawText(errorMessage.getErrorMessage(), errorMessage.getPos(), Color.WHITE);
		}
	}
}
