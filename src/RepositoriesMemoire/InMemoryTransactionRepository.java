package RepositoriesMemoire;

import Module.Transaction;
import Repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
public class InMemoryTransactionRepository implements TransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public Transaction save(Transaction transaction) {
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findByAccountId(String accountId) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAccountId().equals(accountId)) {
                result.add(t);
            }
        }
        return result;
    }
}
