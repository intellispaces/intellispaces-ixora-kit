package tech.intellispaces.ixora.http;

import tech.intellispaces.ixora.data.stream.ByteStreamsCustomizer;
import tech.intellispaces.ixora.data.stream.InputDataStream;
import tech.intellispaces.ixora.http.exception.HttpException;
import tech.intellispaces.ixora.internet.uri.JoinBasePathStringWithEndpointStringGuide;
import tech.intellispaces.ixora.internet.uri.Uri;
import tech.intellispaces.ixora.internet.uri.UrisCustomizer;
import tech.intellispaces.jaquarius.annotation.AutoGuide;
import tech.intellispaces.jaquarius.annotation.Mapper;
import tech.intellispaces.jaquarius.annotation.MapperOfMoving;
import tech.intellispaces.jaquarius.annotation.ObjectHandle;

@ObjectHandle(DedicatedHttpPortDomain.class)
public abstract class DedicatedHttpPortImpl implements MovableDedicatedHttpPort {
  private final String baseUrl;
  private final MovableHttpPort underlyingPort;

  public DedicatedHttpPortImpl(String baseUrl, MovableHttpPort underlyingPort) {
    this.baseUrl = baseUrl;
    this.underlyingPort = underlyingPort;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public MovableHttpPort getUnderlyingPort() {
    return underlyingPort;
  }

  @AutoGuide
  abstract JoinBasePathStringWithEndpointStringGuide joinUrlGuide();

  @Override
  @MapperOfMoving
  public HttpResponse exchange(String endpoint, HttpMethod method) throws HttpException {
    return exchange(
        endpoint,
        method,
        null,
        ByteStreamsCustomizer.empty()
    );
  }

  @Override
  @MapperOfMoving
  public HttpResponse exchange(
      String endpoint,
      HttpMethod method,
      HttpHeaderList headers,
      InputDataStream<Byte> body
  ) throws HttpException {
    Uri uri = UrisCustomizer.get(joinUrlGuide().map(baseUrl, endpoint));
    HttpRequest request = HttpRequestsCustomizer.get(method, uri);
    return underlyingPort.exchange(request);
  }

  @Mapper
  @Override
  public String baseUrl() {
    return baseUrl;
  }
}
