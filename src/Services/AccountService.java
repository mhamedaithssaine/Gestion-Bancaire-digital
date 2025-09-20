package Services;

import Repositories.AccountRepository;
import Module.Account;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import Module.Transaction;

public class AccountService {

    public final AccountRepository accountRepository;
    public final TransactionService transactionService;

    public AccountService(AccountRepository accountRepository,TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
    }

    public Account createdAccount(UUID ownerUserId) {
        Account account = new Account(ownerUserId);
        return accountRepository.save(account);
    }

    public List<Account> getMyAccounts(UUID ownerId) {
        return accountRepository.findByOwner(ownerId);
    }

    public Account deposit(String accountId, UUID ownerUserId, BigDecimal balance) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            System.out.println("account not found !");
            return null;
        }
        if (!account.getOwnerUserId().equals(ownerUserId)) {
            System.out.println("You are not the owner of this account!");
            return null;
        }
        if (!account.isActive()) {
            System.out.println("This account is inactive !");
            return null;
        }
        if (balance.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Balance not valid !");
            return null;
        }
        account.setBalance(account.getBalance().add(balance));
        accountRepository.update(account);
        transactionService.recordDeposit(accountId, balance , "Deposit to account");


        return account;
    }

    public Account withdraw(String accountId,UUID ownerUserId, BigDecimal withdrawAmount ) {

        Account account = accountRepository.findById(accountId);
        if (account == null) {
            System.out.println("account not found !");
            return null;
        }
        if (account.getBalance().compareTo(BigDecimal.ZERO) <= 0 || account.getBalance().compareTo(withdrawAmount ) < 0) {
            System.out.println("Insufficient balance or account has zero funds!");
            return null ;
        }
        if (!account.getOwnerUserId().equals(ownerUserId)) {
            System.out.println("You are not the owner of this account!");
            return null;
        }

       BigDecimal newBalance = account.getBalance().subtract(withdrawAmount ) ;
        account.setBalance(newBalance);
        accountRepository.update(account);
        transactionService.recordWithdraw(accountId,withdrawAmount ,"Deposit to account ");
        return account;

    }
    public Account transfer(String fromAccountId, String toAccountId, UUID ownerUserId, BigDecimal amount) {
        Account fromAccount = accountRepository.findById(fromAccountId);
        Account toAccount   = accountRepository.findById(toAccountId);

        if (fromAccount == null || toAccount == null) {
            System.out.println("One of the accounts not found!");
            return null;
        }
        if (!fromAccount.getOwnerUserId().equals(ownerUserId)) {
            System.out.println("You are not the owner of the source account!");
            return null;
        }
        if (!fromAccount.isActive() || !toAccount.isActive()) {
            System.out.println("One of the accounts is inactive!");
            return null;
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid amount!");
            return null;
        }
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            System.out.println("Insufficient balance!");
            return null;
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        accountRepository.update(fromAccount);

        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.update(toAccount);

        transactionService.recordTransferOut(fromAccountId, toAccountId, amount, "Transfer to " + toAccountId);
        transactionService.recordTransferIn(toAccountId, fromAccountId, amount, "Transfer from " + fromAccountId);

        return fromAccount;
    }

    public List<Transaction> getTransactionHistory(String accountId, UUID ownerUserId) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            System.out.println("Account not found!");
            return null;
        }
        if (!account.getOwnerUserId().equals(ownerUserId)) {
            System.out.println("You are not the owner of this account!");
            return null;
        }
        return transactionService.getAccountHistory(accountId);
    }

    public Account closeAccount(String accountId, UUID ownerUserId) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            System.out.println("Account not found!");
            return null;
        }
        if (!account.getOwnerUserId().equals(ownerUserId)) {
            System.out.println("You are not the owner!");
            return null;
        }
        if (!account.isActive()) {
            System.out.println("Account already closed!");
            return null;
        }

        account.setActive(false);
        accountRepository.update(account);

        System.out.println("Account closed successfully!");
        return account;
    }



}
