package Services;

import Module.Transaction;
import Module.TransactionType;
import Repositories.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction recordDeposit(String accountId, BigDecimal amount, String description) {
        Transaction t = new Transaction(accountId, TransactionType.DEPOSIT, amount, null, description);
        return transactionRepository.save(t);
    }

    public Transaction recordWithdraw(String accountId, BigDecimal amount, String description) {
        Transaction t = new Transaction(accountId, TransactionType.WITHDRAW, amount, null, description);
        return transactionRepository.save(t);
    }

    public Transaction recordTransferOut(String fromAccountId, String toAccountId, BigDecimal amount, String description) {
        Transaction t = new Transaction(fromAccountId, TransactionType.TRANSFEROUT, amount, toAccountId, description);
        return transactionRepository.save(t);
    }

    public Transaction recordTransferIn(String toAccountId, String fromAccountId, BigDecimal amount, String description) {
        Transaction t = new Transaction(toAccountId, TransactionType.TRANSFERIN, amount, fromAccountId, description);
        return transactionRepository.save(t);
    }

    public List<Transaction> getAccountHistory(String accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}
