package com.jamk.thesis.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    
    private Bank bank;
    private static final String ACCOUNT1_NUMBER = "1001";
    private static final String ACCOUNT2_NUMBER = "1002";
    private static final String INVALID_ACCOUNT = "9999";
    private static final double INITIAL_BALANCE = 1000.0;
    private static final double DELTA = 0.001;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Nested
    class AccountManagementTests {
        
        @Test
        void addAccount_WithValidAccount_ShouldSucceed() {
            Account account = new Account(ACCOUNT1_NUMBER, "John Doe", INITIAL_BALANCE);
            
            bank.addAccount(account);
            
            Account retrieved = bank.getAccount(ACCOUNT1_NUMBER);
            assertNotNull(retrieved);
            assertEquals(ACCOUNT1_NUMBER, retrieved.getAccountNumber());
        }

        @Test
        void addAccount_WithDuplicateAccount_ShouldThrowException() {
            Account account1 = new Account(ACCOUNT1_NUMBER, "John Doe", INITIAL_BALANCE);
            Account account2 = new Account(ACCOUNT1_NUMBER, "Jane Doe", INITIAL_BALANCE);
            
            bank.addAccount(account1);
            
            assertThrows(IllegalArgumentException.class, () -> bank.addAccount(account2));
        }

        @Test
        void getAccount_WithNonexistentAccount_ShouldReturnNull() {
            assertNull(bank.getAccount(INVALID_ACCOUNT));
        }
    }

    @Nested
    class BalanceTests {
        
        @Test
        void getTotalBalance_EmptyBank_ShouldReturnZero() {
            assertEquals(0.0, bank.getTotalBalance(), DELTA);
        }

        @Test
        void getTotalBalance_MultipleAccounts_ShouldReturnCorrectSum() {
            Account account1 = new Account(ACCOUNT1_NUMBER, "John Doe", INITIAL_BALANCE);
            Account account2 = new Account(ACCOUNT2_NUMBER, "Jane Doe", INITIAL_BALANCE);
            
            bank.addAccount(account1);
            bank.addAccount(account2);
            
            assertEquals(INITIAL_BALANCE * 2, bank.getTotalBalance(), DELTA);
        }
    }

    @Nested
    class TransactionTests {
        
        @Test
        void transfer_ValidTransaction_ShouldSucceed() throws Exception {
            Account source = new Account(ACCOUNT1_NUMBER, "John Doe", INITIAL_BALANCE);
            Account destination = new Account(ACCOUNT2_NUMBER, "Jane Doe", INITIAL_BALANCE);
            double transferAmount = 500.0;
            
            bank.addAccount(source);
            bank.addAccount(destination);
            
            assertTrue(bank.transfer(ACCOUNT1_NUMBER, ACCOUNT2_NUMBER, transferAmount));
            
            assertEquals(INITIAL_BALANCE - transferAmount, source.getBalance(), DELTA);
            assertEquals(INITIAL_BALANCE + transferAmount, destination.getBalance(), DELTA);
        }

        @Test
        void transfer_WithInvalidSourceAccount_ShouldThrowException() {
            Account destination = new Account(ACCOUNT2_NUMBER, "Jane Doe", INITIAL_BALANCE);
            bank.addAccount(destination);
            
            assertThrows(IllegalArgumentException.class, 
                () -> bank.transfer(INVALID_ACCOUNT, ACCOUNT2_NUMBER, 500.0));
        }

        @Test
        void transfer_WithInvalidDestinationAccount_ShouldThrowException() {
            Account source = new Account(ACCOUNT1_NUMBER, "John Doe", INITIAL_BALANCE);
            bank.addAccount(source);
            
            assertThrows(IllegalArgumentException.class, 
                () -> bank.transfer(ACCOUNT1_NUMBER, INVALID_ACCOUNT, 500.0));
        }

        @Test
        void transfer_WithInsufficientFunds_ShouldRollbackAndThrowException() throws Exception {
            Account source = new Account(ACCOUNT1_NUMBER, "John Doe", INITIAL_BALANCE);
            Account destination = new Account(ACCOUNT2_NUMBER, "Jane Doe", INITIAL_BALANCE);
            double transferAmount = INITIAL_BALANCE + 100.0;
            
            bank.addAccount(source);
            bank.addAccount(destination);
            
            Exception exception = assertThrows(IllegalArgumentException.class, 
                () -> bank.transfer(ACCOUNT1_NUMBER, ACCOUNT2_NUMBER, transferAmount));
            
            // Verify balances were rolled back
            assertEquals(INITIAL_BALANCE, source.getBalance(), DELTA);
            assertEquals(INITIAL_BALANCE, destination.getBalance(), DELTA);
        }
    }

    @Nested
    class CustomTransactionTests {
        
        @Test
        void executeTransaction_SuccessfulTransaction_ShouldCommit() throws Exception {
            Account account = new Account(ACCOUNT1_NUMBER, "John Doe", INITIAL_BALANCE);
            bank.addAccount(account);
            double depositAmount = 500.0;
            
            bank.executeTransaction(() -> account.deposit(depositAmount));
            
            assertEquals(INITIAL_BALANCE + depositAmount, account.getBalance(), DELTA);
        }

        @Test
        void executeTransaction_FailedTransaction_ShouldRollback() {
            Account account = new Account(ACCOUNT1_NUMBER, "John Doe", INITIAL_BALANCE);
            bank.addAccount(account);
            
            assertThrows(IllegalArgumentException.class, () -> 
                bank.executeTransaction(() -> {
                    account.deposit(500.0);
                    account.withdraw(INITIAL_BALANCE + 1000.0); // This should fail
                }));
            
            // Verify the balance was rolled back
            assertEquals(INITIAL_BALANCE, account.getBalance(), DELTA);
        }
        
        @Test
        void executeTransaction_MultipleAccountChanges_ShouldRollbackAll() {
            Account account1 = new Account(ACCOUNT1_NUMBER, "John Doe", INITIAL_BALANCE);
            Account account2 = new Account(ACCOUNT2_NUMBER, "Jane Doe", INITIAL_BALANCE);
            bank.addAccount(account1);
            bank.addAccount(account2);
            
            assertThrows(IllegalArgumentException.class, () -> 
                bank.executeTransaction(() -> {
                    account1.deposit(500.0);
                    account2.deposit(300.0);
                    account1.withdraw(INITIAL_BALANCE + 1000.0); // This should fail
                }));
            
            // Verify both accounts were rolled back
            assertEquals(INITIAL_BALANCE, account1.getBalance(), DELTA);
            assertEquals(INITIAL_BALANCE, account2.getBalance(), DELTA);
        }
    }
}