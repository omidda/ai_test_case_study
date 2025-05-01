package com.jamk.thesis.modules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AccountTest {
    /**
     * Test {@link Account#Account(String, String, double)}.
     * <ul>
     *   <li>When {@code -1.0E-10}.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Account#Account(String, String, double)}
     */
    @Test
    @DisplayName("Test new Account(String, String, double); when '-1.0E-10'; then throw IllegalArgumentException")
    @Tag("MaintainedByDiffblue")
    void testNewAccount_when10e10_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Account("42", "Owner", -1.0E-10d));

    }

    /**
     * Test {@link Account#Account(String, String, double)}.
     * <ul>
     *   <li>When ten.</li>
     *   <li>Then return AccountNumber is {@code 42}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Account#Account(String, String, double)}
     */
    @Test
    @DisplayName("Test new Account(String, String, double); when ten; then return AccountNumber is '42'")
    @Tag("MaintainedByDiffblue")
    void testNewAccount_whenTen_thenReturnAccountNumberIs42() {
        // Arrange and Act
        Account actualAccount = new Account("42", "Owner", 10.0d);

        // Assert
        assertEquals("42", actualAccount.getAccountNumber());
        assertEquals("Owner", actualAccount.getOwner());
        assertEquals(10.0d, actualAccount.getBalance());
    }

    /**
     * Test getters and setters.
     * <p>
     * Methods under test:
     * <ul>
     *   <li>{@link Account#setBalance(double)}
     *   <li>{@link Account#getAccountNumber()}
     *   <li>{@link Account#getBalance()}
     *   <li>{@link Account#getOwner()}
     * </ul>
     */
    @Test
    @DisplayName("Test getters and setters")
    @Tag("MaintainedByDiffblue")
    void testGettersAndSetters() {
        // Arrange
        Account account = new Account("42", "Owner", 10.0d);

        // Act
        account.setBalance(10.0d);
        String actualAccountNumber = account.getAccountNumber();
        double actualBalance = account.getBalance();

        // Assert
        assertEquals("42", actualAccountNumber);
        assertEquals("Owner", account.getOwner());
        assertEquals(10.0d, actualBalance);
    }

    /**
     * Test {@link Account#deposit(double)}.
     * <p>
     * Method under test: {@link Account#deposit(double)}
     */
    @Test
    @DisplayName("Test deposit(double)")
    @Tag("MaintainedByDiffblue")
    void testDeposit() {
        // Arrange
        Account account = new Account("42", "Owner", 10.0d);

        // Act
        account.deposit(10.0d);

        // Assert
        assertEquals(20.0d, account.getBalance());
    }

    /**
     * Test {@link Account#deposit(double)}.
     * <ul>
     *   <li>When zero.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Account#deposit(double)}
     */
    @Test
    @DisplayName("Test deposit(double); when zero; then throw IllegalArgumentException")
    @Tag("MaintainedByDiffblue")
    void testDeposit_whenZero_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Account("42", "Owner", 10.0d)).deposit(0.0d));
    }

    /**
     * Test {@link Account#withdraw(double)}.
     * <p>
     * Method under test: {@link Account#withdraw(double)}
     */
    @Test
    @DisplayName("Test withdraw(double)")
    @Tag("MaintainedByDiffblue")
    void testWithdraw() {
        // Arrange
        Account account = new Account("42", "Owner", 10.0d);

        // Act
        account.withdraw(10.0d);

        // Assert
        assertEquals(0.0d, account.getBalance());
    }

    /**
     * Test {@link Account#withdraw(double)}.
     * <ul>
     *   <li>Given {@link Account#Account(String, String, double)} with accountNumber is {@code 42} and {@code Owner} and initialBalance is zero.</li>
     * </ul>
     * <p>
     * Method under test: {@link Account#withdraw(double)}
     */
    @Test
    @DisplayName("Test withdraw(double); given Account(String, String, double) with accountNumber is '42' and 'Owner' and initialBalance is zero")
    @Tag("MaintainedByDiffblue")
    void testWithdraw_givenAccountWithAccountNumberIs42AndOwnerAndInitialBalanceIsZero() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Account("42", "Owner", 0.0d)).withdraw(10.0d));
    }

    /**
     * Test {@link Account#withdraw(double)}.
     * <ul>
     *   <li>When zero.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Account#withdraw(double)}
     */
    @Test
    @DisplayName("Test withdraw(double); when zero; then throw IllegalArgumentException")
    @Tag("MaintainedByDiffblue")
    void testWithdraw_whenZero_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> (new Account("42", "Owner", 10.0d)).withdraw(0.0d));
    }
}
