package net.npg.abattle.common.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructorProcessor;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.TransformationParticipant;
import org.eclipse.xtend.lib.macro.declaration.AnnotationReference;
import org.eclipse.xtend.lib.macro.declaration.AnnotationTarget;
import org.eclipse.xtend.lib.macro.declaration.Element;
import org.eclipse.xtend.lib.macro.declaration.EnumerationValueDeclaration;
import org.eclipse.xtend.lib.macro.declaration.FieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableFieldDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableMemberDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableParameterDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableTypeDeclaration;
import org.eclipse.xtend.lib.macro.declaration.Type;
import org.eclipse.xtend.lib.macro.declaration.TypeDeclaration;
import org.eclipse.xtend.lib.macro.declaration.TypeReference;
import org.eclipse.xtend.lib.macro.declaration.Visibility;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtend2.lib.StringConcatenationClient;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.StringExtensions;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;

/**
 * @since 2.7
 */
@SuppressWarnings("all")
public class DirtyAccessorsProcessor implements TransformationParticipant<MutableMemberDeclaration> {
	/**
	 * @since 2.7
	 */
	@Beta
	public static class Util {
		@Extension
		private TransformationContext context;

		public Util(final TransformationContext context) {
			this.context = context;
		}

		public Visibility toVisibility(final AccessorType type) {
			Visibility _switchResult = null;
			if (type != null) {
				switch (type) {
				case PUBLIC_GETTER:
					_switchResult = Visibility.PUBLIC;
					break;
				case PROTECTED_GETTER:
					_switchResult = Visibility.PROTECTED;
					break;
				case PACKAGE_GETTER:
					_switchResult = Visibility.DEFAULT;
					break;
				case PRIVATE_GETTER:
					_switchResult = Visibility.PRIVATE;
					break;
				case PUBLIC_SETTER:
					_switchResult = Visibility.PUBLIC;
					break;
				case PROTECTED_SETTER:
					_switchResult = Visibility.PROTECTED;
					break;
				case PACKAGE_SETTER:
					_switchResult = Visibility.DEFAULT;
					break;
				case PRIVATE_SETTER:
					_switchResult = Visibility.PRIVATE;
					break;
				default:
					final StringConcatenation _builder = new StringConcatenation();
					_builder.append("Cannot convert ");
					_builder.append(type, "");
					throw new IllegalArgumentException(_builder.toString());
				}
			} else {
				final StringConcatenation _builder = new StringConcatenation();
				_builder.append("Cannot convert ");
				_builder.append(type, "");
				throw new IllegalArgumentException(_builder.toString());
			}
			return _switchResult;
		}

		public boolean hasGetter(final FieldDeclaration it) {
			final List<String> _possibleGetterNames = this.getPossibleGetterNames(it);
			final Function1<String, Boolean> _function = new Function1<String, Boolean>() {
				@Override
				public Boolean apply(final String name) {
					final TypeDeclaration _declaringType = it.getDeclaringType();
					final MethodDeclaration _findDeclaredMethod = _declaringType.findDeclaredMethod(name);
					return Boolean.valueOf((_findDeclaredMethod != null));
				}
			};
			return IterableExtensions.<String> exists(_possibleGetterNames, _function);
		}

		public boolean shouldAddGetter(final FieldDeclaration it) {
			boolean _and = false;
			final boolean _hasGetter = this.hasGetter(it);
			final boolean _not = (!_hasGetter);
			if (!_not) {
				_and = false;
			} else {
				final AccessorType _getterType = this.getGetterType(it);
				final boolean _tripleNotEquals = (_getterType != AccessorType.NONE);
				_and = _tripleNotEquals;
			}
			return _and;
		}

