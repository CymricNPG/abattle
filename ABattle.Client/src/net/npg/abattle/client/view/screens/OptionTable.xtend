package net.npg.abattle.client.view.screens

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import java.util.Collection
import java.util.Set
import net.npg.abattle.common.i18n.I18N
import net.npg.abattle.common.utils.Validate
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1

import static net.npg.abattle.client.view.screens.Layout.*
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.utils.Align

class OptionTable {

	private Table table

	final static float CELLPAD = 4

	private Set<Procedures.Procedure0> updateProcedures

	private final Widgets widgets

	private final Stage stage

	new(Widgets widgets, Stage stage) {
		Validate.notNulls(widgets, stage)
		this.widgets = widgets
		this.stage = stage
	}

	def create() {
		updateProcedures = newHashSet
		table = new Table
		table.fillParent = false
		val scrollpane = new ScrollPane(table, widgets.skin)
		scrollpane.setSize(OPTION_SIZE.x, OPTION_SIZE.y)
		scrollpane.setPosition(OPTION_SCROLL.x, OPTION_SCROLL.y)
		scrollpane.cancelTouchFocus = false
		scrollpane.fadeScrollBars = false
		stage.addActor(scrollpane)
	}

	def finish() {
		updateProcedures.forEach[it.apply]
	}

	def void fillHeader() {
		updateProcedures.clear
		table.clear
	}

	def addTextField(String label, String text, String defaultText, Procedure1<String> update) {
		table.add(widgets.createLabel(label)).defaultCellOptions
		table.row
		val textfield = widgets.createTextfield(if(text.nullOrEmpty) defaultText else text)
		table.add(textfield).defaultCellOptions.width(250)
		table.row
		updateProcedures.add([if(textfield.text != defaultText) update.apply(textfield.text)])
		textfield.addListener(
			new ClickListener {
				override touchDown(InputEvent event, float x, float y, int pointer, int button) {
					val ret = super.touchDown(event, x, y, pointer, button)
					if(ret && textfield.text == defaultText) {
						textfield.text = ""
					}
					return ret
				}
			}
		)
	}

	def addSlider(String label, int min, int max, int selected, Procedure1<Integer> update, float step) {
		addSlider(label, min, max, selected, update, step, "")
	}

	def addSlider(String label, int min, int max, int selected, Procedure1<Integer> update, float step, String textSuffix) {
		table.add(widgets.createLabel(label)).defaultCellOptions
		table.row
		val slider = widgets.addSliderWithoutLabel(min, max, Game_Para_X.x, Game_Para_X.y, step)
		table.add(slider).defaultCellOptions.width(250)
		slider.value = selected
		val labelWidget = widgets.addLabel(0, 0, selected.toString + textSuffix)
		slider.addListener(new MyChangeListener([labelWidget.text = "" + slider.value.intValue + textSuffix]))
		table.add(labelWidget).defaultCellOptions
		table.row
		updateProcedures.add([update.apply(slider.value.intValue)])
	}

	def addSlider(String label, int min, int max, int selected, Procedure1<Integer> update) {
		addSlider(label, min, max, selected, update, 1.0f)
	}

	def addDropDown(String label, Collection<String> items, String selected, Procedure1<String> update) {
		table.add(widgets.createLabel(label)).defaultCellOptions
		table.row
		val i18nItems = items.map[I18N.get(it)].toList
		val dropDown = widgets.createDropDown(I18N.get(selected), i18nItems)
		table.add(dropDown).defaultCellOptions
		table.row
		updateProcedures.add([update.apply( items.findFirst[I18N.get(it).equals(dropDown.selected)])])
	}

	def addCheckBox(String label, boolean checked, Procedure1<Boolean> update) {
		table.add(widgets.createLabel(label)).defaultCellOptions
		val box = widgets.createCheckBox()
		box.checked = checked
		table.add(box).defaultCellOptions
		table.row
		updateProcedures.add([update.apply(box.checked)])
	}

	private def  Cell defaultCellOptions(Cell cell) {
		cell.pad(CELLPAD).align(Align.left)
	}

}
