package tech.intellispaces.ixora.rdb.annotationprocessor;

import java.util.Optional;

import tech.intellispaces.annotationprocessor.ArtifactGeneratorContext;
import tech.intellispaces.ixora.rdb.transaction.Transaction;
import tech.intellispaces.jaquarius.annotation.Guide;
import tech.intellispaces.jaquarius.annotation.Mapper;
import tech.intellispaces.jaquarius.annotation.MapperOfMoving;
import tech.intellispaces.jaquarius.annotation.Ontology;
import tech.intellispaces.jaquarius.annotationprocessor.JaquariusArtifactGenerator;
import tech.intellispaces.reflection.customtype.CustomType;
import tech.intellispaces.reflection.method.MethodStatement;

public class EntityCrudGuideGenerator extends JaquariusArtifactGenerator {
  private boolean entityHasIdentifier;
  private String entityHandleSimpleName;
  private String identifierType;
  private String transactionToEntityByIdentifierChannelSimpleName;
  private String transactionToNewEntityChannelSimpleName;

  public EntityCrudGuideGenerator(CustomType entityType) {
    super(entityType);
  }

  @Override
  public boolean isRelevant(ArtifactGeneratorContext context) {
    return context.isProcessingFinished(
        EntityAnnotationFunctions.getCrudOntologyCanonicalName(sourceArtifact()), Ontology.class
    );
  }

  @Override
  public String generatedArtifactName() {
    return EntityAnnotationFunctions.getCrudGuideCanonicalName(sourceArtifact());
  }

  @Override
  protected String templateName() {
    return "/entity_crud_guide.template";
  }

  @Override
  protected boolean analyzeSourceArtifact(ArtifactGeneratorContext context) {
    addImport(Guide.class);
    addImport(Mapper.class);
    addImport(MapperOfMoving.class);
    addImport(Transaction.class);

    entityHandleSimpleName = addImportAndGetSimpleName(
        EntityAnnotationFunctions.getEntityHandleCanonicalName(sourceArtifact())
    );

    analyzeEntityIdentifier();

    addVariable("entityHasIdentifier", entityHasIdentifier);
    addVariable("entityHandleSimpleName", entityHandleSimpleName);
    addVariable("identifierType", identifierType);
    addVariable("transactionToEntityByIdentifierChannelSimpleName", transactionToEntityByIdentifierChannelSimpleName);
    addVariable("transactionToNewEntityChannelSimpleName", transactionToNewEntityChannelSimpleName);
    return true;
  }

  private void analyzeEntityIdentifier() {
    Optional<MethodStatement> identifierMethod = EntityAnnotationFunctions.findIdentifierMethod(sourceArtifact());
    if (identifierMethod.isEmpty()) {
      entityHasIdentifier = false;
      return;
    }
    entityHasIdentifier = true;

    identifierType = addImportAndGetSimpleName(
        EntityAnnotationFunctions.getIdentifierType(sourceArtifact(), identifierMethod.orElseThrow())
    );
    transactionToEntityByIdentifierChannelSimpleName = EntityAnnotationFunctions.getTransactionToEntityByIdentifierChannelSimpleName(
        sourceArtifact()
    );
    transactionToNewEntityChannelSimpleName = EntityAnnotationFunctions.getTransactionToNewEntityChannelSimpleName(
        sourceArtifact()
    );
  }
}
