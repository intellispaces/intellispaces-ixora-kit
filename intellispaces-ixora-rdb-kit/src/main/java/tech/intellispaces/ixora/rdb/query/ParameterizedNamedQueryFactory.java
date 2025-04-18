package tech.intellispaces.ixora.rdb.query;

import tech.intellispaces.ixora.data.collection.UnmovableList;
import tech.intellispaces.ixora.data.collection.UnmovableListHandle;
import tech.intellispaces.jaquarius.annotation.Factory;
import tech.intellispaces.jaquarius.object.reference.ObjectHandles;

@Factory
public class ParameterizedNamedQueryFactory implements ParameterizedNamedQueryAssistantCustomizer {

  @Override
  public UnmovableParameterizedNamedQueryHandle create(String query, UnmovableList<String> paramNames) {
    return new UnmovableParameterizedNamedQueryDataset(
        null,
        query,
        ObjectHandles.handle(paramNames, UnmovableListHandle.class)
    );
  }
}
