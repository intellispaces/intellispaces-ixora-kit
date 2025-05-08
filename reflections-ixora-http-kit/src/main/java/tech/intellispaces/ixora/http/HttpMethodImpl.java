package tech.intellispaces.ixora.http;

import tech.intellispaces.reflections.annotation.Mapper;
import tech.intellispaces.reflections.annotation.ObjectHandle;

@ObjectHandle(HttpMethodDomain.class)
abstract class HttpMethodImpl implements UnmovableHttpMethod {
  private final String name;

  HttpMethodImpl(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Mapper
  @Override
  public String name() {
    return name;
  }

  @Mapper
  @Override
  public boolean isGetMethod() {
    return HttpMethods.get().name().equals(name);
  }
}
