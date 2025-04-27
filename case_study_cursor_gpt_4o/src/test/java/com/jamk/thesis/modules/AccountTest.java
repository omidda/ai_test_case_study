package com.jamk.thesis.modules;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account("123456", "John Doe", 100.0);
    }

    @Test
    public void testConstructorWithValidInitialBalance() {
        Account newAccount = new Account("654321", "Jane Doe", 200.0);
        assertEquals("654321", newAccount.getAccountNumber());
        assertEquals("Jane Doe", newAccount.getOwner());
        assertEquals(200.0, newAccount.getBalance());
    }

    @Test
    public void testConstructorWithNegativeInitialBalance() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Account("654321", "Jane Doe", -100.0);
        });
    }

    @Test
    public void testDepositWithValidAmount() {
        account.deposit(50.0);
        assertEquals(150.0, account.getBalance());
    }

    @Test
    public void testDepositWithZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(0);
        });
    }

    @Test
    public void testDepositWithNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-50.0);
        });
    }

    @Test
    public void testWithdrawWithValidAmount() {
        account.withdraw(50.0);
        assertEquals(50.0, account.getBalance());
    }

    @Test
    public void testWithdrawWithZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(0);
        });
    }

    @Test
    public void testWithdrawWithNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-50.0);
        });
    }

    @Test
    public void testWithdrawWithInsufficientBalance() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(150.0);
        });
    }

    @Test
    public void testSetBalance() {
        account.setBalance(200.0);
        assertEquals(200.0, account.getBalance());
    }
} 