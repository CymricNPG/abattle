package net.npg.abattle.common.configuration.impl

import java.util.Map
import net.npg.abattle.common.utils.Validate
import org.eclipse.xtext.xbase.lib.Functions.Function0

class SelectableClassList<T extends NamedClass> {
	private Map<String, T> classMap

	private final Function0<String> getter

	String defaultName

	new( Function0<String> getter, String defaultName) {
		classMap = newHashMap
		this.getter = getter
		this.defaultName = defaultName
	}

	def getSelectedClass() {
		val selectedClass = classMap.get(selectedName)
		Validate.notNull(selectedClass)
		selectedClass
	}

	private def selectedName() {
		val name = getter.apply()
		if (name.isNullOrEmpty) {
			return defaultName
		} else {
			return name
		}
	}

	def getNames() {
		classMap.keySet
	}

	def addSectableClass(T selectableClass) {
		classMap.put(selectableClass.name, selectableClass)
	}
}
