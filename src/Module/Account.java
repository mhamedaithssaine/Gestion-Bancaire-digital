package Module;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Account {
    private String accountId;
    private UUID ownerUserId ;
    private BigDecimal balance ;
    private Instant createdAt = Instant.now();
    private boolean active;

    public Account(  UUID ownerUserId) {
        this.accountId = generateAccountId();
        this.ownerUserId = ownerUserId;
        this.balance = BigDecimal.ZERO.setScale(2);
        this.createdAt = Instant.now();
        this.active = true ;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public UUID getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(UUID ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String generateAccountId(){
        int secondGroup = (int)(Math.random() * 9000) + 1000;
        int firstGroup = (int)(Math.random() * 9000) + 1000;
        return "BK-" + firstGroup + "-" + secondGroup;
    }
}
