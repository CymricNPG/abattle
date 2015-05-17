package net.npg.abattle.common.utils

import java.util.List
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.CodeGenerationContext
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.TypeReference
import org.eclipse.xtend.lib.macro.declaration.Visibility

class TransferDataProcessor extends AbstractClassProcessor {

	override doRegisterGlobals(ClassDeclaration annotatedClass, RegisterGlobalsContext context) {
		context.registerClass(annotatedClass.className)
	}

	override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {

		val StringBuilder bodyString = new StringBuilder

		val params = generateConstructor(annotatedClass, bodyString)
		generateBuilder(annotatedClass, context, params)
		generateToString(annotatedClass, context, params)
	}

	private def generateConstructor(MutableClassDeclaration annotatedClass, StringBuilder bodyString) {
		val List<Pair<String, TypeReference>> params = newArrayList

		annotatedClass.addConstructor [
			visibility = Visibility::PUBLIC
			var superClass = annotatedClass  as ClassDeclaration
			var x = 0
			do {
				val fields = superClass.declaredFields
				for (field : fields) {
					val fieldName = field.getSimpleName
					addParameter(fieldName, field.getType)
					params.add(fieldName -> field.getType)
					bodyString.append(
						'''
							this.«fieldName» = «fieldName»;
						''')
				}
				superClass = superClass.extendedClass.type  as ClassDeclaration
				x = x + 1
			} while (x < 5 && superClass != null && superClass.simpleName != "Object")
			body = [bodyString.toString]
		]
		if (bodyString.length != 0) {
			annotatedClass.addConstructor [
				visibility = Visibility::PUBLIC
			]
		}
		params
	}

	private def className(ClassDeclaration annotatedClass) {
		annotatedClass.qualifiedName + "Builder"
	}

	private def generateToString(MutableClassDeclaration annotatedClass, extension TransformationContext context,
		List<Pair<String, TypeReference>> params) {
		val bodyString = new StringBuilder

		bodyString.append("return com.google.common.base.Objects.toStringHelper(this)\n")
		for (Pair<String, TypeReference> entry : params) {
			bodyString.append(".add(\"" + entry.key + "\"," + entry.key + ")\n")
		}
		bodyString.append(".addValue(super.toString())\n")
		bodyString.append(".toString();")
		annotatedClass.addMethod("toString") [
			visibility = Visibility::PUBLIC
			static = false
			returnType = newTypeReference("java.lang.String")
			body = [bodyString.toString]
		]
	}

	private def generateBuilder(MutableClassDeclaration annotatedClass, extension TransformationContext context,
		List<Pair<String, TypeReference>> params) {
		val bodyString = new StringBuilder

		val classType = findClass(annotatedClass.className)
		val classTypeReference = newTypeReference(annotatedClass.className)
		val instanceTypeName = annotatedClass.qualifiedName
		val instanceType = newTypeReference(instanceTypeName)
		var first = true
		for (Pair<String, TypeReference> entry : params) {
			classType.addField(entry.key) [
				type = entry.value
				visibility = Visibility::PRIVATE
			]
			classType.addMethod(entry.key) [
				visibility = Visibility::PUBLIC
				static = false
				returnType = classTypeReference
				addParameter(entry.key, entry.value)
				body = [
					'''
						this.«entry.key»=«entry.key»;
						return this;
'''
				]
			]
			if (!first) {
				bodyString.append(",")

			} else {
				first = false
			}
			bodyString.append(entry.key)

		}

		classType.addMethod("build") [
			visibility = Visibility::PUBLIC
			static = false
			returnType = instanceType
			body = [
				'''
					return new «instanceType»(
					«bodyString.toString»
					);
				'''
			]
		]

	}

	override doGenerateCode(List<? extends ClassDeclaration> annotatedSourceElements,
		extension CodeGenerationContext context) {
	}
}
