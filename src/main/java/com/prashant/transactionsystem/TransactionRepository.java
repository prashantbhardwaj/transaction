package com.prashant.transactionsystem;

import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.UUID;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, UUID> {

}
