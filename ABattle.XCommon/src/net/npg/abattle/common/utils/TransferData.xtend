package net.npg.abattle.common.utils

import org.eclipse.xtend.lib.macro.Active
import java.lang.annotation.Target
import java.lang.annotation.ElementType

@Active(typeof(net.npg.abattle.common.utils.TransferDataProcessor))
@Target(ElementType.TYPE)
annotation TransferData {
	
}