package net.npg.abattle.client.view.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import net.npg.abattle.client.local.GameBaseParametersImpl
import net.npg.abattle.common.CommonConstants
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.configuration.GameConfigurationData
import net.npg.abattle.common.i18n.I18N
import net.npg.abattle.common.utils.Validate
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0

import static net.npg.abattle.client.view.screens.Layout.*

class GameParameterPartScreen {

	private final Widgets widgets

	private final boolean humans

	private final BasicScreen parentScreen

	private OptionTable table

	GameConfigurationData configuration

	new(Widgets widgets, boolean humans, BasicScreen parentScreen ) {
		Validate.notNulls(widgets, parentScreen)
		this.widgets = widgets
		this.configuration = ComponentLookup.getInstance.getComponent(ConfigurationComponent).gameConfiguration
		this.parentScreen = parentScreen
		this.humans = humans
	}

	def create(Procedure0 startProcedure, Stage stage) {
		table = new OptionTable(widgets, stage)
		table.create
		table.fillHeader
		table.addSlider(I18N.get("gp_width"), CommonConstants.MIN_BOARD_SIZE, CommonConstants.MAX_BOARD_SIZE, configuration.xsize, [
			configuration.xsize = it
		])
		table.addSlider(I18N.get("gp_height"), CommonConstants.MIN_BOARD_SIZE, CommonConstants.MAX_BOARD_SIZE, configuration.ysize, [
			configuration.ysize = it
		])
		table.addSlider(I18N.get("gp_nlayers_"+if(humans) "h" else "c"), (if(humans) 2 else 1), CommonConstants.MAX_PLAYERS - 1,
			Math.max((if(humans) 2 else 1), configuration.playerCount), [configuration.playerCount = it]
		)
		widgets.addButton(Options, Icons.Options, [|switchToOptions])
		widgets.addButton(Back, Icons.Back, [|parentScreen.switcher.switchToScreen(Screens.Main)])
		widgets.addButton(Game_Para_START, Icons.Start, startProcedure)
	}

	def switchToOptions() {
		table.finish
		ComponentLookup.getInstance.getComponent(ConfigurationComponent).save
		parentScreen.switcher.switchToScreen(Screens.Options, parentScreen.type)
	}

	def finish() {
		table.finish
	}

	def getGameParameters( ) {
		var name = ComponentLookup.getInstance.getComponent(ConfigurationComponent).globalOptions.name
		if(name.isNullOrEmpty) {
			name = I18N.get("gp_unknown")
		}
		if(humans) {
			return new GameBaseParametersImpl(0, configuration.playerCount, configuration.xsize, configuration.ysize, name)
		} else {
			return new GameBaseParametersImpl(configuration.playerCount, 1, configuration.xsize, configuration.ysize, name)
		}
	}
}
