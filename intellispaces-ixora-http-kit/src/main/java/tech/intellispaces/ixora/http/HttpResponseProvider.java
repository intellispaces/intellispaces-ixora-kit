package tech.intellispaces.ixora.http;

import tech.intellispaces.jaquarius.annotation.ObjectProvider;

import java.io.InputStream;

@ObjectProvider
public class HttpResponseProvider implements HttpResponseProviderCustomizer {

  @Override
  public HttpResponseHandle create(HttpStatusHandle status) {
    return new HttpResponseImplWrapper(status);
  }

  @Override
  public HttpResponseHandle create(HttpStatusHandle status, InputStream body) {
    return new HttpResponseImplWrapper(status, body);
  }

  @Override
  public HttpResponseHandle create(HttpStatusHandle status, String body) {
    return new HttpResponseImplWrapper(status, body);
  }

  @Override
  public HttpResponseHandle create(HttpStatusHandle status, byte[] body) {
    return new HttpResponseImplWrapper(status, body);
  }
}
