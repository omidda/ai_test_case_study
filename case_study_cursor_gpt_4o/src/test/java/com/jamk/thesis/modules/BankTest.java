package com.jamk.thesis.modules;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

    private Bank bank;
    private Account account1;
    private Account account2;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        account1 = new Account("123456", "John Doe", 100.0);
        account2 = new Account("654321", "Jane Doe", 200.0);
        bank.addAccount(account1);
        bank.addAccount(account2);
    }

    @Test
    public void testAddAccountSuccessfully() {
        Account newAccount = new Account("111111", "Alice", 300.0);
        bank.addAccount(newAccount);
        assertEquals(newAccount, bank.getAccount("111111"));
    }

    @Test
    public void testAddAccountThatAlreadyExists() {
        assertThrows(IllegalArgumentException.class, () -> {
            bank.addAccount(account1);
        });
    }

    @Test
    public void testAddNullAccount() {
        assertThrows(NullPointerException.class, () -> {
            bank.addAccount(null);
        });
    }

    @Test
    public void testGetAccountSuccessfully() {
        assertEquals(account1, bank.getAccount("123456"));
    }

    @Test
    public void testGetNonExistentAccount() {
        assertNull(bank.getAccount("000000"));
    }

    @Test
    public void testGetTotalBalance() {
        assertEquals(300.0, bank.getTotalBalance());
    }

    @Test
    public void testTransferSuccessfully() throws Exception {
        assertTrue(bank.transfer("123456", "654321", 50.0));
        assertEquals(50.0, account1.getBalance());
        assertEquals(250.0, account2.getBalance());
    }

    @Test
    public void testTransferWithInvalidAccountNumbers() {
        assertThrows(IllegalArgumentException.class, () -> {
            bank.transfer("000000", "654321", 50.0);
        });
    }

    @Test
    public void testTransferWithInsufficientBalance() {
        assertThrows(IllegalArgumentException.class, () -> {
            bank.transfer("123456", "654321", 150.0);
        });
    }

    @Test
    public void testExecuteTransactionSuccessfully() throws Exception {
        bank.executeTransaction(() -> {
            account1.withdraw(50.0);
            account2.deposit(50.0);
        });
        assertEquals(50.0, account1.getBalance());
        assertEquals(250.0, account2.getBalance());
    }

    @Test
    public void testExecuteTransactionWithException() {
        assertThrows(Exception.class, () -> {
            bank.executeTransaction(() -> {
                account1.withdraw(150.0); // This will throw an exception
                account2.deposit(50.0);
            });
        });
        // Ensure balances are rolled back
        assertEquals(100.0, account1.getBalance());
        assertEquals(200.0, account2.getBalance());
    }
} 