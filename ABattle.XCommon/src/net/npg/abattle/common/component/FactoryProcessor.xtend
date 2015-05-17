package net.npg.abattle.common.component

import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.CodeGenerationContext
import java.util.List
import org.eclipse.xtend.lib.macro.declaration.Visibility

class FactoryProcessor extends AbstractClassProcessor {

	override doRegisterGlobals(ClassDeclaration annotatedClass, RegisterGlobalsContext context) {
		context.registerClass(annotatedClass.className)
	}

	def className(ClassDeclaration annotatedClass) {
		annotatedClass.qualifiedName.replace("Impl", "Factory")
	}

	override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {
		val classType = findClass(annotatedClass.className)
		val instanceTypeName = classType.qualifiedName.replace("Factory", "Impl")
		val instanceType = findClass(instanceTypeName)
		val interfaceType = instanceType.implementedInterfaces.findFirst[true]
		if (interfaceType == null) {
			instanceType.addError("Cannot find interface in:" + instanceType.implementedInterfaces.join(";"))
			return
		}
		classType.addMethod("create") [
			visibility = Visibility::PUBLIC
			static = true
			returnType = interfaceType
			body = [
				'''
					«interfaceType.name»  instance = new «instanceTypeName»();
					return net.npg.abattle.common.component.ComponentLookup.getInstance().registerComponent(instance);
				                               '''
			]
		]
	}

	override doGenerateCode(List<? extends ClassDeclaration> annotatedSourceElements,
		extension CodeGenerationContext context) {
	}
}