		public AccessorType getGetterType(final FieldDeclaration it) {
			AnnotationReference _elvis = null;
			final AnnotationReference _accessorsAnnotation = this.getAccessorsAnnotation(it);
			if (_accessorsAnnotation != null) {
				_elvis = _accessorsAnnotation;
			} else {
				final TypeDeclaration _declaringType = it.getDeclaringType();
				final AnnotationReference _accessorsAnnotation_1 = this.getAccessorsAnnotation(_declaringType);
				_elvis = _accessorsAnnotation_1;
			}
			final AnnotationReference annotation = _elvis;
			if ((annotation != null)) {
				final EnumerationValueDeclaration[] _enumArrayValue = annotation.getEnumArrayValue("value");
				final Function1<EnumerationValueDeclaration, AccessorType> _function = new Function1<EnumerationValueDeclaration, AccessorType>() {
					@Override
					public AccessorType apply(final EnumerationValueDeclaration it) {
						final String _simpleName = it.getSimpleName();
						return AccessorType.valueOf(_simpleName);
					}
				};
				final List<AccessorType> types = ListExtensions.<EnumerationValueDeclaration, AccessorType> map(
						((List<EnumerationValueDeclaration>) Conversions.doWrapArray(_enumArrayValue)), _function);
				AccessorType _elvis_1 = null;
				final Function1<AccessorType, Boolean> _function_1 = new Function1<AccessorType, Boolean>() {
					@Override
					public Boolean apply(final AccessorType it) {
						final String _name = it.name();
						return Boolean.valueOf(_name.endsWith("GETTER"));
					}
				};
				final AccessorType _findFirst = IterableExtensions.<AccessorType> findFirst(types, _function_1);
				if (_findFirst != null) {
					_elvis_1 = _findFirst;
				} else {
					_elvis_1 = AccessorType.NONE;
				}
				return _elvis_1;
			}
			return null;
		}

		public AnnotationReference getAccessorsAnnotation(final AnnotationTarget it) {
			final Type _findTypeGlobally = this.context.findTypeGlobally(DirtyAccessors.class);
			return it.findAnnotation(_findTypeGlobally);
		}

		public Object validateGetter(final MutableFieldDeclaration field) {
			return null;
		}

		public String getGetterName(final FieldDeclaration it) {
			final List<String> _possibleGetterNames = this.getPossibleGetterNames(it);
			return IterableExtensions.<String> head(_possibleGetterNames);
		}

		public List<String> getPossibleGetterNames(final FieldDeclaration it) {
			List<String> _xifexpression = null;
			final TypeReference _type = it.getType();
			final TypeReference _orObject = this.orObject(_type);
			final boolean _isBooleanType = this.isBooleanType(_orObject);
			if (_isBooleanType) {
				_xifexpression = Collections.<String> unmodifiableList(CollectionLiterals.<String> newArrayList("is", "get"));
			} else {
				_xifexpression = Collections.<String> unmodifiableList(CollectionLiterals.<String> newArrayList("get"));
			}
			final Function1<String, String> _function = new Function1<String, String>() {
				@Override
				public String apply(final String prefix) {
					final String _simpleName = it.getSimpleName();
					final String _firstUpper = StringExtensions.toFirstUpper(_simpleName);
					return (prefix + _firstUpper);
				}
			};
			return ListExtensions.<String, String> map(_xifexpression, _function);
		}

		public boolean isBooleanType(final TypeReference it) {
			boolean _and = false;
			final boolean _isInferred = it.isInferred();
			final boolean _not = (!_isInferred);
			if (!_not) {
				_and = false;
			} else {
				final TypeReference _primitiveBoolean = this.context.getPrimitiveBoolean();
				final boolean _equals = Objects.equal(it, _primitiveBoolean);
				_and = _equals;
			}
			return _and;
		}

