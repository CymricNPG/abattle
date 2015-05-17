package net.npg.abattle.common.component

import org.eclipse.xtend.lib.macro.Active
import java.lang.annotation.Target
import java.lang.annotation.ElementType

/**
 * annotated classes get the getInterface-Method
 */
@Active(typeof(ComponentTypeProcessor))
@Target(ElementType.TYPE)
annotation ComponentType {
	
}