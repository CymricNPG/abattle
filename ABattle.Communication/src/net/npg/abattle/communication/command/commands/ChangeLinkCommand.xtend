package net.npg.abattle.communication.command.commands

import net.npg.abattle.communication.service.common.MutableIntPoint
import net.npg.abattle.common.utils.TransferData

@TransferData
public  class ChangeLinkCommand extends OriginatedPlayerCommand {

	public MutableIntPoint endCell

	public MutableIntPoint startCell

	public boolean create
}
