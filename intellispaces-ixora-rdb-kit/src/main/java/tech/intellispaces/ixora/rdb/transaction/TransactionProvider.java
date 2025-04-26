package tech.intellispaces.ixora.rdb.transaction;

import java.util.ArrayDeque;
import java.util.Deque;

import tech.intellispaces.jaquarius.annotation.Factory;
import tech.intellispaces.jaquarius.exception.TraverseExceptions;

@Factory
public class TransactionProvider implements TransactionAssistantCustomizer {
  private static final ThreadLocal<Deque<MovableTransactionHandle>> CURRENT_TRANSACTIONS = new ThreadLocal<>();

  @Override
  public MovableTransactionHandle current() {
    MovableTransactionHandle tx = null;
    Deque<MovableTransactionHandle> transactions = CURRENT_TRANSACTIONS.get();
    if (transactions != null) {
      tx = transactions.peek();
    }
    if (tx == null) {
      throw TraverseExceptions.withMessage("Current transaction is not defined");
    }
    return tx;
  }

  public static void setCurrentTransaction(MovableTransactionHandle tx) {
    if (tx != null) {
      Deque<MovableTransactionHandle> transactions = CURRENT_TRANSACTIONS.get();
      if (transactions == null) {
        transactions = new ArrayDeque<>();
        CURRENT_TRANSACTIONS.set(transactions);
      }
      transactions.push(tx);
    } else {
      Deque<MovableTransactionHandle> transactions = CURRENT_TRANSACTIONS.get();
      if (transactions != null) {
        transactions.pop();
      }
    }
  }
}
