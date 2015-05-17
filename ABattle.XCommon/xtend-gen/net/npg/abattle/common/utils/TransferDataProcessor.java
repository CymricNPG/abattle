package net.npg.abattle.common.utils;

import com.google.common.base.Objects;
import java.util.List;
import org.eclipse.xtend.lib.macro.AbstractClassProcessor;
import org.eclipse.xtend.lib.macro.CodeGenerationContext;
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.CompilationStrategy;
import org.eclipse.xtend.lib.macro.declaration.FieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableConstructorDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableFieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.Type;
import org.eclipse.xtend.lib.macro.declaration.TypeReference;
import org.eclipse.xtend.lib.macro.declaration.Visibility;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class TransferDataProcessor extends AbstractClassProcessor {
  @Override
  public void doRegisterGlobals(final ClassDeclaration annotatedClass, final RegisterGlobalsContext context) {
    String _className = this.className(annotatedClass);
    context.registerClass(_className);
  }
  
  @Override
  public void doTransform(final MutableClassDeclaration annotatedClass, @Extension final TransformationContext context) {
    final StringBuilder bodyString = new StringBuilder();
    final List<Pair<String, TypeReference>> params = this.generateConstructor(annotatedClass, bodyString);
    this.generateBuilder(annotatedClass, context, params);
    this.generateToString(annotatedClass, context, params);
  }
  
  private List<Pair<String, TypeReference>> generateConstructor(final MutableClassDeclaration annotatedClass, final StringBuilder bodyString) {
    List<Pair<String, TypeReference>> _xblockexpression = null;
    {
      final List<Pair<String, TypeReference>> params = CollectionLiterals.<Pair<String, TypeReference>>newArrayList();
      final Procedure1<MutableConstructorDeclaration> _function = new Procedure1<MutableConstructorDeclaration>() {
        @Override
        public void apply(final MutableConstructorDeclaration it) {
          it.setVisibility(Visibility.PUBLIC);
          ClassDeclaration superClass = ((ClassDeclaration) annotatedClass);
          int x = 0;
          do {
            {
              final Iterable<? extends FieldDeclaration> fields = superClass.getDeclaredFields();
              for (final FieldDeclaration field : fields) {
                {
                  final String fieldName = field.getSimpleName();
                  TypeReference _type = field.getType();
                  it.addParameter(fieldName, _type);
                  TypeReference _type_1 = field.getType();
                  Pair<String, TypeReference> _mappedTo = Pair.<String, TypeReference>of(fieldName, _type_1);
                  params.add(_mappedTo);
                  StringConcatenation _builder = new StringConcatenation();
                  _builder.append("this.");
                  _builder.append(fieldName, "");
                  _builder.append(" = ");
                  _builder.append(fieldName, "");
                  _builder.append(";");
                  _builder.newLineIfNotEmpty();
                  bodyString.append(_builder);
                }
              }
              TypeReference _extendedClass = superClass.getExtendedClass();
              Type _type = _extendedClass.getType();
              superClass = ((ClassDeclaration) _type);
              x = (x + 1);
            }
          } while((((x < 5) && (!Objects.equal(superClass, null))) && (!Objects.equal(superClass.getSimpleName(), "Object"))));
          final CompilationStrategy _function = new CompilationStrategy() {
            @Override
            public CharSequence compile(final CompilationStrategy.CompilationContext it) {
              return bodyString.toString();
            }
          };
          it.setBody(_function);
        }
      };
      annotatedClass.addConstructor(_function);
      int _length = bodyString.length();
      boolean _notEquals = (_length != 0);
      if (_notEquals) {
        final Procedure1<MutableConstructorDeclaration> _function_1 = new Procedure1<MutableConstructorDeclaration>() {
          @Override
          public void apply(final MutableConstructorDeclaration it) {
            it.setVisibility(Visibility.PUBLIC);
          }
        };
        annotatedClass.addConstructor(_function_1);
      }
      _xblockexpression = params;
    }
    return _xblockexpression;
  }
  
  private String className(final ClassDeclaration annotatedClass) {
    String _qualifiedName = annotatedClass.getQualifiedName();
    return (_qualifiedName + "Builder");
  }
  
  private MutableMethodDeclaration generateToString(final MutableClassDeclaration annotatedClass, @Extension final TransformationContext context, final List<Pair<String, TypeReference>> params) {
    MutableMethodDeclaration _xblockexpression = null;
    {
      final StringBuilder bodyString = new StringBuilder();
      bodyString.append("return com.google.common.base.Objects.toStringHelper(this)\n");
      for (final Pair<String, TypeReference> entry : params) {
        String _key = entry.getKey();
        String _plus = (".add(\"" + _key);
        String _plus_1 = (_plus + "\",");
        String _key_1 = entry.getKey();
        String _plus_2 = (_plus_1 + _key_1);
        String _plus_3 = (_plus_2 + ")\n");
        bodyString.append(_plus_3);
      }
      bodyString.append(".addValue(super.toString())\n");
      bodyString.append(".toString();");
      final Procedure1<MutableMethodDeclaration> _function = new Procedure1<MutableMethodDeclaration>() {
        @Override
        public void apply(final MutableMethodDeclaration it) {
          it.setVisibility(Visibility.PUBLIC);
          it.setStatic(false);
          TypeReference _newTypeReference = context.newTypeReference("java.lang.String");
          it.setReturnType(_newTypeReference);
          final CompilationStrategy _function = new CompilationStrategy() {
            @Override
            public CharSequence compile(final CompilationStrategy.CompilationContext it) {
              return bodyString.toString();
            }
          };
          it.setBody(_function);
        }
      };
      _xblockexpression = annotatedClass.addMethod("toString", _function);
    }
    return _xblockexpression;
  }
  
  private MutableMethodDeclaration generateBuilder(final MutableClassDeclaration annotatedClass, @Extension final TransformationContext context, final List<Pair<String, TypeReference>> params) {
    MutableMethodDeclaration _xblockexpression = null;
    {
      final StringBuilder bodyString = new StringBuilder();
      String _className = this.className(annotatedClass);
      final MutableClassDeclaration classType = context.findClass(_className);
      String _className_1 = this.className(annotatedClass);
      final TypeReference classTypeReference = context.newTypeReference(_className_1);
      final String instanceTypeName = annotatedClass.getQualifiedName();
      final TypeReference instanceType = context.newTypeReference(instanceTypeName);
      boolean first = true;
      for (final Pair<String, TypeReference> entry : params) {
        {
          String _key = entry.getKey();
          final Procedure1<MutableFieldDeclaration> _function = new Procedure1<MutableFieldDeclaration>() {
            @Override
            public void apply(final MutableFieldDeclaration it) {
              TypeReference _value = entry.getValue();
              it.setType(_value);
              it.setVisibility(Visibility.PRIVATE);
            }
          };
          classType.addField(_key, _function);
          String _key_1 = entry.getKey();
          final Procedure1<MutableMethodDeclaration> _function_1 = new Procedure1<MutableMethodDeclaration>() {
            @Override
            public void apply(final MutableMethodDeclaration it) {
              it.setVisibility(Visibility.PUBLIC);
              it.setStatic(false);
              it.setReturnType(classTypeReference);
              String _key = entry.getKey();
              TypeReference _value = entry.getValue();
              it.addParameter(_key, _value);
              final CompilationStrategy _function = new CompilationStrategy() {
                @Override
                public CharSequence compile(final CompilationStrategy.CompilationContext it) {
                  StringConcatenation _builder = new StringConcatenation();
                  _builder.append("this.");
                  String _key = entry.getKey();
                  _builder.append(_key, "");
                  _builder.append("=");
                  String _key_1 = entry.getKey();
                  _builder.append(_key_1, "");
                  _builder.append(";");
                  _builder.newLineIfNotEmpty();
                  _builder.append("return this;");
                  _builder.newLine();
                  return _builder;
                }
              };
              it.setBody(_function);
            }
          };
          classType.addMethod(_key_1, _function_1);
          if ((!first)) {
            bodyString.append(",");
          } else {
            first = false;
          }
          String _key_2 = entry.getKey();
          bodyString.append(_key_2);
        }
      }
      final Procedure1<MutableMethodDeclaration> _function = new Procedure1<MutableMethodDeclaration>() {
        @Override
        public void apply(final MutableMethodDeclaration it) {
          it.setVisibility(Visibility.PUBLIC);
          it.setStatic(false);
          it.setReturnType(instanceType);
          final CompilationStrategy _function = new CompilationStrategy() {
            @Override
            public CharSequence compile(final CompilationStrategy.CompilationContext it) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append("return new ");
              _builder.append(instanceType, "");
              _builder.append("(");
              _builder.newLineIfNotEmpty();
              String _string = bodyString.toString();
              _builder.append(_string, "");
              _builder.newLineIfNotEmpty();
              _builder.append(");");
              _builder.newLine();
              return _builder;
            }
          };
          it.setBody(_function);
        }
      };
      _xblockexpression = classType.addMethod("build", _function);
    }
    return _xblockexpression;
  }
  
  @Override
  public void doGenerateCode(final List<? extends ClassDeclaration> annotatedSourceElements, @Extension final CodeGenerationContext context) {
  }
}
