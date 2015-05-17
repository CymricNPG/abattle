package net.npg.abattle.common.component

import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.TransformationContext
import java.util.List
import org.eclipse.xtend.lib.macro.CodeGenerationContext

class ComponentTypeProcessor extends AbstractClassProcessor {

	override doRegisterGlobals(ClassDeclaration annotatedClass, RegisterGlobalsContext context) {
		//context.registerClass(annotatedClass.className)
	}

	override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {
		val interfaceType = annotatedClass.implementedInterfaces.findFirst[true]
		if( interfaceType == null) {
			annotatedClass.addError("Cannot find interface in:"+annotatedClass.implementedInterfaces.join(";"))
			return
		}
		annotatedClass.addMethod('getInterface') [
			returnType = newTypeReference(Class, interfaceType)
			body = ['''return «interfaceType».class;''']
		]
	}

	override doGenerateCode(List<? extends ClassDeclaration> annotatedSourceElements,
		extension CodeGenerationContext context) {
		for (clazz : annotatedSourceElements) {
		}
	}
}