		public void addGetter(final MutableFieldDeclaration field, final Visibility visibility) {
			this.validateGetter(field);
			field.markAsRead();
			final MutableTypeDeclaration _declaringType = field.getDeclaringType();
			final String _getterName = this.getGetterName(field);
			final Procedure1<MutableMethodDeclaration> _function = new Procedure1<MutableMethodDeclaration>() {
				@Override
				public void apply(final MutableMethodDeclaration it) {
					final Element _primarySourceElement = Util.this.context.getPrimarySourceElement(field);
					Util.this.context.setPrimarySourceElement(it, _primarySourceElement);
					final AnnotationReference _newAnnotationReference = Util.this.context.newAnnotationReference(Pure.class);
					it.addAnnotation(_newAnnotationReference);
					final TypeReference _type = field.getType();
					final TypeReference _orObject = Util.this.orObject(_type);
					it.setReturnType(_orObject);
					final StringConcatenationClient _client = new StringConcatenationClient() {
						@Override
						protected void appendTo(final StringConcatenationClient.TargetStringConcatenation _builder) {
							_builder.append("return ");
							final Object _fieldOwner = Util.this.fieldOwner(field);
							_builder.append(_fieldOwner, "");
							_builder.append(".");
							final String _simpleName = field.getSimpleName();
							_builder.append(_simpleName, "");
							_builder.append(";");
						}
					};
					it.setBody(_client);
					final boolean _isStatic = field.isStatic();
					it.setStatic(_isStatic);
					it.setVisibility(visibility);
				}
			};
			_declaringType.addMethod(_getterName, _function);
		}

		public AccessorType getSetterType(final FieldDeclaration it) {
			AnnotationReference _elvis = null;
			final AnnotationReference _accessorsAnnotation = this.getAccessorsAnnotation(it);
			if (_accessorsAnnotation != null) {
				_elvis = _accessorsAnnotation;
			} else {
				final TypeDeclaration _declaringType = it.getDeclaringType();
				final AnnotationReference _accessorsAnnotation_1 = this.getAccessorsAnnotation(_declaringType);
				_elvis = _accessorsAnnotation_1;
			}
			final AnnotationReference annotation = _elvis;
			if ((annotation != null)) {
				final EnumerationValueDeclaration[] _enumArrayValue = annotation.getEnumArrayValue("value");
				final Function1<EnumerationValueDeclaration, AccessorType> _function = new Function1<EnumerationValueDeclaration, AccessorType>() {
					@Override
					public AccessorType apply(final EnumerationValueDeclaration it) {
						final String _simpleName = it.getSimpleName();
						return AccessorType.valueOf(_simpleName);
					}
				};
				final List<AccessorType> types = ListExtensions.<EnumerationValueDeclaration, AccessorType> map(
						((List<EnumerationValueDeclaration>) Conversions.doWrapArray(_enumArrayValue)), _function);
				AccessorType _elvis_1 = null;
				final Function1<AccessorType, Boolean> _function_1 = new Function1<AccessorType, Boolean>() {
					@Override
					public Boolean apply(final AccessorType it) {
						final String _name = it.name();
						return Boolean.valueOf(_name.endsWith("SETTER"));
					}
				};
				final AccessorType _findFirst = IterableExtensions.<AccessorType> findFirst(types, _function_1);
				if (_findFirst != null) {
					_elvis_1 = _findFirst;
				} else {
					_elvis_1 = AccessorType.NONE;
				}
				return _elvis_1;
			}
			return null;
		}

		private Object fieldOwner(final MutableFieldDeclaration it) {
			Object _xifexpression = null;
			final boolean _isStatic = it.isStatic();
			if (_isStatic) {
				final MutableTypeDeclaration _declaringType = it.getDeclaringType();
				_xifexpression = this.context.newTypeReference(_declaringType);
			} else {
				_xifexpression = "this";
			}
			return _xifexpression;
		}

		public boolean hasSetter(final FieldDeclaration it) {
			final TypeDeclaration _declaringType = it.getDeclaringType();
			final String _setterName = this.getSetterName(it);
			final TypeReference _type = it.getType();
			final TypeReference _orObject = this.orObject(_type);
			final MethodDeclaration _findDeclaredMethod = _declaringType.findDeclaredMethod(_setterName, _orObject);
			return (_findDeclaredMethod != null);
		}

