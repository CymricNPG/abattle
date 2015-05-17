package net.npg.abattle.common.utils;

import com.google.common.base.Objects;
import java.util.List;
import net.npg.abattle.common.utils.NotNull;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.TransformationParticipant;
import org.eclipse.xtend.lib.macro.declaration.AnnotationReference;
import org.eclipse.xtend.lib.macro.declaration.MutableExecutableDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableParameterDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableTypeDeclaration;
import org.eclipse.xtend.lib.macro.declaration.Type;
import org.eclipse.xtend.lib.macro.declaration.TypeReference;
import org.eclipse.xtend.lib.macro.expression.Expression;
import org.eclipse.xtend2.lib.StringConcatenationClient;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class NotNullProcessor implements TransformationParticipant<MutableParameterDeclaration> {
  @Override
  public void doTransform(final List<? extends MutableParameterDeclaration> annotatedParameters, @Extension final TransformationContext context) {
    for (final MutableParameterDeclaration annotatedParameter : annotatedParameters) {
      this.doTransform(annotatedParameter, context);
    }
  }
  
  private void doTransform(final MutableParameterDeclaration annotatedParameter, @Extension final TransformationContext context) {
    MutableExecutableDeclaration _declaringExecutable = annotatedParameter.getDeclaringExecutable();
    final MutableMethodDeclaration declaringMethod = ((MutableMethodDeclaration) _declaringExecutable);
    String _simpleName = declaringMethod.getSimpleName();
    final String oldMethodName = (_simpleName + "_");
    final Expression oldBody = declaringMethod.getBody();
    final TypeReference oldReturnType = declaringMethod.getReturnType();
    final Iterable<? extends MutableParameterDeclaration> oldParameters = declaringMethod.getParameters();
    MutableTypeDeclaration _declaringType = declaringMethod.getDeclaringType();
    Iterable<? extends MutableMethodDeclaration> _declaredMethods = _declaringType.getDeclaredMethods();
    final Function1<MutableMethodDeclaration, Boolean> _function = new Function1<MutableMethodDeclaration, Boolean>() {
      @Override
      public Boolean apply(final MutableMethodDeclaration it) {
        String _simpleName = it.getSimpleName();
        return Boolean.valueOf(Objects.equal(_simpleName, oldMethodName));
      }
    };
    MutableMethodDeclaration _findFirst = IterableExtensions.findFirst(_declaredMethods, _function);
    boolean _equals = Objects.equal(_findFirst, null);
    if (_equals) {
      MutableTypeDeclaration _declaringType_1 = declaringMethod.getDeclaringType();
      final Procedure1<MutableMethodDeclaration> _function_1 = new Procedure1<MutableMethodDeclaration>() {
        @Override
        public void apply(final MutableMethodDeclaration it) {
          it.setReturnType(oldReturnType);
          for (final MutableParameterDeclaration parameter : oldParameters) {
            String _simpleName = parameter.getSimpleName();
            TypeReference _type = parameter.getType();
            it.addParameter(_simpleName, _type);
          }
          it.setBody(oldBody);
        }
      };
      _declaringType_1.addMethod(oldMethodName, _function_1);
    }
    String _xifexpression = null;
    boolean _isVoid = oldReturnType.isVoid();
    if (_isVoid) {
      _xifexpression = "";
    } else {
      _xifexpression = "return";
    }
    final String returnString = _xifexpression;
    final Function1<MutableParameterDeclaration, Boolean> _function_2 = new Function1<MutableParameterDeclaration, Boolean>() {
      @Override
      public Boolean apply(final MutableParameterDeclaration it) {
        Type _findTypeGlobally = context.findTypeGlobally(NotNull.class);
        AnnotationReference _findAnnotation = it.findAnnotation(_findTypeGlobally);
        return Boolean.valueOf((!Objects.equal(_findAnnotation, null)));
      }
    };
    final Iterable<? extends MutableParameterDeclaration> annotatedParameters = IterableExtensions.filter(oldParameters, _function_2);
    StringConcatenationClient _client = new StringConcatenationClient() {
      @Override
      protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
        {
          for(final MutableParameterDeclaration parameter : annotatedParameters) {
            _builder.append(" net.npg.abattle.common.utils.MyValidate.notNull( ");
            String _simpleName = parameter.getSimpleName();
            _builder.append(_simpleName, "");
            _builder.append(");");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t\t");
        _builder.append(returnString, "\t\t");
        _builder.append(" ");
        _builder.append(oldMethodName, "\t\t");
        _builder.append("(");
        final Function1<MutableParameterDeclaration, String> _function = new Function1<MutableParameterDeclaration, String>() {
          @Override
          public String apply(final MutableParameterDeclaration it) {
            return it.getSimpleName();
          }
        };
        Iterable<String> _map = IterableExtensions.map(oldParameters, _function);
        String _join = IterableExtensions.join(_map, ",");
        _builder.append(_join, "\t\t");
        _builder.append(");");
      }
    };
    declaringMethod.setBody(_client);
  }
}
