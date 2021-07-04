package com.prashant.transactionsystem.repositories;

import com.prashant.transactionsystem.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AccountRepository extends CrudRepository<Account, UUID> {
}
