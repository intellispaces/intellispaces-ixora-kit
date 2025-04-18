package tech.intellispaces.ixora.rdb.transaction;

import tech.intellispaces.actions.Action;
import tech.intellispaces.ixora.rdb.exception.TransactionExceptions;
import tech.intellispaces.jaquarius.aop.Interceptor;
import tech.intellispaces.jaquarius.system.ProjectionProvider;
import tech.intellispaces.reflection.method.MethodStatement;

import java.util.List;

public class TransactionalInterceptor extends Interceptor {

  public TransactionalInterceptor(
      MethodStatement joinPoint, Action nextAction, ProjectionProvider projectionProvider
  ) {
    super(joinPoint, nextAction, projectionProvider);
  }

  @Override
  public int order() {
    return wrappedAction().order();
  }

  @Override
  public Object execute(Object[] data) {
    MovableTransactionFactory transactionFactory = getDefaultTransactionFactory();
    Action joinAction = joinAction();
    return TransactionFunctions.transactional(transactionFactory, joinAction::execute, data);
  }

  private MovableTransactionFactory getDefaultTransactionFactory() {
    List<MovableTransactionFactory> transactionFactories = projectionProvider.getProjections(MovableTransactionFactory.class);
    if (transactionFactories.isEmpty()) {
      throw TransactionExceptions.withMessage("Transaction factory is not found");
    }
    if (transactionFactories.size() > 1) {
      throw TransactionExceptions.withMessage("Multiple transaction factories are found");
    }
    return transactionFactories.get(0);
  }
}
