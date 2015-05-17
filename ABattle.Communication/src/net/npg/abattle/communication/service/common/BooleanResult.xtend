package net.npg.abattle.communication.service.common

import net.npg.abattle.common.utils.TransferData

@TransferData
class BooleanResult {   

	/** The error message. */
	public String errorMessage

	/** The success. */
	public boolean success
}
