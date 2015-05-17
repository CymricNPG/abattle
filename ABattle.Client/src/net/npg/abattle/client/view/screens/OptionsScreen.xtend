package net.npg.abattle.client.view.screens

import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.i18n.I18N
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.server.game.impl.terrain.TerrainCreators
import net.npg.abattle.server.logic.Logics

import static net.npg.abattle.client.view.screens.Layout.*
import static net.npg.abattle.common.utils.Asserts.*
import net.npg.abattle.server.game.impl.fog.Fogs
import net.npg.abattle.client.view.boardscene.debug.DebugCells

class OptionsScreen extends BasicScreen implements ParameterScreen<Screens> {

	OptionTable table

	MyTextButton resetButton

	Screens backScreen

	override create() {
		table = new OptionTable(widgets, stage)
		table.create()
		fillTable

		widgets.addButton(Back, Icons.Back, [|finish])
		resetButton = widgets.addButton(Options, Icons.Reset, [|reset])

	}

	def reset() {
		configuration.reset
		resetButton.checked = false
		fillTable
	}

	private def finish() {
		assertNotNull(backScreen)
		table.finish
		configuration.save
		switcher.switchToScreen(backScreen)
	}

	override render() {
	}

	private def fillTable() {
		table.fillHeader
		val gameConfiguration = configuration.gameConfiguration
		table.addTextField(I18N.get("opt_namefield"), configuration.globalOptions.name, I18N.get("opt_name"), [configuration.globalOptions.name = it])
		table.addDropDown(I18N.get("opt_movement"), Logics.logicMap.names, Logics.logicMap.selectedClass.name, [gameConfiguration.logic = it])

		table.addSlider(I18N.get("opt_levels"), 1, 9, gameConfiguration.maxCellHeight, [gameConfiguration.maxCellHeight = it])
		table.addSlider(I18N.get("opt_towns"), 0, 100, gameConfiguration.randomBases, [gameConfiguration.randomBases = it], 1.0f, "%")
		table.addSlider(I18N.get("opt_win"), 0, 100, gameConfiguration.winCondition, [gameConfiguration.winCondition = it], 1.0f, "%")

		table.addSlider(I18N.get("opt_home"), 100, 1000, gameConfiguration.baseGrowthPerTick, [gameConfiguration.baseGrowthPerTick = it], 50)
		table.addSlider(I18N.get("opt_town"), 0, 1000, gameConfiguration.townGrowthPerTick, [gameConfiguration.townGrowthPerTick = it], 50)

		table.addSlider(I18N.get("opt_moving"), 50, 2000, gameConfiguration.maxMovement, [gameConfiguration.maxMovement = it], 50)
		table.addSlider(I18N.get("opt_terrain"), 1, 100, gameConfiguration.terrainInfluence, [gameConfiguration.terrainInfluence = it], 1.0f, "%")
		table.addDropDown(I18N.get("opt_creator"), TerrainCreators.terrainMap.names, TerrainCreators.terrainMap.selectedClass.name, [
			gameConfiguration.terrainCreator = it
		])
		table.addDropDown(I18N.get("opt_fog"), Fogs.fogList.names, Fogs.fogList.selectedClass.name, [gameConfiguration.fog = it])
		table.addSlider(I18N.get("opt_peaks"), 0, 100, gameConfiguration.peakCount, [gameConfiguration.peakCount = it], 1.0f, "%")
		table.addCheckBox(I18N.get("opt_shade"), configuration.graphicsConfiguration.cellShading, [configuration.graphicsConfiguration.cellShading = it])
		table.addCheckBox(I18N.get("opt_selection"), configuration.globalOptions.coneSelection, [configuration.globalOptions.coneSelection = it])
		table.addDropDown(I18N.get("opt_debugcell"), DebugCells.debugList.names, DebugCells.debugList.selectedClass.name, [
			configuration.graphicsConfiguration.debugCell = it
		])
		table.addSlider(I18N.get("opt_updates"), 5, 25, configuration.gameLoopConfiguration.logicUpdatesPerSecond as int, [
			configuration.gameLoopConfiguration.logicUpdatesPerSecond = it as long
		])
	}

	private def getConfiguration() {
		ComponentLookup.getInstance.getComponent(ConfigurationComponent)
	}

	override backButton() {
		switcher.switchToScreen(Screens.Main)
	}

	override setParameter(Screens backScreen) {
		Validate.notNull(backScreen)
		this.backScreen = backScreen
	}

	override getType() {
		Screens.Options
	}

}