		public String getSetterName(final FieldDeclaration it) {
			final String _simpleName = it.getSimpleName();
			final String _firstUpper = StringExtensions.toFirstUpper(_simpleName);
			return ("set" + _firstUpper);
		}

		public boolean shouldAddSetter(final FieldDeclaration it) {
			boolean _and = false;
			boolean _and_1 = false;
			final boolean _isFinal = it.isFinal();
			final boolean _not = (!_isFinal);
			if (!_not) {
				_and_1 = false;
			} else {
				final boolean _hasSetter = this.hasSetter(it);
				final boolean _not_1 = (!_hasSetter);
				_and_1 = _not_1;
			}
			if (!_and_1) {
				_and = false;
			} else {
				final AccessorType _setterType = this.getSetterType(it);
				final boolean _tripleNotEquals = (_setterType != AccessorType.NONE);
				_and = _tripleNotEquals;
			}
			return _and;
		}

		public void validateSetter(final MutableFieldDeclaration field) {
			final boolean _isFinal = field.isFinal();
			if (_isFinal) {
				this.context.addError(field, "Cannot set a final field");
			}
			boolean _or = false;
			final TypeReference _type = field.getType();
			final boolean _tripleEquals = (_type == null);
			if (_tripleEquals) {
				_or = true;
			} else {
				final TypeReference _type_1 = field.getType();
				final boolean _isInferred = _type_1.isInferred();
				_or = _isInferred;
			}
			if (_or) {
				this.context.addError(field, "Type cannot be inferred.");
				return;
			}
		}

		public void addSetter(final MutableFieldDeclaration field, final Visibility visibility) {
			this.validateSetter(field);
			final MutableTypeDeclaration _declaringType = field.getDeclaringType();
			final String _setterName = this.getSetterName(field);
			final Procedure1<MutableMethodDeclaration> _function = new Procedure1<MutableMethodDeclaration>() {
				@Override
				public void apply(final MutableMethodDeclaration it) {
					final Element _primarySourceElement = Util.this.context.getPrimarySourceElement(field);
					Util.this.context.setPrimarySourceElement(it, _primarySourceElement);
					final TypeReference _primitiveVoid = Util.this.context.getPrimitiveVoid();
					it.setReturnType(_primitiveVoid);
					final String _simpleName = field.getSimpleName();
					final TypeReference _type = field.getType();
					final TypeReference _orObject = Util.this.orObject(_type);
					final MutableParameterDeclaration param = it.addParameter(_simpleName, _orObject);
					final StringConcatenationClient _client = new StringConcatenationClient() {
						@Override
						protected void appendTo(final StringConcatenationClient.TargetStringConcatenation _builder) {
							final Object _fieldOwner = Util.this.fieldOwner(field);
							final String _simpleName = field.getSimpleName();
							final String _simpleName_1 = param.getSimpleName();
							if (field.getType().isPrimitive()) {
								_builder.append("if(" +_fieldOwner+"."+ _simpleName + "==" + _simpleName_1 + ") return;\n");
							} else {
								_builder.append("if(" +_fieldOwner+"."+ _simpleName + ".equals(" + _simpleName_1 + ")) return;\n");
							}
							_builder.append("this.dirty = true;\n");
							_builder.append(_fieldOwner, "");
							_builder.append(".");
							_builder.append(_simpleName, "");
							_builder.append(" = ");
							_builder.append(_simpleName_1, "");
							_builder.append(";");
						}
					};
					it.setBody(_client);
					final boolean _isStatic = field.isStatic();
					it.setStatic(_isStatic);
					it.setVisibility(visibility);
				}
			};
			_declaringType.addMethod(_setterName, _function);
		}

		private TypeReference orObject(final TypeReference ref) {
			TypeReference _xifexpression = null;
			if ((ref == null)) {
				_xifexpression = this.context.getObject();
			} else {
				_xifexpression = ref;
			}
			return _xifexpression;
		}
	}

