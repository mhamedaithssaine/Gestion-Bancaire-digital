package Repositories;
import Module.Account;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface AccountRepository {
     Account save(Account account);
     Account findById(String Id);
     List<Account> findByOwner(UUID ownerId);
     Account update(Account account);




}
