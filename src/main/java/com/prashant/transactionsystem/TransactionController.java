package com.prashant.transactionsystem;

import com.prashant.transactionsystem.model.Account;
import com.prashant.transactionsystem.model.Transaction;
import com.prashant.transactionsystem.repositories.AccountRepository;
import com.prashant.transactionsystem.repositories.TransactionRepository;
import com.prashant.transactionsystem.util.AccountCalculator;
import com.prashant.transactionsystem.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class TransactionController {

    @Autowired
    private AccountCalculator accountCalculator;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/transactions")
    public Iterable<Transaction> getTransactions(){
        return transactionRepository.findAll();
    }

    @GetMapping("/transaction/{id}")
    public Optional<Transaction> getTransaction(@PathVariable UUID id){
        return transactionRepository.findById(id);
    }

    @PostMapping("/transaction")
    public Transaction addTransaction(@RequestBody Transaction transaction){
        Transaction updatedTransaction = transactionRepository.save(transaction);
        this.accountCalculator.handle(updatedTransaction);
        return updatedTransaction;
    }

    @PutMapping("/transaction/{id}")
    public Transaction updateTransaction(@PathVariable UUID id, @RequestBody Transaction transaction){
        return transactionRepository.save(transaction);
    }

    @DeleteMapping("/transaction/{id}")
    public String deleteTransaction(@PathVariable UUID id){
        if(!transactionRepository.existsById(id)){
            return Constants.RECORD_NOT_EXISTS;
        }
        transactionRepository.deleteById(id);
        return Constants.DELETED;
    }

    @GetMapping("/account/{id}")
    public Optional<Account> getAccountDetails(@PathVariable UUID id){
        return accountRepository.findById(id);
    }
}