	@Override
	public void doTransform(final List<? extends MutableMemberDeclaration> elements, @Extension final TransformationContext context) {
		final Procedure1<MutableMemberDeclaration> _function = new Procedure1<MutableMemberDeclaration>() {
			@Override
			public void apply(final MutableMemberDeclaration it) {
				DirtyAccessorsProcessor.this.transform(it, context);
			}
		};
		IterableExtensions.forEach(elements, _function);
	}

	protected void _transform(final MutableFieldDeclaration it, @Extension final TransformationContext context) {
		@Extension
		final DirtyAccessorsProcessor.Util util = new DirtyAccessorsProcessor.Util(context);
		final boolean _shouldAddGetter = util.shouldAddGetter(it);
		if (_shouldAddGetter) {
			final AccessorType _getterType = util.getGetterType(it);
			final Visibility _visibility = util.toVisibility(_getterType);
			util.addGetter(it, _visibility);
		}
		final boolean _shouldAddSetter = util.shouldAddSetter(it);
		if (_shouldAddSetter) {
			final AccessorType _setterType = util.getSetterType(it);
			final Visibility _visibility_1 = util.toVisibility(_setterType);
			util.addSetter(it, _visibility_1);
		}
	}

	protected void _transform(final MutableClassDeclaration it, @Extension final TransformationContext context) {
		final Type _findTypeGlobally = context.findTypeGlobally(Data.class);
		final AnnotationReference _findAnnotation = it.findAnnotation(_findTypeGlobally);
		final boolean _tripleNotEquals = (_findAnnotation != null);
		if (_tripleNotEquals) {
			return;
		}
		@Extension
		final FinalFieldsConstructorProcessor.Util requiredArgsUtil = new FinalFieldsConstructorProcessor.Util(context);
		boolean _or = false;
		final boolean _needsFinalFieldConstructor = requiredArgsUtil.needsFinalFieldConstructor(it);
		if (_needsFinalFieldConstructor) {
			_or = true;
		} else {
			final Type _findTypeGlobally_1 = context.findTypeGlobally(FinalFieldsConstructor.class);
			final AnnotationReference _findAnnotation_1 = it.findAnnotation(_findTypeGlobally_1);
			final boolean _tripleNotEquals_1 = (_findAnnotation_1 != null);
			_or = _tripleNotEquals_1;
		}
		if (_or) {
			requiredArgsUtil.addFinalFieldsConstructor(it);
		}
		final Iterable<? extends MutableFieldDeclaration> _declaredFields = it.getDeclaredFields();
		final Function1<MutableFieldDeclaration, Boolean> _function = new Function1<MutableFieldDeclaration, Boolean>() {
			@Override
			public Boolean apply(final MutableFieldDeclaration it) {
				boolean _and = false;
				final boolean _isStatic = it.isStatic();
				final boolean _not = (!_isStatic);
				if (!_not) {
					_and = false;
				} else {
					final boolean _isThePrimaryGeneratedJavaElement = context.isThePrimaryGeneratedJavaElement(it);
					_and = _isThePrimaryGeneratedJavaElement;
				}
				return Boolean.valueOf(_and);
			}
		};
		final Iterable<? extends MutableFieldDeclaration> _filter = IterableExtensions.filter(_declaredFields, _function);
		final Procedure1<MutableFieldDeclaration> _function_1 = new Procedure1<MutableFieldDeclaration>() {
			@Override
			public void apply(final MutableFieldDeclaration it) {
				DirtyAccessorsProcessor.this._transform(it, context);
			}
		};
		IterableExtensions.forEach(_filter, _function_1);
	}

	public void transform(final MutableMemberDeclaration it, final TransformationContext context) {
		if (it instanceof MutableClassDeclaration) {
			_transform((MutableClassDeclaration) it, context);
			return;
		} else if (it instanceof MutableFieldDeclaration) {
			_transform((MutableFieldDeclaration) it, context);
			return;
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " + Arrays.<Object> asList(it, context).toString());
		}
	}
}
