package tech.intellispaces.ixora.internet.uri;

import java.util.Arrays;

import tech.intellispaces.ixora.data.collection.ListHandle;
import tech.intellispaces.ixora.data.collection.Lists;
import tech.intellispaces.reflections.annotation.Guide;
import tech.intellispaces.reflections.annotation.Mapper;

@Guide
public class SplitUriPathStringToPartsGuideImpl implements SplitUriPathStringToPartsGuide {
  private static final String SLASH = "/";

  @Mapper
  @Override
  public ListHandle<String> splitUriPathStringToParts(String uriPath) {
    if (uriPath == null) {
      return null;
    }
    String path = uriPath.startsWith(SLASH) ? uriPath.substring(1) : uriPath;
    path = path.endsWith(SLASH) ? path.substring(0, path.length() - 1) : path;
    return Lists.handleOf(Arrays.asList(path.split(SLASH)), String.class);
  }
}
