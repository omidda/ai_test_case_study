package com.jamk.thesis.modules;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private static final String VALID_ACCOUNT_NUMBER = "12345";
    private static final String VALID_OWNER = "John Doe";
    private static final double VALID_INITIAL_BALANCE = 1000.0;
    private static final double DELTA = 0.001; // For double comparisons

    // Constructor Tests
    @Test
    void constructor_WithValidInputs_ShouldCreateAccount() {
        Account account = new Account(VALID_ACCOUNT_NUMBER, VALID_OWNER, VALID_INITIAL_BALANCE);
        
        assertEquals(VALID_ACCOUNT_NUMBER, account.getAccountNumber());
        assertEquals(VALID_OWNER, account.getOwner());
        assertEquals(VALID_INITIAL_BALANCE, account.getBalance(), DELTA);
    }

    @Test
    void constructor_WithZeroBalance_ShouldCreateAccount() {
        Account account = new Account(VALID_ACCOUNT_NUMBER, VALID_OWNER, 0);
        
        assertEquals(0.0, account.getBalance(), DELTA);
    }

    @Test
    void constructor_WithNegativeBalance_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Account(VALID_ACCOUNT_NUMBER, VALID_OWNER, -100));
    }

    // Deposit Tests
    @Test
    void deposit_WithValidAmount_ShouldIncreaseBalance() {
        Account account = new Account(VALID_ACCOUNT_NUMBER, VALID_OWNER, VALID_INITIAL_BALANCE);
        double depositAmount = 500.0;
        
        account.deposit(depositAmount);
        
        assertEquals(VALID_INITIAL_BALANCE + depositAmount, account.getBalance(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, -100.0})
    void deposit_WithInvalidAmount_ShouldThrowException(double invalidAmount) {
        Account account = new Account(VALID_ACCOUNT_NUMBER, VALID_OWNER, VALID_INITIAL_BALANCE);
        
        assertThrows(IllegalArgumentException.class, 
            () -> account.deposit(invalidAmount));
    }

    // Withdraw Tests
    @Test
    void withdraw_WithValidAmount_ShouldDecreaseBalance() {
        Account account = new Account(VALID_ACCOUNT_NUMBER, VALID_OWNER, VALID_INITIAL_BALANCE);
        double withdrawAmount = 500.0;
        
        account.withdraw(withdrawAmount);
        
        assertEquals(VALID_INITIAL_BALANCE - withdrawAmount, account.getBalance(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, -100.0})
    void withdraw_WithInvalidAmount_ShouldThrowException(double invalidAmount) {
        Account account = new Account(VALID_ACCOUNT_NUMBER, VALID_OWNER, VALID_INITIAL_BALANCE);
        
        assertThrows(IllegalArgumentException.class, 
            () -> account.withdraw(invalidAmount));
    }

    @Test
    void withdraw_WithInsufficientFunds_ShouldThrowException() {
        Account account = new Account(VALID_ACCOUNT_NUMBER, VALID_OWNER, VALID_INITIAL_BALANCE);
        
        assertThrows(IllegalArgumentException.class, 
            () -> account.withdraw(VALID_INITIAL_BALANCE + 0.01));
    }

    @Test
    void withdraw_ExactBalance_ShouldSucceed() {
        Account account = new Account(VALID_ACCOUNT_NUMBER, VALID_OWNER, VALID_INITIAL_BALANCE);
        
        account.withdraw(VALID_INITIAL_BALANCE);
        
        assertEquals(0.0, account.getBalance(), DELTA);
    }

    // SetBalance Tests (for transaction support)
    @Test
    void setBalance_ShouldUpdateBalance() {
        Account account = new Account(VALID_ACCOUNT_NUMBER, VALID_OWNER, VALID_INITIAL_BALANCE);
        double newBalance = 2000.0;
        
        account.setBalance(newBalance);
        
        assertEquals(newBalance, account.getBalance(), DELTA);
    }

    @Test
    void setBalance_WithNegativeValue_ShouldUpdate() {
        Account account = new Account(VALID_ACCOUNT_NUMBER, VALID_OWNER, VALID_INITIAL_BALANCE);
        double newBalance = -100.0;
        
        account.setBalance(newBalance);
        
        assertEquals(newBalance, account.getBalance(), DELTA);
    }
}