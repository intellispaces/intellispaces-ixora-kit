package tech.intellispaces.ixora.data.collection;

import java.util.Iterator;
import java.util.List;

import tech.intellispaces.commons.collection.ArraysFunctions;
import tech.intellispaces.commons.type.Type;
import tech.intellispaces.commons.type.Types;
import tech.intellispaces.reflections.framework.annotation.Mapper;
import tech.intellispaces.reflections.framework.annotation.Reflection;

@Reflection(ByteListDomain.class)
abstract class ByteArrayReflectionImpl implements UnmovableByteListReflection {
  private final Type<Byte> elementType = Types.get(Byte.class);
  private final byte[] array;
  private List<Byte> list;

  ByteArrayReflectionImpl(byte[] array) {
    this.array = array;
  }

  ByteArrayReflectionImpl(List<Byte> list) {
    this.array = ArraysFunctions.toByteArray(list);
    this.list = list;
  }

  public byte[] array() {
    return array;
  }

  @Mapper
  @Override
  public UnmovableCollectionReflection<Byte> asCollection() {
    return new JavaCollectionReflectionImplWrapper<>(list(), elementType);
  }

  @Mapper
  @Override
  public Type<Byte> elementDomain() {
    return elementType;
  }

  @Mapper
  @Override
  public Byte get(int index) {
    return getElement(index);
  }

  @Mapper
  @Override
  public byte getAsPrimitive(int index) {
    return getElement(index);
  }

  private byte getElement(int index) {
    return array[index];
  }

  @Mapper
  @Override
  public int size() {
    return array.length;
  }

  @Override
  public Iterator<Byte> iterator() {
    return list().iterator();
  }

  private List<Byte> list() {
    if (list == null) {
      list = ArraysFunctions.toByteList(array);
    }
    return list;
  }
}

