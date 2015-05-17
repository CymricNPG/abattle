/**
 * ABattle, a xbattle conversion for java, Copyright by Roland Spatzenegger (2011-)
 */
package net.npg.abattle.client.view.boardscene;

import java.util.ArrayList;
import java.util.List;

import net.npg.abattle.common.utils.Validate;

/**
 *
 * @author cymric
 *
 */
public class BoardViewModel implements VisitableSceneElement {

	private float height;
	private final List<LayerModel> layers;
	private float width;
	private ErrorMessage errorMessage;

	public BoardViewModel() {
		super();
		this.layers = new ArrayList<LayerModel>();
	}

	@Override
	public void accept(final SceneRenderer visitor) {
		visitor.visit(this);
	}

	public void addLayer(final LayerModel layer) {
		Validate.notNull(layer);
		assert layers != null;
		layers.add(layer);
	}

	public float getHeight() {
		assert height > 0;
		return height;
	}

	public List<LayerModel> getLayers() {
		assert layers != null;
		return layers;
	}

	public float getWidth() {
		assert width > 0;
		return width;
	}

	public void setHeight(final float height) {
		this.height = height;
	}

	public void setWidth(final float width) {
		this.width = width;
	}

	public void addErrorMessage(final ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}
}
