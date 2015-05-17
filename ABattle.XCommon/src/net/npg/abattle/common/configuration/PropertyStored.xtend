package net.npg.abattle.common.configuration

import org.eclipse.xtend.lib.macro.Active
import java.lang.annotation.Target
import java.lang.annotation.ElementType

@Active(typeof(PropertyStoredProcessor))
@Target(ElementType.TYPE)
annotation PropertyStored {
	
}