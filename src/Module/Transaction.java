package Module;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Transaction {
    private UUID id ;
    private Instant timeTamps;
    private String accountId;
    private TransactionType type ;
    private BigDecimal amount;
    private String counterpartyAccountId;
    private String description;

    public Transaction(String accountId, TransactionType type, BigDecimal amount,
                       String counterpartyAccountId, String description) {
        this.id = UUID.randomUUID();
        this.timeTamps = Instant.now();
        this.accountId = accountId;
        this.type = type;
        this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.counterpartyAccountId = counterpartyAccountId;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getTimeTamps() {
        return timeTamps;
    }

    public void setTimeTamps(Instant timeTamps) {
        this.timeTamps = timeTamps;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCounterpartyAccountId() {
        return counterpartyAccountId;
    }

    public void setCounterpartyAccountId(String counterpartyAccountId) {
        this.counterpartyAccountId = counterpartyAccountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
