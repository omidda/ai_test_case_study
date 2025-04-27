package com.jamk.thesis.modules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.jamk.thesis.modules.Bank.TransactionAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BankTest {
    /**
     * Test {@link Bank#addAccount(Account)}.
     * <p>
     * Method under test: {@link Bank#addAccount(Account)}
     */
    @Test
    @DisplayName("Test addAccount(Account)")
    void testAddAccount() {
        // Arrange
        Bank bank = new Bank();

        // Act
        bank.addAccount(new Account("42", "Owner", 10.0d));

        // Assert
        assertEquals(10.0d, bank.getTotalBalance());
    }

    /**
     * Test {@link Bank#getAccount(String)}.
     * <p>
     * Method under test: {@link Bank#getAccount(String)}
     */
    @Test
    @DisplayName("Test getAccount(String)")
    void testGetAccount() {
        // Arrange
        Bank bank = new Bank();
        Account account = new Account("42", "Owner", 10.0d);

        bank.addAccount(account);

        // Act and Assert
        assertSame(account, bank.getAccount("42"));
    }

    /**
     * Test {@link Bank#getAccount(String)}.
     * <p>
     * Method under test: {@link Bank#getAccount(String)}
     */
    @Test
    @DisplayName("Test getAccount(String)")
    void testGetAccount2() {
        // Arrange
        Bank bank = new Bank();
        bank.addAccount(new Account("3", "Owner", 10.0d));

        // Act and Assert
        assertNull(bank.getAccount("42"));
    }

    /**
     * Test {@link Bank#getAccount(String)}.
     * <ul>
     *   <li>Given {@link Bank} (default constructor).</li>
     *   <li>Then return {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Bank#getAccount(String)}
     */
    @Test
    @DisplayName("Test getAccount(String); given Bank (default constructor); then return 'null'")
    void testGetAccount_givenBank_thenReturnNull() {
        // Arrange, Act and Assert
        assertNull((new Bank()).getAccount("42"));
    }

    /**
     * Test {@link Bank#transfer(String, String, double)}.
     * <p>
     * Method under test: {@link Bank#transfer(String, String, double)}
     */
    @Test
    @DisplayName("Test transfer(String, String, double)")
    void testTransfer() throws Exception {
        // Arrange
        Bank bank = new Bank();
        bank.addAccount(new Account("42", "Invalid account number(s).", 10.0d));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> bank.transfer("3", "3", 10.0d));
    }

    /**
     * Test {@link Bank#transfer(String, String, double)}.
     * <ul>
     *   <li>Given {@link Bank} (default constructor).</li>
     *   <li>When {@code 3}.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Bank#transfer(String, String, double)}
     */
    @Test
    @DisplayName("Test transfer(String, String, double); given Bank (default constructor); when '3'; then throw IllegalArgumentException")
    void testTransfer_givenBank_when3_thenThrowIllegalArgumentException() throws Exception {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Bank()).transfer("3", "3", 10.0d));
    }

    /**
     * Test {@link Bank#transfer(String, String, double)}.
     * <ul>
     *   <li>Then return {@code true}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Bank#transfer(String, String, double)}
     */
    @Test
    @DisplayName("Test transfer(String, String, double); then return 'true'")
    void testTransfer_thenReturnTrue() throws Exception {
        // Arrange
        Bank bank = new Bank();
        bank.addAccount(new Account("3", "Invalid account number(s).", 10.0d));

        // Act and Assert
        assertTrue(bank.transfer("3", "3", 10.0d));
    }

    /**
     * Test {@link Bank#transfer(String, String, double)}.
     * <ul>
     *   <li>When {@code 42}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Bank#transfer(String, String, double)}
     */
    @Test
    @DisplayName("Test transfer(String, String, double); when '42'")
    void testTransfer_when42() throws Exception {
        // Arrange
        Bank bank = new Bank();
        bank.addAccount(new Account("42", "Invalid account number(s).", 10.0d));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> bank.transfer("42", "3", 10.0d));
    }

    /**
     * Test {@link Bank#executeTransaction(TransactionAction)}.
     * <p>
     * Method under test: {@link Bank#executeTransaction(Bank.TransactionAction)}
     */
    @Test
    @DisplayName("Test executeTransaction(TransactionAction)")
    void testExecuteTransaction() throws Exception {
        // Arrange
        Bank bank = new Bank();
        bank.addAccount(new Account("42", "Owner", 10.0d));
        Bank.TransactionAction action = mock(Bank.TransactionAction.class);
        doNothing().when(action).execute();

        // Act
        bank.executeTransaction(action);

        // Assert
        verify(action).execute();
    }

    /**
     * Test {@link Bank#executeTransaction(TransactionAction)}.
     * <ul>
     *   <li>Given {@link Bank} (default constructor).</li>
     *   <li>Then throw {@link Exception}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Bank#executeTransaction(Bank.TransactionAction)}
     */
    @Test
    @DisplayName("Test executeTransaction(TransactionAction); given Bank (default constructor); then throw Exception")
    void testExecuteTransaction_givenBank_thenThrowException() throws Exception {
        // Arrange
        Bank bank = new Bank();
        Bank.TransactionAction action = mock(Bank.TransactionAction.class);
        doThrow(new Exception("foo")).when(action).execute();

        // Act and Assert
        assertThrows(Exception.class, () -> bank.executeTransaction(action));
        verify(action).execute();
    }

    /**
     * Test {@link Bank#executeTransaction(TransactionAction)}.
     * <ul>
     *   <li>Given {@link Bank} (default constructor).</li>
     *   <li>When {@link TransactionAction} {@link TransactionAction#execute()} does
     * nothing.</li>
     * </ul>
     * <p>
     * Method under test: {@link Bank#executeTransaction(Bank.TransactionAction)}
     */
    @Test
    @DisplayName("Test executeTransaction(TransactionAction); given Bank (default constructor); when TransactionAction execute() does nothing")
    void testExecuteTransaction_givenBank_whenTransactionActionExecuteDoesNothing() throws Exception {
        // Arrange
        Bank bank = new Bank();
        Bank.TransactionAction action = mock(Bank.TransactionAction.class);
        doNothing().when(action).execute();

        // Act
        bank.executeTransaction(action);

        // Assert that nothing has changed
        verify(action).execute();
    }

    /**
     * Test {@link Bank#executeTransaction(TransactionAction)}.
     * <ul>
     *   <li>Then throw {@link Exception}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Bank#executeTransaction(Bank.TransactionAction)}
     */
    @Test
    @DisplayName("Test executeTransaction(TransactionAction); then throw Exception")
    void testExecuteTransaction_thenThrowException() throws Exception {
        // Arrange
        Bank bank = new Bank();
        bank.addAccount(new Account("42", "Owner", 10.0d));
        Bank.TransactionAction action = mock(Bank.TransactionAction.class);
        doThrow(new Exception("foo")).when(action).execute();

        // Act and Assert
        assertThrows(Exception.class, () -> bank.executeTransaction(action));
        verify(action).execute();
    }

    /**
     * Test new {@link Bank} (default constructor).
     * <p>
     * Method under test: default or parameterless constructor of {@link Bank}
     */
    @Test
    @DisplayName("Test new Bank (default constructor)")
    void testNewBank() {
        // Arrange, Act and Assert
        assertEquals(0.0d, (new Bank()).getTotalBalance());
    }
}
