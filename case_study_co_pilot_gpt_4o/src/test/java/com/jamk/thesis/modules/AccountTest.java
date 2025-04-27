package com.jamk.thesis.modules;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void testConstructorValidAccount() {
        Account account = new Account("12345", "John Doe", 100.0);
        assertEquals("12345", account.getAccountNumber());
        assertEquals("John Doe", account.getOwner());
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void testConstructorNegativeInitialBalanceThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            new Account("12345", "John Doe", -100.0)
        );
        assertEquals("Initial balance cannot be negative.", exception.getMessage());
    }

    @Test
    void testGetAccountNumber() {
        Account account = new Account("12345", "John Doe", 100.0);
        assertEquals("12345", account.getAccountNumber());
    }

    @Test
    void testGetOwner() {
        Account account = new Account("12345", "John Doe", 100.0);
        assertEquals("John Doe", account.getOwner());
    }

    @Test
    void testGetBalance() {
        Account account = new Account("12345", "John Doe", 100.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void testDepositValidAmount() {
        Account account = new Account("12345", "John Doe", 100.0);
        account.deposit(50.0);
        assertEquals(150.0, account.getBalance());
    }

    @Test
    void testDepositZeroThrowsException() {
        Account account = new Account("12345", "John Doe", 100.0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            account.deposit(0.0)
        );
        assertEquals("Deposit amount must be positive.", exception.getMessage());
    }

    @Test
    void testDepositNegativeAmountThrowsException() {
        Account account = new Account("12345", "John Doe", 100.0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            account.deposit(-50.0)
        );
        assertEquals("Deposit amount must be positive.", exception.getMessage());
    }

    @Test
    void testWithdrawValidAmount() {
        Account account = new Account("12345", "John Doe", 100.0);
        account.withdraw(50.0);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    void testWithdrawZeroThrowsException() {
        Account account = new Account("12345", "John Doe", 100.0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            account.withdraw(0.0)
        );
        assertEquals("Withdrawal amount must be positive.", exception.getMessage());
    }

    @Test
    void testWithdrawNegativeAmountThrowsException() {
        Account account = new Account("12345", "John Doe", 100.0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            account.withdraw(-50.0)
        );
        assertEquals("Withdrawal amount must be positive.", exception.getMessage());
    }

    @Test
    void testWithdrawAmountExceedingBalanceThrowsException() {
        Account account = new Account("12345", "John Doe", 100.0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            account.withdraw(150.0)
        );
        assertEquals("Insufficient balance.", exception.getMessage());
    }

    @Test
    void testSetBalanceValidValue() {
        Account account = new Account("12345", "John Doe", 100.0);
        account.setBalance(200.0);
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void testSetBalanceNegativeValue() {
        Account account = new Account("12345", "John Doe", 100.0);
        account.setBalance(-50.0);
        assertEquals(-50.0, account.getBalance());
    }
}