package tech.intellispaces.ixora.rdb.annotationprocessor;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import tech.intellispaces.annotationprocessor.ArtifactGeneratorContext;
import tech.intellispaces.commons.text.StringFunctions;
import tech.intellispaces.commons.type.Types;
import tech.intellispaces.ixora.data.association.Map;
import tech.intellispaces.ixora.data.association.Maps;
import tech.intellispaces.ixora.rdb.annotation.Transactional;
import tech.intellispaces.ixora.rdb.exception.RdbExceptions;
import tech.intellispaces.ixora.rdb.transaction.Transaction;
import tech.intellispaces.ixora.rdb.transaction.Transactions;
import tech.intellispaces.jstatements.customtype.CustomType;
import tech.intellispaces.jstatements.method.MethodStatement;
import tech.intellispaces.reflections.annotationprocessor.ReflectionsArtifactGenerator;
import tech.intellispaces.reflections.framework.annotation.Guide;
import tech.intellispaces.reflections.framework.annotation.Ontology;

import java.util.Optional;

public class EntityCrudGuideImplGenerator extends ReflectionsArtifactGenerator {
  private String entityHandleSimpleName;
  private String entityTable;
  private String entityAlias;
  private boolean entityHasIdentifier;
  private String identifierType;
  private String identifierColumn;
  private String guideType;

  public EntityCrudGuideImplGenerator(CustomType entityType) {
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
    return EntityAnnotationFunctions.getCrudGuideGeneratedImplCanonicalName(sourceArtifact());
  }

  @Override
  protected String templateName() {
    return "/entity_crud_guide_impl.template";
  }

  @Override
  protected boolean analyzeSourceArtifact(ArtifactGeneratorContext context) {
    addImport(Types.class);
    addImport(Maps.class);
    addImport(Guide.class);
    addImport(Transactional.class);
    addImport(Transaction.class);
    addImport(Map.class);
    addImport(Transactions.class);

    guideType = addImportAndGetSimpleName(EntityAnnotationFunctions.getCrudGuideCanonicalName(sourceArtifact()));
    entityHandleSimpleName = addImportAndGetSimpleName(
        EntityAnnotationFunctions.getEntityHandleCanonicalName(sourceArtifact())
    );

    analyzeEntity();

    addVariable("guideType", guideType);
    addVariable("entityReflectionSimpleName", entityHandleSimpleName);
    addVariable("entityTable", entityTable);
    addVariable("entityAlias", entityAlias);
    addVariable("entityHasIdentifier", entityHasIdentifier);
    addVariable("identifierType", identifierType);
    addVariable("identifierColumn", identifierColumn);
    return true;
  }

  private void analyzeEntity() {
    entityTable = getTableName();
    entityAlias = entityTable.substring(0, 1).toLowerCase();
    analyzeEntityIdentifier();
  }

  private String getTableName() {
    Table table = sourceArtifact().selectAnnotation(Table.class).orElseThrow(() ->
        RdbExceptions.withMessage("RDB entity class {0} must annotation with annotation {1}",
        sourceArtifact().canonicalName(), Table.class.getCanonicalName()
    ));
    if (StringFunctions.isNotBlank(table.schema())) {
      return table.schema() + "." + table.name();
    }
    return table.name();
  }

  private void analyzeEntityIdentifier() {
    Optional<MethodStatement> identifierMethod = EntityAnnotationFunctions.findIdentifierMethod(sourceArtifact());
    if (identifierMethod.isEmpty()) {
      entityHasIdentifier = false;
      return;
    }
    entityHasIdentifier = true;

    identifierType = addImportAndGetSimpleName(
        EntityAnnotationFunctions.getIdentifierType(sourceArtifact(), identifierMethod.get())
    );

    Column column = identifierMethod.orElseThrow().selectAnnotation(Column.class).orElseThrow(() ->
        RdbExceptions.withMessage("RDB entity {0} identifier method {1} must annotation with annotation {2}",
            sourceArtifact().canonicalName(), identifierMethod.get().name(), Column.class.getCanonicalName()
        ));
    identifierColumn = column.name();
  }
}
