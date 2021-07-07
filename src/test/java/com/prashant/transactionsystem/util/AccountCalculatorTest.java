package com.prashant.transactionsystem.util;

import com.prashant.transactionsystem.TransactionBuilder;
import com.prashant.transactionsystem.model.Account;
import com.prashant.transactionsystem.model.Transaction;
import com.prashant.transactionsystem.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountCalculatorTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountCalculator subject;

    //@BeforeEach
    public void setUp(){
        this.subject = new AccountCalculator();
    }

    @Test
    public void testAmountIsAddedForCreditTransactionWhenAccountAlreadyExist(){
        Transaction testTransaction = TransactionBuilder.instance()
                .credit()
                .withRandomAccountId()
                .withGBP()
                .withAmount(1.00)
                .build();

        Account testAccount = new Account(testTransaction.getAccountId());

        Mockito.when(accountRepository.existsById(eq(testTransaction.getAccountId()))).thenReturn(true);
        Mockito.when(accountRepository.findById(eq(testTransaction.getAccountId()))).thenReturn(Optional.of(testAccount));

        subject.handle(testTransaction);

        assertThat(testAccount.getBalance()).isEqualTo(1.00);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        verify(accountRepository, times(1)).existsById(testAccount.getAccountId());
        verify(accountRepository, times(1)).save(accountCaptor.capture());
        assertThat(accountCaptor.getValue().getBalance()).isEqualTo(1.00);

        subject.handle(testTransaction);

        assertThat(testAccount.getBalance()).isEqualTo(2.00);

        ArgumentCaptor<Account> accountCaptor2 = ArgumentCaptor.forClass(Account.class);

        verify(accountRepository, times(2)).existsById(testAccount.getAccountId());
        verify(accountRepository, times(2)).save(accountCaptor2.capture());

        assertThat(accountCaptor2.getAllValues().get(1).getBalance()).isEqualTo(2.00);

    }

    @Test
    public void testAmountIsDeductedForDebitTransactionWhenAccountAlreadyExist(){

        Transaction testTransaction = TransactionBuilder.instance()
                .debit()
                .withRandomAccountId()
                .withGBP()
                .withAmount(1.00)
                .build();

        Account testAccount = new Account(testTransaction.getAccountId());

        Mockito.when(accountRepository.existsById(eq(testTransaction.getAccountId()))).thenReturn(true);
        Mockito.when(accountRepository.findById(eq(testTransaction.getAccountId()))).thenReturn(Optional.of(testAccount));

        subject.handle(testTransaction);

        assertThat(testAccount.getBalance()).isEqualTo(-1.00);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        verify(accountRepository, times(1)).existsById(testAccount.getAccountId());
        verify(accountRepository, times(1)).save(accountCaptor.capture());
        assertThat(accountCaptor.getValue().getBalance()).isEqualTo(-1.00);

        subject.handle(testTransaction);

        assertThat(testAccount.getBalance()).isEqualTo(-2.00);

        ArgumentCaptor<Account> accountCaptor2 = ArgumentCaptor.forClass(Account.class);

        verify(accountRepository, times(2)).existsById(testAccount.getAccountId());
        verify(accountRepository, times(2)).save(accountCaptor2.capture());

        assertThat(accountCaptor2.getAllValues().get(1).getBalance()).isEqualTo(-2.00);

    }

    @Test
    public void testAmountIsAddedForCreditTransactionWhenAccountDoesNotExist(){
        Transaction testTransaction = TransactionBuilder.instance()
                .credit()
                .withRandomAccountId()
                .withGBP()
                .withAmount(1.00)
                .build();

        Account testAccount = new Account(testTransaction.getAccountId());

        Mockito.when(accountRepository.existsById(eq(testTransaction.getAccountId()))).thenReturn(false);

        subject.handle(testTransaction);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        verify(accountRepository, times(1)).existsById(testAccount.getAccountId());
        verify(accountRepository, times(1)).save(accountCaptor.capture());

        assertThat(accountCaptor.getValue().getBalance()).isEqualTo(1.00);

        //verifyNoInteractions(accountRepository);

    }

    @Test
    public void testAmountIsDeductedForDebitTransactionWhenAccountDoesNotExist(){

        Transaction testTransaction = TransactionBuilder.instance()
                .debit()
                .withRandomAccountId()
                .withGBP()
                .withAmount(1.00)
                .build();

        Account testAccount = new Account(testTransaction.getAccountId());

        Mockito.when(accountRepository.existsById(eq(testTransaction.getAccountId()))).thenReturn(false);

        subject.handle(testTransaction);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        verify(accountRepository, times(1)).existsById(testAccount.getAccountId());
        verify(accountRepository, times(1)).save(accountCaptor.capture());

        assertThat(accountCaptor.getValue().getBalance()).isEqualTo(-1.00);

        //verifyNoInteractions(accountRepository);

    }
}