package net.npg.abattle.common.configuration.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.npg.abattle.common.configuration.impl.NamedClass;
import net.npg.abattle.common.utils.Validate;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class SelectableClassList<T extends NamedClass> {
  private Map<String, T> classMap;
  
  private final Function0<String> getter;
  
  private String defaultName;
  
  public SelectableClassList(final Function0<String> getter, final String defaultName) {
    HashMap<String, T> _newHashMap = CollectionLiterals.<String, T>newHashMap();
    this.classMap = _newHashMap;
    this.getter = getter;
    this.defaultName = defaultName;
  }
  
  public T getSelectedClass() {
    T _xblockexpression = null;
    {
      String _selectedName = this.selectedName();
      final T selectedClass = this.classMap.get(_selectedName);
      Validate.notNull(selectedClass);
      _xblockexpression = selectedClass;
    }
    return _xblockexpression;
  }
  
  private String selectedName() {
    final String name = this.getter.apply();
    boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(name);
    if (_isNullOrEmpty) {
      return this.defaultName;
    } else {
      return name;
    }
  }
  
  public Set<String> getNames() {
    return this.classMap.keySet();
  }
  
  public T addSectableClass(final T selectableClass) {
    String _name = selectableClass.getName();
    return this.classMap.put(_name, selectableClass);
  }
}
