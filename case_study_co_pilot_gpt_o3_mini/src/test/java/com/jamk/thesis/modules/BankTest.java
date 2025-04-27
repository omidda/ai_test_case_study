package com.jamk.thesis.modules;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BankTest {

    private Bank bank;
    private Account account1;
    private Account account2;

    @BeforeEach
    void setup() {
        bank = new Bank();
        account1 = new Account("ACC001", "Alice", 100.0);
        account2 = new Account("ACC002", "Bob", 200.0);
    }

    // Test for addAccount
    @Test
    void addAccount_ValidAccount_ShouldAddSuccessfully() {
        bank.addAccount(account1);
        Account found = bank.getAccount("ACC001");
        assertThat(found).isEqualTo(account1);
    }
    
    @Test
    void addAccount_DuplicateAccount_ShouldThrowException() {
        bank.addAccount(account1);
        Throwable thrown = catchThrowable(() -> bank.addAccount(new Account("ACC001", "Alice", 50.0)));
        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Account already exists.");
    }

    
    // Test for getAccount
    @Test
    void getAccount_ExistingAccount_ShouldReturnAccount() {
        bank.addAccount(account1);
        Account retrieved = bank.getAccount("ACC001");
        assertThat(retrieved).isEqualTo(account1);
    }
    
    @Test
    void getAccount_NonExistingAccount_ShouldReturnNull() {
        Account retrieved = bank.getAccount("NONEXISTENT");
        assertThat(retrieved).isNull();
    }
    
    // Test for getTotalBalance
    @Test
    void getTotalBalance_NoAccounts_ShouldReturnZero() {
        assertThat(bank.getTotalBalance()).isEqualTo(0.0);
    }
    
    @Test
    void getTotalBalance_MultipleAccounts_ShouldReturnSumOfBalances() {
        bank.addAccount(account1);
        bank.addAccount(account2);
        double expectedTotal = account1.getBalance() + account2.getBalance();
        assertThat(bank.getTotalBalance()).isEqualTo(expectedTotal);
    }
    
    // Test for transfer
    @Test
    void transfer_ValidTransfer_ShouldUpdateBalances() throws Exception {
        bank.addAccount(account1);
        bank.addAccount(account2);
        boolean result = bank.transfer("ACC001", "ACC002", 50.0);
        assertThat(result).isTrue();
        assertThat(account1.getBalance()).isEqualTo(50.0);
        assertThat(account2.getBalance()).isEqualTo(250.0);
    }
    
    @Test
    void transfer_InvalidSourceAccount_ShouldThrowException() {
        bank.addAccount(account2);
        Throwable thrown = catchThrowable(() -> bank.transfer("NONEXISTENT", "ACC002", 10.0));
        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid account number(s).");
    }
    
    @Test
    void transfer_InvalidDestinationAccount_ShouldThrowException() {
        bank.addAccount(account1);
        Throwable thrown = catchThrowable(() -> bank.transfer("ACC001", "NONEXISTENT", 10.0));
        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid account number(s).");
    }
  
    @Test
    void transfer_InsufficientFunds_ShouldRollbackBalances() {
        // account1 has 100, attempt to withdraw more than balance
        bank.addAccount(account1);
        bank.addAccount(account2);
        double initialBalanceAcc1 = account1.getBalance();
        double initialBalanceAcc2 = account2.getBalance();
  
        Throwable thrown = catchThrowable(() -> bank.transfer("ACC001", "ACC002", 150.0));
        assertThat(thrown).isInstanceOf(Exception.class);
        // Verify rollback: balances remain unchanged
        assertThat(account1.getBalance()).isEqualTo(initialBalanceAcc1);
        assertThat(account2.getBalance()).isEqualTo(initialBalanceAcc2);
    }
    
    // Test for executeTransaction
    @Test
    void executeTransaction_SuccessfulTransaction_ShouldCommitChanges() throws Exception {
        bank.addAccount(account1);
        bank.addAccount(account2);
  
        double transferAmount = 40.0;
        Bank.TransactionAction action = () -> {
            account1.withdraw(transferAmount);
            account2.deposit(transferAmount);
        };
  
        bank.executeTransaction(action);
        assertThat(account1.getBalance()).isEqualTo(60.0);
        assertThat(account2.getBalance()).isEqualTo(240.0);
    }
    
    @Test
    void executeTransaction_FailedTransaction_ShouldRollbackChanges() {
        bank.addAccount(account1);
        bank.addAccount(account2);
  
        double originalBalanceAcc1 = account1.getBalance();
        double originalBalanceAcc2 = account2.getBalance();
        Bank.TransactionAction action = () -> {
            account1.withdraw(50.0);
            account2.deposit(50.0);
            // Force failure after partial transaction
            throw new Exception("Transaction failed.");
        };
  
        Throwable thrown = catchThrowable(() -> bank.executeTransaction(action));
        assertThat(thrown).isInstanceOf(Exception.class).hasMessage("Transaction failed.");
        // Verify rollback: balances remain unchanged.
        assertThat(account1.getBalance()).isEqualTo(originalBalanceAcc1);
        assertThat(account2.getBalance()).isEqualTo(originalBalanceAcc2);
    }
}