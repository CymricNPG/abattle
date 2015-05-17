package net.npg.abattle.common.component;

import com.google.common.base.Objects;
import java.util.List;
import org.eclipse.xtend.lib.macro.AbstractClassProcessor;
import org.eclipse.xtend.lib.macro.CodeGenerationContext;
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.CompilationStrategy;
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.TypeReference;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class ComponentTypeProcessor extends AbstractClassProcessor {
  @Override
  public void doRegisterGlobals(final ClassDeclaration annotatedClass, final RegisterGlobalsContext context) {
  }
  
  @Override
  public void doTransform(final MutableClassDeclaration annotatedClass, @Extension final TransformationContext context) {
    Iterable<? extends TypeReference> _implementedInterfaces = annotatedClass.getImplementedInterfaces();
    final Function1<TypeReference, Boolean> _function = new Function1<TypeReference, Boolean>() {
      @Override
      public Boolean apply(final TypeReference it) {
        return Boolean.valueOf(true);
      }
    };
    final TypeReference interfaceType = IterableExtensions.findFirst(_implementedInterfaces, _function);
    boolean _equals = Objects.equal(interfaceType, null);
    if (_equals) {
      Iterable<? extends TypeReference> _implementedInterfaces_1 = annotatedClass.getImplementedInterfaces();
      String _join = IterableExtensions.join(_implementedInterfaces_1, ";");
      String _plus = ("Cannot find interface in:" + _join);
      context.addError(annotatedClass, _plus);
      return;
    }
    final Procedure1<MutableMethodDeclaration> _function_1 = new Procedure1<MutableMethodDeclaration>() {
      @Override
      public void apply(final MutableMethodDeclaration it) {
        TypeReference _newTypeReference = context.newTypeReference(Class.class, interfaceType);
        it.setReturnType(_newTypeReference);
        final CompilationStrategy _function = new CompilationStrategy() {
          @Override
          public CharSequence compile(final CompilationStrategy.CompilationContext it) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("return ");
            _builder.append(interfaceType, "");
            _builder.append(".class;");
            return _builder;
          }
        };
        it.setBody(_function);
      }
    };
    annotatedClass.addMethod("getInterface", _function_1);
  }
  
  @Override
  public void doGenerateCode(final List<? extends ClassDeclaration> annotatedSourceElements, @Extension final CodeGenerationContext context) {
    for (final ClassDeclaration clazz : annotatedSourceElements) {
    }
  }
}
