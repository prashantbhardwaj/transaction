package com.prashant.transactionsystem.util;

import com.prashant.transactionsystem.model.Account;
import com.prashant.transactionsystem.model.Transaction;
import com.prashant.transactionsystem.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountCalculator.class);

    @Autowired
    private AccountRepository accountRepository;

    public void handle(Transaction transaction){
        LOGGER.info("Handelling transaction - {}", transaction);
        Account account = null;
        if(accountRepository.existsById(transaction.getAccountId())){
            account = accountRepository.findById(transaction.getAccountId()).get();
        } else {
            account = new Account(transaction.getAccountId());
        }
        account.calculate(transaction);
        accountRepository.save(account);
    }
}
