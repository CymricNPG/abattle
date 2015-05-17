package net.npg.abattle.client.view.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox
import com.badlogic.gdx.scenes.scene2d.ui.Slider
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import java.util.Collection
import java.util.List
import net.npg.abattle.client.asset.AssetManager
import net.npg.abattle.common.component.ComponentLookup
import static net.npg.abattle.common.utils.Controls.*
import net.npg.abattle.common.i18n.I18N

class Widgets {

	private final Stage stage

	private final List<TextButton> buttons

	private AssetManager manager

	private boolean hidden = true

	private String defaultHelpText

	private Label helpLabel

	new(Stage stage) {
		this.stage = stage
		buttons = newArrayList
		manager = ComponentLookup.getInstance.getComponent(AssetManager)

	}

	def setHelp( Label helpLabel, String defaultHelpText) {
		this.helpLabel = helpLabel
		this.defaultHelpText = defaultHelpText
	}

	def addLabel(float x, float y, String message) {
		val label = createLabel(message)
		label.setPosition(x, y)
		stage.addActor(label)
		label
	}

	def addImage(float x, float y, Icons icon) {
		val image = createImage(icon)
		image.setPosition(x, y)
		stage.addActor(image)
	}

	def addButton(Layout position, Icons icon, Procedures.Procedure0 changeProcedure) {
		val button = createButton(icon, [|if(!hidden) changeProcedure.apply])
		button.setPosition(position.x, position.y)
		button.addHoverListener([changeText(it, icon, helpLabel, defaultHelpText)])
		stage.addActor(button)
		buttons.add(button)
		button
	}

	private def changeText(Boolean over, Icons icon, Label helpLabel, String defaultText) {
		returnif(helpLabel == null)
		if(over) {
			helpLabel.text = I18N.get("menu_" + icon)
		} else {
			helpLabel.text = defaultText
		}
	}

	def resetButtons() {
		buttons.forEach[checked = false]
	}

	def addSliderWithoutLabel(int min, int max, float x, float y) {
		addSliderWithoutLabel( min,  max,  x,  y,  1.0f) 
	}

	def addSliderWithoutLabel(int min, int max, float x, float y, float step) {
		val slider = new Slider(min, max, step, false, manager.skin)
		slider.setPosition(x, y)
		slider.setValue(min)
		slider.width = 200
		stage.addActor(slider)
		slider
	}

	def addSlider(int min, int max, float x, float y) {
		val slider = addSliderWithoutLabel(min, max, x, y)
		val label = addLabel(x + 150, y + 5, "" + min)
		slider.addListener(new MyChangeListener([label.text = "" + slider.value.intValue]))
		slider
	}

	def show() {
		resetButtons
		hidden = false;
	}

	def hide() {
		hidden = true
	}

	def createButton(Icons icon) {
		createButton(icon, 1.0f, null, false)
	}

	def createButton(Icons icon, Procedures.Procedure0 buttonPressed) {
		createButton(icon, 1.0f, buttonPressed, false)
	}

	def createButton(Icons icon, Procedures.Procedure0 buttonPressed, boolean disabled) {
		createButton(icon, 1.0f, buttonPressed, disabled)
	}

	def createButton(Icons icon, float scale) {
		createButton(icon, scale, null, false)
	}

	def createTextfield(String text) {
		val textfield = new TextField(text, manager.skin)
		textfield.maxLength = 16
		textfield.selectAll
		textfield
	}

	def createButton(Icons icon, float scale, Procedures.Procedure0 buttonPressed, boolean disabled) {
		val texture = manager.getTexture(icon.filename)
		val textButtonStyle = new TextButton.TextButtonStyle()
		textButtonStyle.up = buildSprite(texture, Color.LIGHT_GRAY)

		if(!disabled) {
			textButtonStyle.down = buildSprite(texture, Color.WHITE)
			textButtonStyle.over = buildSprite(texture, Color.WHITE)
			textButtonStyle.checked = buildSprite(texture, Color.BLUE)
		} else {
			textButtonStyle.down = buildSprite(texture, Color.LIGHT_GRAY)
			textButtonStyle.over = buildSprite(texture, Color.LIGHT_GRAY)
			textButtonStyle.checked = buildSprite(texture, Color.LIGHT_GRAY)
		}

		textButtonStyle.font = manager.normalFont
		val button = new MyTextButton("", textButtonStyle)
		if(buttonPressed != null && !disabled) {
			button.addListener(new MyChangeListener(buttonPressed))
		}

		// button.setSize(button.getPrefWidth()*scale, button.getPrefHeight()*scale);
		button.name = icon.toString
		button
	}

	def buildSprite(Texture texture, Color tint) {
		val sprite = new Sprite(texture)
		sprite.setColor(tint)
		new SpriteDrawable(sprite)
	}

	def createImage(Icons icon) {
		manager.getImage(icon.filename)
	}

	def createLabel(String message) {
		val labelStyle = new Label.LabelStyle(manager.smallFont, new Color(1.0f, 1.0f, 1.0f, 1.0f))
		new Label(message, labelStyle)
	}

	def createLabel(String message, Color color) {
		val labelStyle = new Label.LabelStyle(manager.smallFont, color)
		new Label(message, labelStyle)
	}

	def createCheckBox() {
		new CheckBox("", manager.skin)
	}

	def createDropDown(String selected, Collection<String> items) {
		val selectBox = new SelectBox<String>(manager.skin)
		selectBox.setItems(items)
		selectBox.selected = selected
		return selectBox
	}

	def getSkin() {
		manager.skin
	}

}
