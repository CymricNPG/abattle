/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import java.util.ArrayList;
import java.util.List;

import net.npg.abattle.common.utils.Validate;

/**
 * contains all shapes on the same layer
 * 
 * @author cymric
 * 
 */
public class LayerModel implements VisitableSceneElement {

	private static final int MIN_SIZE_OF_BOARD = 128;
	private final List<Shape> shapes;
	private final boolean staticLayer;

	public LayerModel(final boolean staticLayer) {
		super();
		this.staticLayer = staticLayer;
		this.shapes = new ArrayList<Shape>(MIN_SIZE_OF_BOARD);
	}

	@Override
	public void accept(final SceneRenderer visitor) {
		visitor.visit(this);
	}

	public void addShape(final Shape shape) {
		assert shapes != null;
		Validate.notNull(shape);
		shapes.add(shape);
	}

	public List<Shape> getShapes() {
		assert shapes != null;
		return shapes;
	}

	public boolean isStatic() {
		return staticLayer;
	}
}
