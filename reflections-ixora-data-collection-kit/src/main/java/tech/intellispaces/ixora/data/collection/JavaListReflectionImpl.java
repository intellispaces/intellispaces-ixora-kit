package tech.intellispaces.ixora.data.collection;

import java.util.Iterator;
import java.util.List;

import tech.intellispaces.commons.type.Type;
import tech.intellispaces.commons.type.Types;
import tech.intellispaces.reflections.framework.annotation.Mapper;
import tech.intellispaces.reflections.framework.annotation.Reflection;
import tech.intellispaces.reflections.framework.reflection.NativeReflection;

@Reflection(ListDomain.class)
abstract class JavaListReflectionImpl<E> implements
    UnmovableList<E>,
    UnmovableListReflection<E>,
    NativeReflection<ListDomain<E>>
{
  private final List<E> list;
  private final Type<E> elementType;

  JavaListReflectionImpl(List<E> list, Class<E> elementClass) {
    this.list = java.util.Collections.unmodifiableList(list);
    this.elementType = Types.get(elementClass);
  }

  JavaListReflectionImpl(List<E> list, Type<E> elementType) {
    this.list = java.util.Collections.unmodifiableList(list);
    this.elementType = elementType;
  }

  @Override
  public List<E> boundObject() {
    return list;
  }

  @Mapper
  @Override
  public UnmovableCollectionReflection<E> asCollection() {
    return Collections.reflectionOf(list, elementType);
  }

  @Mapper
  @Override
  public Type<E> elementDomain() {
    return elementType;
  }

  @Mapper
  @Override
  public E get(int index) {
    return list.get(index);
  }

  @Mapper
  @Override
  public int size() {
    return list.size();
  }

  @Override
  public Iterator<E> iterator() {
    return list.iterator();
  }
}
