package com.jamk.thesis.modules;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    private Bank bank;
    private Account testAccount1;
    private Account testAccount2;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        testAccount1 = new Account("ACC001", "John Doe", 1000.0);
        testAccount2 = new Account("ACC002", "Jane Doe", 2000.0);
    }

    // Account Addition Tests
    @Test
    @DisplayName("Should successfully add a valid account")
    void addAccount_WithValidAccount_ShouldAddSuccessfully() {
        bank.addAccount(testAccount1);
        Account retrieved = bank.getAccount(testAccount1.getAccountNumber());
        assertEquals(testAccount1, retrieved);
    }

    @Test
    @DisplayName("Should throw exception when adding duplicate account")
    void addAccount_WithDuplicateAccount_ShouldThrowIllegalArgumentException() {
        bank.addAccount(testAccount1);
        Account duplicateAccount = new Account(testAccount1.getAccountNumber(), "Different Name", 500.0);
        assertThrows(IllegalArgumentException.class, () -> bank.addAccount(duplicateAccount));
    }

    // Account Retrieval Tests
    @Test
    @DisplayName("Should return null for non-existent account")
    void getAccount_WithNonExistentAccount_ShouldReturnNull() {
        assertNull(bank.getAccount("NON_EXISTENT"));
    }

    @Test
    @DisplayName("Should retrieve correct account by account number")
    void getAccount_WithExistingAccount_ShouldReturnCorrectAccount() {
        bank.addAccount(testAccount1);
        bank.addAccount(testAccount2);
        
        Account retrieved = bank.getAccount(testAccount1.getAccountNumber());
        assertEquals(testAccount1.getAccountNumber(), retrieved.getAccountNumber());
        assertEquals(testAccount1.getOwner(), retrieved.getOwner());
        assertEquals(testAccount1.getBalance(), retrieved.getBalance());
    }

    // Balance Calculation Tests
    @Test
    @DisplayName("Should return zero total balance for empty bank")
    void getTotalBalance_WithNoAccounts_ShouldReturnZero() {
        assertEquals(0.0, bank.getTotalBalance());
    }

    @Test
    @DisplayName("Should calculate correct total balance for multiple accounts")
    void getTotalBalance_WithMultipleAccounts_ShouldReturnCorrectSum() {
        bank.addAccount(testAccount1); // 1000.0
        bank.addAccount(testAccount2); // 2000.0
        assertEquals(3000.0, bank.getTotalBalance());
    }

    // Transfer Tests
    @Test
    @DisplayName("Should successfully transfer valid amount between accounts")
    void transfer_WithValidAmount_ShouldSucceed() throws Exception {
        bank.addAccount(testAccount1);
        bank.addAccount(testAccount2);

        boolean result = bank.transfer(testAccount1.getAccountNumber(), 
                                     testAccount2.getAccountNumber(), 
                                     500.0);

        assertTrue(result);
        assertEquals(500.0, testAccount1.getBalance());
        assertEquals(2500.0, testAccount2.getBalance());
    }

    @Test
    @DisplayName("Should fail transfer with insufficient funds and rollback")
    void transfer_WithInsufficientFunds_ShouldFailAndRollback() {
        bank.addAccount(testAccount1);
        bank.addAccount(testAccount2);
        
        double initialBalance1 = testAccount1.getBalance();
        double initialBalance2 = testAccount2.getBalance();

        Exception exception = assertThrows(Exception.class, 
            () -> bank.transfer(testAccount1.getAccountNumber(), 
                              testAccount2.getAccountNumber(), 
                              2000.0));
        
        assertEquals("Insufficient balance.", exception.getMessage());
        assertEquals(initialBalance1, testAccount1.getBalance());
        assertEquals(initialBalance2, testAccount2.getBalance());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -100.0})
    @DisplayName("Should reject invalid transfer amounts")
    void transfer_WithInvalidAmount_ShouldThrowException(double invalidAmount) {
        bank.addAccount(testAccount1);
        bank.addAccount(testAccount2);

        assertThrows(IllegalArgumentException.class, 
            () -> bank.transfer(testAccount1.getAccountNumber(), 
                              testAccount2.getAccountNumber(), 
                              invalidAmount));
    }

    @Test
    @DisplayName("Should fail transfer with invalid account numbers")
    void transfer_WithInvalidAccounts_ShouldThrowException() {
        bank.addAccount(testAccount1);

        assertThrows(IllegalArgumentException.class, 
            () -> bank.transfer("INVALID", testAccount1.getAccountNumber(), 100.0));
        assertThrows(IllegalArgumentException.class, 
            () -> bank.transfer(testAccount1.getAccountNumber(), "INVALID", 100.0));
    }

    // Custom Transaction Tests
    @Test
    @DisplayName("Should execute custom transaction successfully")
    void executeTransaction_WithValidOperation_ShouldSucceed() throws Exception {
        bank.addAccount(testAccount1);
        bank.addAccount(testAccount2);

        bank.executeTransaction(() -> {
            testAccount1.withdraw(300.0);
            testAccount2.deposit(300.0);
        });

        assertEquals(700.0, testAccount1.getBalance());
        assertEquals(2300.0, testAccount2.getBalance());
    }

    @Test
    @DisplayName("Should rollback failed custom transaction")
    void executeTransaction_WithFailingOperation_ShouldRollback() {
        bank.addAccount(testAccount1);
        bank.addAccount(testAccount2);

        double initialBalance1 = testAccount1.getBalance();
        double initialBalance2 = testAccount2.getBalance();

        assertThrows(Exception.class, () -> {
            bank.executeTransaction(() -> {
                testAccount1.withdraw(200.0);
                testAccount2.deposit(200.0);
                throw new RuntimeException("Simulated failure");
            });
        });

        assertEquals(initialBalance1, testAccount1.getBalance());
        assertEquals(initialBalance2, testAccount2.getBalance());
    }

    // Concurrency Tests
    @Test
    @DisplayName("Should handle concurrent transfers safely")
    void concurrentTransfers_ShouldMaintainConsistency() throws Exception {
        int numThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        
        bank.addAccount(testAccount1);
        bank.addAccount(testAccount2);
        
        double initialTotalBalance = bank.getTotalBalance();

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                try {
                    bank.transfer(testAccount1.getAccountNumber(), 
                                testAccount2.getAccountNumber(), 
                                10.0);
                } catch (Exception e) {
                    // Expected some transfers might fail due to race conditions
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        
        assertEquals(initialTotalBalance, bank.getTotalBalance(), 
                    "Total balance should remain constant after concurrent transfers");
    }

    // Edge Cases and Boundary Tests
    @Test
    @DisplayName("Should handle decimal precision correctly in transfers")
    void transfer_WithDecimalAmounts_ShouldMaintainPrecision() throws Exception {
        bank.addAccount(testAccount1);
        bank.addAccount(testAccount2);

        bank.transfer(testAccount1.getAccountNumber(), 
                     testAccount2.getAccountNumber(), 
                     0.01);

        assertEquals(999.99, testAccount1.getBalance());
        assertEquals(2000.01, testAccount2.getBalance());
    }

    @Test
    @DisplayName("Should maintain consistency after multiple operations")
    void multipleOperations_ShouldMaintainConsistency() throws Exception {
        bank.addAccount(testAccount1);
        bank.addAccount(testAccount2);
        
        double initialTotal = bank.getTotalBalance();

        // Perform multiple operations
        bank.transfer(testAccount1.getAccountNumber(), testAccount2.getAccountNumber(), 100.0);
        bank.transfer(testAccount2.getAccountNumber(), testAccount1.getAccountNumber(), 50.0);
        bank.executeTransaction(() -> {
            testAccount1.deposit(200.0);
            testAccount2.withdraw(200.0);
        });

        assertEquals(initialTotal, bank.getTotalBalance(), 
                    "Total balance should remain constant after multiple operations");
    }

    // Parameterized Tests for Transfer Scenarios
    static Stream<Arguments> transferScenarios() {
        return Stream.of(
            Arguments.of(1000.0, 1000.0, 500.0, true),  // Standard transfer
            Arguments.of(1000.0, 1000.0, 1000.0, true), // Exact amount
            Arguments.of(1000.0, 1000.0, 1000.1, false) // Just over limit
        );
    }

    @ParameterizedTest
    @MethodSource("transferScenarios")
    @DisplayName("Should handle various transfer scenarios correctly")
    void transfer_WithVariousScenarios_ShouldBehaveCorrectly(
            double sourceBalance, double destBalance, 
            double transferAmount, boolean shouldSucceed) {
        
        Account source = new Account("SRC", "Source", sourceBalance);
        Account dest = new Account("DEST", "Destination", destBalance);
        bank.addAccount(source);
        bank.addAccount(dest);

        if (shouldSucceed) {
            assertDoesNotThrow(() -> bank.transfer("SRC", "DEST", transferAmount));
        } else {
            assertThrows(Exception.class, 
                () -> bank.transfer("SRC", "DEST", transferAmount));
        }
    }
} 