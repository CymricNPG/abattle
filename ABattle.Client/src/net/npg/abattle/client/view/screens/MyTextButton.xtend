package net.npg.abattle.client.view.screens

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1

class MyTextButton extends TextButton {
	private static MyTextButton lastOver = null

	private Procedure1<Boolean> hoverProcedure

	new(String text, TextButtonStyle style) {
		super(text, style)
	}

	override void draw(Batch batch, float parentAlpha) {
		if (over && this != lastOver && hoverProcedure != null) {
			hoverProcedure.apply(true);
			lastOver = this
		} else if(!over && this == lastOver && hoverProcedure != null) {
			hoverProcedure.apply(false);
			lastOver = null
		}
		super.draw(batch, parentAlpha)
	}

	def void addHoverListener(Procedures.Procedure1<Boolean> hoverProcedure) {
		this.hoverProcedure = hoverProcedure
	}

}
