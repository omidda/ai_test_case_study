package com.jamk.thesis.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void testAddAccountValidAccount() {
        Account account = new Account("12345", "John Doe", 100.0);
        bank.addAccount(account);
        assertEquals(account, bank.getAccount("12345"));
    }

    @Test
    void testAddAccountDuplicateAccountThrowsException() {
        Account account = new Account("12345", "John Doe", 100.0);
        bank.addAccount(account);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            bank.addAccount(account)
        );
        assertEquals("Account already exists.", exception.getMessage());
    }

    @Test
    void testGetAccountValidAccountNumber() {
        Account account = new Account("12345", "John Doe", 100.0);
        bank.addAccount(account);
        assertEquals(account, bank.getAccount("12345"));
    }

    @Test
    void testGetAccountInvalidAccountNumberReturnsNull() {
        assertNull(bank.getAccount("99999"));
    }

    @Test
    void testGetTotalBalanceWithMultipleAccounts() {
        bank.addAccount(new Account("12345", "John Doe", 100.0));
        bank.addAccount(new Account("67890", "Jane Doe", 200.0));
        assertEquals(300.0, bank.getTotalBalance());
    }

    @Test
    void testTransferValidTransaction() throws Exception {
        Account src = new Account("12345", "John Doe", 100.0);
        Account dest = new Account("67890", "Jane Doe", 50.0);
        bank.addAccount(src);
        bank.addAccount(dest);

        boolean result = bank.transfer("12345", "67890", 50.0);

        assertTrue(result);
        assertEquals(50.0, src.getBalance());
        assertEquals(100.0, dest.getBalance());
    }

    @Test
    void testTransferInvalidSourceAccountThrowsException() {
        Account dest = new Account("67890", "Jane Doe", 50.0);
        bank.addAccount(dest);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            bank.transfer("12345", "67890", 50.0)
        );
        assertEquals("Invalid account number(s).", exception.getMessage());
    }

    @Test
    void testTransferInvalidDestinationAccountThrowsException() {
        Account src = new Account("12345", "John Doe", 100.0);
        bank.addAccount(src);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            bank.transfer("12345", "67890", 50.0)
        );
        assertEquals("Invalid account number(s).", exception.getMessage());
    }

    @Test
    void testTransferInsufficientBalanceThrowsException() {
        Account src = new Account("12345", "John Doe", 50.0);
        Account dest = new Account("67890", "Jane Doe", 50.0);
        bank.addAccount(src);
        bank.addAccount(dest);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            bank.transfer("12345", "67890", 100.0)
        );
        assertEquals("Insufficient balance.", exception.getMessage());
    }

    @Test
    void testTransferRollbackOnException() {
        Account src = new Account("12345", "John Doe", 100.0);
        Account dest = new Account("67890", "Jane Doe", 50.0);
        bank.addAccount(src);
        bank.addAccount(dest);

        Exception exception = assertThrows(Exception.class, () ->
            bank.transfer("12345", "67890", -50.0)
        );

        assertEquals(100.0, src.getBalance());
        assertEquals(50.0, dest.getBalance());
    }

    @Test
    void testExecuteTransactionValidAction() throws Exception {
        Account account = new Account("12345", "John Doe", 100.0);
        bank.addAccount(account);

        bank.executeTransaction(() -> account.withdraw(50.0));

        assertEquals(50.0, account.getBalance());
    }

    @Test
    void testExecuteTransactionRollbackOnException() {
        Account account = new Account("12345", "John Doe", 100.0);
        bank.addAccount(account);

        Exception exception = assertThrows(Exception.class, () ->
            bank.executeTransaction(() -> {
                account.withdraw(50.0);
                throw new RuntimeException("Transaction failed");
            })
        );

        assertEquals(100.0, account.getBalance());
    }

    // Invalid test cases

//    @Test
//    void testCreateSnapshot() {
//        Account account1 = new Account("12345", "John Doe", 100.0);
//        Account account2 = new Account("67890", "Jane Doe", 200.0);
//        bank.addAccount(account1);
//        bank.addAccount(account2);
//
//        Map<Account, Double> snapshot = bank.createSnapshot();
//
//        assertEquals(100.0, snapshot.get(account1));
//        assertEquals(200.0, snapshot.get(account2));
//    }
//
//    @Test
//    void testRestoreSnapshot() {
//        Account account1 = new Account("12345", "John Doe", 100.0);
//        Account account2 = new Account("67890", "Jane Doe", 200.0);
//        bank.addAccount(account1);
//        bank.addAccount(account2);
//
//        Map<Account, Double> snapshot = bank.createSnapshot();
//        account1.setBalance(50.0);
//        account2.setBalance(150.0);
//
//        bank.restoreSnapshot(snapshot);
//
//        assertEquals(100.0, account1.getBalance());
//        assertEquals(200.0, account2.getBalance());
//    }
}