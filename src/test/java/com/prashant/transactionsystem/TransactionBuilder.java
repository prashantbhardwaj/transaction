package com.prashant.transactionsystem;

import java.util.UUID;

public class TransactionBuilder {
    private UUID id;
    private UUID accountId;
    private String currency;
    private Double amount;
    private String description;
    private TransactionType type;

    public static final TransactionBuilder instance(){
        return new TransactionBuilder();
    }

    public TransactionBuilder withRandomAccountId(){
        this.accountId = UUID.randomUUID();
        return this;
    }

    public TransactionBuilder withGBP(){
        this.currency = "GBP";
        return this;
    }

    public TransactionBuilder withAmount(Double value){
        this.amount = value;
        return this;
    }

    public TransactionBuilder description(String value){
        this.description = value;
        return this;
    }

    public TransactionBuilder debit(){
        this.type = TransactionType.DEBIT;
        return this;
    }

    public TransactionBuilder credit(){
        this.type = TransactionType.CREDIT;
        return this;
    }

    public Transaction build(){
        Transaction transaction = new Transaction();
        transaction.setAccountId(this.accountId);
        transaction.setCurrency(this.currency);
        transaction.setAmount(this.amount);
        transaction.setDescription(this.description);
        transaction.setType(this.type);
        return transaction;
    }
}
