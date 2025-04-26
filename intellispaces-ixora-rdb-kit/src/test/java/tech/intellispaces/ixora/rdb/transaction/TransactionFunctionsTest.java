package tech.intellispaces.ixora.rdb.transaction;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import tech.intellispaces.jaquarius.Jaquarius;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link TransactionFunctions} class.
 */
public class TransactionFunctionsTest {

  @BeforeEach
  public void init() {
    Jaquarius.createModule().start();
  }

  @AfterEach
  public void deinit() {
    Jaquarius.releaseModule();
  }

  @Test
  public void testTransactional_whenOk() {
    // Given
    var transactionFactory = Mockito.mock(MovableTransactionFactory.class);
    var tx = Mockito.mock(MovableTransactionHandle.class);
    when(transactionFactory.getTransaction()).thenReturn(tx);

    List<Transaction> appliedTransactions = new ArrayList<>();

    // When
    TransactionFunctions.transactional(transactionFactory,
        () -> appliedTransactions.add(Transactions.current())
    );

    // Then
    Assertions.assertThat(appliedTransactions).containsExactly(tx);
    verify(tx).commit();
    verify(tx, never()).rollback();
  }
}
