package Services;

import Repositories.AccountRepository;
import Module.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class AccountService {

    public final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
        return account;

    }


}
