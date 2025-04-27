package com.jamk.thesis.modules;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private static final double DELTA = 1e-10;
    private static final String VALID_ACCOUNT_NUMBER = "ACC123";
    private static final String VALID_OWNER = "John Doe";
    private static final double VALID_INITIAL_BALANCE = 1000.0;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(VALID_ACCOUNT_NUMBER, VALID_OWNER, VALID_INITIAL_BALANCE);
    }

    // Constructor Tests
    @Test
    @DisplayName("Should create account with valid parameters")
    void constructor_WithValidParameters_ShouldCreateAccount() {
        Account newAccount = new Account("ACC456", "Jane Doe", 500.0);
        
        assertEquals("ACC456", newAccount.getAccountNumber());
        assertEquals("Jane Doe", newAccount.getOwner());
        assertEquals(500.0, newAccount.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Should create account with zero initial balance")
    void constructor_WithZeroBalance_ShouldCreateAccount() {
        Account newAccount = new Account("ACC789", "Alice Smith", 0.0);
        assertEquals(0.0, newAccount.getBalance(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -100.0, -0.01})
    @DisplayName("Should throw exception for negative initial balance")
    void constructor_WithNegativeBalance_ShouldThrowException(double negativeBalance) {
        assertThrows(IllegalArgumentException.class, 
            () -> new Account("ACC999", "Bob Wilson", negativeBalance));
    }

    // Getter Tests
    @Test
    @DisplayName("Should return correct account number")
    void getAccountNumber_ShouldReturnCorrectValue() {
        assertEquals(VALID_ACCOUNT_NUMBER, account.getAccountNumber());
    }

    @Test
    @DisplayName("Should return correct owner name")
    void getOwner_ShouldReturnCorrectValue() {
        assertEquals(VALID_OWNER, account.getOwner());
    }

    @Test
    @DisplayName("Should return correct balance")
    void getBalance_ShouldReturnCorrectValue() {
        assertEquals(VALID_INITIAL_BALANCE, account.getBalance(), DELTA);
    }

    // Deposit Tests
    @ParameterizedTest
    @CsvSource({
        "100.0, 1100.0",
        "0.01, 1000.01",
        "9999.99, 10999.99"
    })
    @DisplayName("Should correctly deposit valid amounts")
    void deposit_WithValidAmount_ShouldIncreaseBalance(double depositAmount, double expectedBalance) {
        account.deposit(depositAmount);
        assertEquals(expectedBalance, account.getBalance(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, -100.0})
    @DisplayName("Should throw exception for invalid deposit amounts")
    void deposit_WithInvalidAmount_ShouldThrowException(double invalidAmount) {
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> account.deposit(invalidAmount));
        assertEquals("Deposit amount must be positive.", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle multiple deposits correctly")
    void deposit_MultipleDeposits_ShouldAccumulateCorrectly() {
        account.deposit(100.0);
        account.deposit(200.0);
        account.deposit(300.0);
        assertEquals(1600.0, account.getBalance(), DELTA);
    }

    // Withdrawal Tests
    @ParameterizedTest
    @CsvSource({
        "100.0, 900.0",
        "1000.0, 0.0",
        "0.01, 999.99"
    })
    @DisplayName("Should correctly withdraw valid amounts")
    void withdraw_WithValidAmount_ShouldDecreaseBalance(double withdrawAmount, double expectedBalance) {
        account.withdraw(withdrawAmount);
        assertEquals(expectedBalance, account.getBalance(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, -100.0})
    @DisplayName("Should throw exception for invalid withdrawal amounts")
    void withdraw_WithInvalidAmount_ShouldThrowException(double invalidAmount) {
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> account.withdraw(invalidAmount));
        assertEquals("Withdrawal amount must be positive.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when withdrawing more than balance")
    void withdraw_ExceedingBalance_ShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> account.withdraw(VALID_INITIAL_BALANCE + 0.01));
        assertEquals("Insufficient balance.", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle multiple withdrawals correctly")
    void withdraw_MultipleWithdrawals_ShouldDecreaseCorrectly() {
        account.withdraw(100.0);
        account.withdraw(200.0);
        account.withdraw(300.0);
        assertEquals(400.0, account.getBalance(), DELTA);
    }

    // Balance Setter Tests (for transaction support)
    @Test
    @DisplayName("Should correctly set new balance")
    void setBalance_WithNewValue_ShouldUpdateBalance() {
        account.setBalance(2000.0);
        assertEquals(2000.0, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Should allow setting zero balance")
    void setBalance_WithZero_ShouldUpdateBalance() {
        account.setBalance(0.0);
        assertEquals(0.0, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Should allow setting negative balance for transaction rollback")
    void setBalance_WithNegative_ShouldUpdateBalance() {
        account.setBalance(-100.0);
        assertEquals(-100.0, account.getBalance(), DELTA);
    }

    // Complex Transaction Tests
    @Test
    @DisplayName("Should maintain consistency after multiple operations")
    void multipleOperations_ShouldMaintainConsistency() {
        account.deposit(500.0);    // 1500.0
        account.withdraw(200.0);   // 1300.0
        account.deposit(700.0);    // 2000.0
        account.withdraw(1500.0);  // 500.0
        
        assertEquals(500.0, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Should handle decimal precision correctly")
    void operations_WithDecimalValues_ShouldMaintainPrecision() {
        account.deposit(0.01);
        account.deposit(0.02);
        account.withdraw(0.02);
        assertEquals(1000.01, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Should handle boundary values correctly")
    void operations_WithBoundaryValues_ShouldWorkCorrectly() {
        // Setup account with large initial balance
        Account largeAccount = new Account("ACC999", "Rich Person", Double.MAX_VALUE / 2);
        
        // Should handle very large deposits
        largeAccount.deposit(Double.MAX_VALUE / 4);
        assertTrue(largeAccount.getBalance() > 0);
        
        // Should handle very small operations
        largeAccount.deposit(Double.MIN_VALUE);
        largeAccount.withdraw(Double.MIN_VALUE);
    }
} 