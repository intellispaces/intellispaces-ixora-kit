package tech.intellispaces.ixora.http;

import tech.intellispaces.ixora.internet.uri.Uri;
import tech.intellispaces.reflections.framework.annotation.Factory;

@Factory
public class HttpRequestFactory implements HttpRequestAssistantCustomizer {

  @Override
  public UnmovableHttpRequestReflection create(HttpMethod method, Uri requestURI) {
    return new HttpRequestReflectionImplWrapper(method, requestURI);
  }
}
