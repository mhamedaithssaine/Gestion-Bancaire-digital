package RepositoriesMemoire;
import Repositories.AccountRepository;
import Module.Account;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

public class InMemoryAccountRepository implements AccountRepository {
    private AccountRepository accountRepository;
    private Set<Account> accounts = new HashSet<>();

    @Override
    public Account save(Account account) {
        accounts.add(account);
        return  account;
    }

    @Override
    public Account findById(String id){
        for(Account account : accounts ){
            if(account.getAccountId().equals(id)){
                return account ;
            }
        }
        return null;
    }

    @Override
    public List<Account> findByOwner(UUID ownerId) {
        List<Account> result = new ArrayList<>();
        for (Account account : accounts) {
            if (account.getOwnerUserId().equals(ownerId)) {
                result.add(account);
            }
        }
        return result;
    }

    @Override
    public Account update(Account account){
        accounts.add(account);
        return account;
    }



}
