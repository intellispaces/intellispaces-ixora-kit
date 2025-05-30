package tech.intellispaces.ixora.rdb.transaction;

import tech.intellispaces.ixora.rdb.datasource.DataSource;
import tech.intellispaces.ixora.rdb.datasource.MovableConnection;
import tech.intellispaces.ixora.rdb.datasource.MovableDataSource;
import tech.intellispaces.ixora.rdb.exception.TransactionException;
import tech.intellispaces.reflections.framework.annotation.Mapper;
import tech.intellispaces.reflections.framework.annotation.MapperOfMoving;
import tech.intellispaces.reflections.framework.annotation.Reflection;

@Reflection(TransactionFactoryDomain.class)
abstract class TransactionFactoryOverDataSourceReflectionImpl implements MovableTransactionFactory {
  private final MovableDataSource dataSource;

  TransactionFactoryOverDataSourceReflectionImpl(MovableDataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Mapper
  @Override
  public DataSource dataSource() {
    return dataSource;
  }

  @Override
  @MapperOfMoving
  public MovableTransaction getTransaction() throws TransactionException {
    MovableConnection connection = dataSource.getConnection();
    return new TransactionOverConnectionReflectionImplWrapper(connection);
  }
}
