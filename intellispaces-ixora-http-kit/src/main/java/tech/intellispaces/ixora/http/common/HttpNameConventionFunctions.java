package tech.intellispaces.ixora.http.common;

import java.util.List;

import tech.intellispaces.commons.text.StringFunctions;
import tech.intellispaces.commons.type.ClassNameFunctions;
import tech.intellispaces.ixora.http.HttpRequestDomain;
import tech.intellispaces.jaquarius.naming.NameConventionFunctions;
import tech.intellispaces.statementsj.customtype.CustomType;
import tech.intellispaces.statementsj.method.MethodParam;
import tech.intellispaces.statementsj.method.MethodStatement;
import tech.intellispaces.statementsj.reference.CustomTypeReferences;

public interface HttpNameConventionFunctions {

  static String getPortHandleCanonicalName(CustomType httpPortDomain) {
    return StringFunctions.replaceTailOrElseThrow(httpPortDomain.canonicalName(), "Domain", "HandleGenerated");
  }

  static String getPortHandleImplCanonicalName(CustomType httpPortDomain) {
    return getPortHandleCanonicalName(httpPortDomain) + "Wrapper";
  }

  static String getPortAssistantCustomizerCanonicalName(CustomType httpPortDomain) {
    return StringFunctions.replaceTailOrElseThrow(httpPortDomain.canonicalName(), "Domain", "AssistantCustomizer");
  }

  static String getPortFactoryCanonicalName(CustomType httpPortDomain) {
    return StringFunctions.replaceTailOrElseThrow(httpPortDomain.canonicalName(), "Domain", "Factory");
  }

  static String getPortGuideCanonicalName(CustomType ontology) {
    return StringFunctions.replaceTailOrElseThrow(ontology.canonicalName(), "Ontology", "Guide");
  }

  static String getPortExchangeChannelCanonicalName(CustomType httpPortDomain) {
    MethodStatement exchangeChannel = httpPortDomain
        .declaredMethod("exchange", List.of(CustomTypeReferences.get(HttpRequestDomain.class)))
        .orElseThrow();
    return NameConventionFunctions.getChannelClassCanonicalName(exchangeChannel);
  }

  static String getActualPortExchangeChannelCanonicalName(
      CustomType portDomain, CustomType ontology, MethodStatement channelMethod
  ) {
    var sb = new StringBuilder();

    String packageName = ClassNameFunctions.getPackageName(ontology.canonicalName());
    if (StringFunctions.isNotBlank(packageName)) {
      sb.append(packageName).append(".");
    }
    sb.append(StringFunctions.removeTailOrElseThrow(portDomain.simpleName(), "Domain"));
    sb.append(StringFunctions.capitalizeFirstLetter(channelMethod.name()));
    for (MethodParam param : channelMethod.params()) {
      sb.append(StringFunctions.capitalizeFirstLetter(param.name()));
    }
    sb.append("ExchangeChannel");
    return sb.toString();
  }

  static String getFormalPortExchangeChannelCanonicalName(
      CustomType portDomain, CustomType ontology, MethodStatement channelMethod
  ) {
    var sb = new StringBuilder();
    sb.append(StringFunctions.removeTailOrElseThrow(portDomain.canonicalName(), "Domain"));
    sb.append(StringFunctions.capitalizeFirstLetter(channelMethod.name()));
    for (MethodParam param : channelMethod.params()) {
      sb.append(StringFunctions.capitalizeFirstLetter(param.name()));
    }
    sb.append("ExchangeChannel");
    return sb.toString();
  }
}
