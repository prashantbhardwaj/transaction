package com.prashant.transactionsystem.repositories;

import com.prashant.transactionsystem.model.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.UUID;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, UUID> {

}
