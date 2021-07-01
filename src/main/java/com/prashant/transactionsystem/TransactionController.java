package com.prashant.transactionsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

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
        return transactionRepository.save(transaction);
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
}
