package net.npg.abattle.common.configuration;

import com.badlogic.gdx.Preferences;
import com.google.common.base.Objects;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import net.npg.abattle.common.configuration.PropertyName;
import org.eclipse.xtend.lib.macro.AbstractClassProcessor;
import org.eclipse.xtend.lib.macro.CodeGenerationContext;
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.declaration.AnnotationReference;
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.CompilationStrategy;
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableConstructorDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableFieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.Type;
import org.eclipse.xtend.lib.macro.declaration.TypeReference;
import org.eclipse.xtend.lib.macro.declaration.Visibility;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class PropertyStoredProcessor extends AbstractClassProcessor {
  @Override
  public void doRegisterGlobals(final ClassDeclaration annotatedClass, final RegisterGlobalsContext context) {
  }
  
  @Override
  public void doTransform(final MutableClassDeclaration annotatedClass, @Extension final TransformationContext context) {
    final Type annotationDefinition = context.findTypeGlobally(PropertyName.class);
    Iterable<? extends MutableFieldDeclaration> _declaredFields = annotatedClass.getDeclaredFields();
    final Function1<MutableFieldDeclaration, Boolean> _function = new Function1<MutableFieldDeclaration, Boolean>() {
      @Override
      public Boolean apply(final MutableFieldDeclaration it) {
        AnnotationReference _findAnnotation = it.findAnnotation(annotationDefinition);
        return Boolean.valueOf((!Objects.equal(_findAnnotation, null)));
      }
    };
    final Iterable<? extends MutableFieldDeclaration> fields = IterableExtensions.filter(_declaredFields, _function);
    final StringBuilder bodyString = new StringBuilder();
    final StringBuilder saveString = new StringBuilder();
    final StringBuilder resetString = new StringBuilder();
    for (final MutableFieldDeclaration field : fields) {
      {
        final AnnotationReference annotation = field.findAnnotation(annotationDefinition);
        final String name = annotation.getStringValue("name");
        final String defaultValue = annotation.getStringValue("defaultValue");
        final boolean noReset = annotation.getBooleanValue("noReset");
        final String fieldName = field.getSimpleName();
        TypeReference _type = field.getType();
        final String fieldType = _type.getSimpleName();
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(fieldName, "");
        _builder.append(" = net.npg.abattle.common.configuration.Helper.get");
        _builder.append(fieldType, "");
        _builder.append("(properties,\"");
        _builder.append(name, "");
        _builder.append("\",\"");
        _builder.append(defaultValue, "");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
        bodyString.append(_builder);
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("   ");
        _builder_1.append("map.put(\"");
        _builder_1.append(name, "   ");
        _builder_1.append("\",");
        _builder_1.append(fieldName, "   ");
        _builder_1.append(");");
        _builder_1.newLineIfNotEmpty();
        saveString.append(_builder_1);
        if ((!noReset)) {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append(fieldName, "");
          _builder_2.append(" = net.npg.abattle.common.configuration.Helper.get");
          _builder_2.append(fieldType, "");
          _builder_2.append("(\"");
          _builder_2.append(defaultValue, "");
          _builder_2.append("\");");
          _builder_2.newLineIfNotEmpty();
          resetString.append(_builder_2);
        }
      }
    }
    final String resetDirty = "dirty = false;\n";
    bodyString.append(resetDirty);
    saveString.append(resetDirty);
    resetString.append(resetDirty);
    final Procedure1<MutableConstructorDeclaration> _function_1 = new Procedure1<MutableConstructorDeclaration>() {
      @Override
      public void apply(final MutableConstructorDeclaration it) {
        it.setVisibility(Visibility.PUBLIC);
        TypeReference _newTypeReference = context.newTypeReference(Preferences.class);
        it.addParameter("properties", _newTypeReference);
        final CompilationStrategy _function = new CompilationStrategy() {
          @Override
          public CharSequence compile(final CompilationStrategy.CompilationContext it) {
            return bodyString.toString();
          }
        };
        it.setBody(_function);
      }
    };
    annotatedClass.addConstructor(_function_1);
    final Procedure1<MutableConstructorDeclaration> _function_2 = new Procedure1<MutableConstructorDeclaration>() {
      @Override
      public void apply(final MutableConstructorDeclaration it) {
        it.setVisibility(Visibility.PUBLIC);
      }
    };
    annotatedClass.addConstructor(_function_2);
    final Procedure1<MutableConstructorDeclaration> _function_3 = new Procedure1<MutableConstructorDeclaration>() {
      @Override
      public void apply(final MutableConstructorDeclaration it) {
        it.setVisibility(Visibility.PUBLIC);
        TypeReference _newTypeReference = context.newTypeReference(Properties.class);
        it.addParameter("properties", _newTypeReference);
        final CompilationStrategy _function = new CompilationStrategy() {
          @Override
          public CharSequence compile(final CompilationStrategy.CompilationContext it) {
            return bodyString.toString();
          }
        };
        it.setBody(_function);
      }
    };
    annotatedClass.addConstructor(_function_3);
    final Procedure1<MutableMethodDeclaration> _function_4 = new Procedure1<MutableMethodDeclaration>() {
      @Override
      public void apply(final MutableMethodDeclaration it) {
        it.setVisibility(Visibility.PUBLIC);
        TypeReference _newTypeReference = context.newTypeReference(Map.class);
        it.addParameter("map", _newTypeReference);
        final CompilationStrategy _function = new CompilationStrategy() {
          @Override
          public CharSequence compile(final CompilationStrategy.CompilationContext it) {
            return saveString.toString();
          }
        };
        it.setBody(_function);
      }
    };
    annotatedClass.addMethod("save", _function_4);
    final Procedure1<MutableMethodDeclaration> _function_5 = new Procedure1<MutableMethodDeclaration>() {
      @Override
      public void apply(final MutableMethodDeclaration it) {
        it.setVisibility(Visibility.PUBLIC);
        final CompilationStrategy _function = new CompilationStrategy() {
          @Override
          public CharSequence compile(final CompilationStrategy.CompilationContext it) {
            return resetString.toString();
          }
        };
        it.setBody(_function);
      }
    };
    annotatedClass.addMethod("reset", _function_5);
    final Procedure1<MutableFieldDeclaration> _function_6 = new Procedure1<MutableFieldDeclaration>() {
      @Override
      public void apply(final MutableFieldDeclaration it) {
        it.setVisibility(Visibility.PRIVATE);
        TypeReference _primitiveBoolean = context.getPrimitiveBoolean();
        it.setType(_primitiveBoolean);
      }
    };
    annotatedClass.addField("dirty", _function_6);
    final Procedure1<MutableMethodDeclaration> _function_7 = new Procedure1<MutableMethodDeclaration>() {
      @Override
      public void apply(final MutableMethodDeclaration it) {
        it.setVisibility(Visibility.PUBLIC);
        TypeReference _primitiveBoolean = context.getPrimitiveBoolean();
        it.setReturnType(_primitiveBoolean);
        final CompilationStrategy _function = new CompilationStrategy() {
          @Override
          public CharSequence compile(final CompilationStrategy.CompilationContext it) {
            return "return dirty;";
          }
        };
        it.setBody(_function);
      }
    };
    annotatedClass.addMethod("isDirty", _function_7);
  }
  
  @Override
  public void doGenerateCode(final List<? extends ClassDeclaration> annotatedSourceElements, @Extension final CodeGenerationContext context) {
  }
}
