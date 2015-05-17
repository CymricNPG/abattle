package net.npg.abattle.common.utils

import java.util.List
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.TransformationParticipant
import org.eclipse.xtend.lib.macro.declaration.MutableParameterDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration

class NotNullProcessor implements TransformationParticipant<MutableParameterDeclaration>{


	override doTransform(List<? extends MutableParameterDeclaration> annotatedParameters, @Extension TransformationContext context) {
		for (MutableParameterDeclaration annotatedParameter : annotatedParameters) {
			doTransform(annotatedParameter, context);
		}
	}

	private def doTransform(MutableParameterDeclaration annotatedParameter, @Extension TransformationContext context) {
		val declaringMethod = (annotatedParameter.declaringExecutable as MutableMethodDeclaration)
		val oldMethodName = declaringMethod.simpleName+"_"
		val oldBody =  declaringMethod.body
		val oldReturnType =  declaringMethod.returnType
		val oldParameters =	declaringMethod.parameters
		if(declaringMethod.declaringType.declaredMethods.findFirst[it.simpleName == oldMethodName] == null) {
			declaringMethod.declaringType.addMethod(oldMethodName) [
				returnType = oldReturnType
				for(parameter : oldParameters) addParameter(parameter.simpleName, parameter.type)
				body = oldBody
			]
		}
		val returnString = if(oldReturnType.void) "" else "return"
		val annotatedParameters = oldParameters.filter[it.findAnnotation(findTypeGlobally(typeof(NotNull)))!=null]
		declaringMethod.body = '''«FOR parameter : annotatedParameters» net.npg.abattle.common.utils.MyValidate.notNull( «parameter.simpleName»);
		«ENDFOR»
		«returnString» «oldMethodName»(«oldParameters.map[it.simpleName].join(',')»);''';
	}


}