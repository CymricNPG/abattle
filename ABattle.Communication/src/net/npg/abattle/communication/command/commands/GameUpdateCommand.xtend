package net.npg.abattle.communication.command.commands

import net.npg.abattle.common.utils.TransferData
import net.npg.abattle.communication.command.data.StatisticsData

@TransferData
public class GameUpdateCommand extends CommandImpl {
	public StatisticsData[] statistics
}