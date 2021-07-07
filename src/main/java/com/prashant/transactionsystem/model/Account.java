package com.prashant.transactionsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Account {
    @Id
    private UUID accountId;
    private Double balance = 0.0;

    public Account(){
    }

    public Account(UUID accountId){
        this.accountId = accountId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public Double getBalance() {
        return balance;
    }

    public void calculate(Transaction transaction){
        if(transaction == null || transaction.getAmount() == null || transaction.getType() == null){
            return;
        }
        if(TransactionType.CREDIT.equals(transaction.getType())){
            this.balance = this.balance + transaction.getAmount();
        } else {
            this.balance = this.balance - transaction.getAmount();
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId.equals(account.accountId) && balance.equals(account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, balance);
    }
}
