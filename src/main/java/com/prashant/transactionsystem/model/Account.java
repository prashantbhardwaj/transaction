package com.prashant.transactionsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
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
}
