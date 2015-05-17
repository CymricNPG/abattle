package net.npg.abattle.common.configuration

import java.util.List
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.CodeGenerationContext
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.Visibility
import java.util.Map
import java.util.Properties
import com.badlogic.gdx.Preferences
import org.eclipse.xtend.lib.macro.declaration.TypeReference

class PropertyStoredProcessor extends AbstractClassProcessor {

	override doRegisterGlobals(ClassDeclaration annotatedClass, RegisterGlobalsContext context) {
	}

	override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {

		val annotationDefinition = findTypeGlobally(typeof(PropertyName))
		val fields = annotatedClass.declaredFields.filter[it.findAnnotation(annotationDefinition) != null]
		val StringBuilder bodyString = new StringBuilder
		val StringBuilder saveString = new StringBuilder
		val StringBuilder resetString = new StringBuilder
		for (field : fields) {
			val annotation = field.findAnnotation(annotationDefinition)
			val name = annotation.getStringValue("name")
			val defaultValue = annotation.getStringValue("defaultValue")
			val noReset = annotation.getBooleanValue("noReset")
			val fieldName = field.getSimpleName
			val fieldType = field.getType.getSimpleName
			bodyString.
				append( '''
					«fieldName» = net.npg.abattle.common.configuration.Helper.get«fieldType»(properties,"«name»","«defaultValue»");
				''')
			saveString.append('''   map.put("«name»",«fieldName»);
			''')
			if (!noReset) {
				resetString.append('''
					«fieldName» = net.npg.abattle.common.configuration.Helper.get«fieldType»("«defaultValue»");
				''')
			}
		}
		val resetDirty  = "dirty = false;\n"
		bodyString.append(resetDirty)
		saveString.append(resetDirty)
		resetString.append(resetDirty)

		annotatedClass.addConstructor [
			visibility = Visibility::PUBLIC
			addParameter('properties', typeof(Preferences).newTypeReference)
			body = [bodyString.toString]
		]
		annotatedClass.addConstructor [
			visibility = Visibility::PUBLIC
		]
		annotatedClass.addConstructor [
			visibility = Visibility::PUBLIC
			addParameter('properties', typeof(Properties).newTypeReference)
			body = [bodyString.toString]
		]
		annotatedClass.addMethod("save", [
			visibility = Visibility::PUBLIC
			addParameter("map", typeof(Map).newTypeReference())
			body = [saveString.toString]
		])

		annotatedClass.addMethod("reset", [
			visibility = Visibility::PUBLIC
			body = [resetString.toString]
		])

		annotatedClass.addField("dirty", [
			visibility = Visibility::PRIVATE
			type = primitiveBoolean
		])

		annotatedClass.addMethod("isDirty", [
			visibility = Visibility::PUBLIC
			returnType = primitiveBoolean
			body = ["return dirty;"]
		])
	}

	override doGenerateCode(List<? extends ClassDeclaration> annotatedSourceElements,
		extension CodeGenerationContext context) {
	}
}
