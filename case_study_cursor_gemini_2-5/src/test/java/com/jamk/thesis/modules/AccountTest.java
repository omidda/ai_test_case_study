package com.jamk.thesis.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Account Class Tests")
class AccountTest {

    private static final String TEST_ACCOUNT_NUMBER = "123456789";
    private static final String TEST_OWNER = "Test Owner";
    private static final double INITIAL_BALANCE = 100.0;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(TEST_ACCOUNT_NUMBER, TEST_OWNER, INITIAL_BALANCE);
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create account with positive initial balance")
        void constructor_whenPositiveInitialBalance_shouldCreateAccount() {
            Account newAccount = new Account("ACC001", "Owner One", 500.0);
            assertNotNull(newAccount);
            assertEquals("ACC001", newAccount.getAccountNumber());
            assertEquals("Owner One", newAccount.getOwner());
            assertEquals(500.0, newAccount.getBalance());
        }

        @Test
        @DisplayName("Should create account with zero initial balance")
        void constructor_whenZeroInitialBalance_shouldCreateAccount() {
            Account newAccount = new Account("ACC002", "Owner Two", 0.0);
            assertNotNull(newAccount);
            assertEquals(0.0, newAccount.getBalance());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for negative initial balance")
        void constructor_whenNegativeInitialBalance_shouldThrowIllegalArgumentException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new Account("ACC003", "Owner Three", -100.0);
            });
            assertEquals("Initial balance cannot be negative.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Getter Method Tests")
    class GetterTests {

        @Test
        @DisplayName("getAccountNumber should return correct account number")
        void getAccountNumber_shouldReturnCorrectValue() {
            assertEquals(TEST_ACCOUNT_NUMBER, account.getAccountNumber());
        }

        @Test
        @DisplayName("getOwner should return correct owner name")
        void getOwner_shouldReturnCorrectValue() {
            assertEquals(TEST_OWNER, account.getOwner());
        }

        @Test
        @DisplayName("getBalance should return correct initial balance")
        void getBalance_shouldReturnCorrectValue() {
            assertEquals(INITIAL_BALANCE, account.getBalance());
        }
    }

    @Nested
    @DisplayName("Deposit Method Tests")
    class DepositTests {

        @Test
        @DisplayName("Should increase balance on positive deposit")
        void deposit_whenAmountIsPositive_shouldIncreaseBalance() {
            account.deposit(50.0);
            assertEquals(INITIAL_BALANCE + 50.0, account.getBalance());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for zero deposit amount")
        void deposit_whenAmountIsZero_shouldThrowIllegalArgumentException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                account.deposit(0.0);
            });
            assertEquals("Deposit amount must be positive.", exception.getMessage());
            assertEquals(INITIAL_BALANCE, account.getBalance()); // Balance should remain unchanged
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for negative deposit amount")
        void deposit_whenAmountIsNegative_shouldThrowIllegalArgumentException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                account.deposit(-50.0);
            });
            assertEquals("Deposit amount must be positive.", exception.getMessage());
            assertEquals(INITIAL_BALANCE, account.getBalance()); // Balance should remain unchanged
        }
         @Test
        @DisplayName("Should handle large deposit amounts")
        void deposit_whenAmountIsLarge_shouldIncreaseBalanceCorrectly() {
            double largeDeposit = 1_000_000_000.0;
            account.deposit(largeDeposit);
            assertEquals(INITIAL_BALANCE + largeDeposit, account.getBalance());
        }

        @Test
        @DisplayName("Should handle deposit resulting in very large balance")
        void deposit_whenResultingBalanceIsLarge_shouldUpdateCorrectly() {
             Account largeBalanceAccount = new Account("ACC_LARGE", "Large Owner", Double.MAX_VALUE - 100.0);
             largeBalanceAccount.deposit(50.0);
             assertEquals(Double.MAX_VALUE - 50.0, largeBalanceAccount.getBalance());
        }
    }

    @Nested
    @DisplayName("Withdraw Method Tests")
    class WithdrawTests {

        @Test
        @DisplayName("Should decrease balance on valid withdrawal")
        void withdraw_whenAmountIsValidAndSufficientBalance_shouldDecreaseBalance() {
            account.withdraw(30.0);
            assertEquals(INITIAL_BALANCE - 30.0, account.getBalance());
        }

        @Test
        @DisplayName("Should allow withdrawing entire balance")
        void withdraw_whenAmountEqualsBalance_shouldResultInZeroBalance() {
            account.withdraw(INITIAL_BALANCE);
            assertEquals(0.0, account.getBalance());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for zero withdrawal amount")
        void withdraw_whenAmountIsZero_shouldThrowIllegalArgumentException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                account.withdraw(0.0);
            });
            assertEquals("Withdrawal amount must be positive.", exception.getMessage());
            assertEquals(INITIAL_BALANCE, account.getBalance()); // Balance unchanged
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for negative withdrawal amount")
        void withdraw_whenAmountIsNegative_shouldThrowIllegalArgumentException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                account.withdraw(-30.0);
            });
            assertEquals("Withdrawal amount must be positive.", exception.getMessage());
            assertEquals(INITIAL_BALANCE, account.getBalance()); // Balance unchanged
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for insufficient balance")
        void withdraw_whenAmountExceedsBalance_shouldThrowIllegalArgumentException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                account.withdraw(INITIAL_BALANCE + 0.01);
            });
            assertEquals("Insufficient balance.", exception.getMessage());
            assertEquals(INITIAL_BALANCE, account.getBalance()); // Balance unchanged
        }
         @Test
        @DisplayName("Should handle large withdrawal amounts")
        void withdraw_whenAmountIsLargeAndSufficient_shouldDecreaseBalanceCorrectly() {
             Account richAccount = new Account("RICH001", "Rich Owner", 2000.0);
             richAccount.withdraw(1500.0);
             assertEquals(500.0, richAccount.getBalance());
        }
    }

    @Nested
    @DisplayName("setBalance Method Tests")
    class SetBalanceTests {

        @Test
        @DisplayName("Should update balance to a new positive value")
        void setBalance_whenNewBalanceIsPositive_shouldUpdateBalance() {
            account.setBalance(500.0);
            assertEquals(500.0, account.getBalance());
        }

        @Test
        @DisplayName("Should update balance to zero")
        void setBalance_whenNewBalanceIsZero_shouldUpdateBalance() {
            account.setBalance(0.0);
            assertEquals(0.0, account.getBalance());
        }

        @Test
        @DisplayName("Should allow setting a negative balance")
        void setBalance_whenNewBalanceIsNegative_shouldUpdateBalance() {
            // Note: The method allows this, although constructor prevents negative initial balance.
            // This test verifies the method's behavior as written.
            account.setBalance(-50.0);
            assertEquals(-50.0, account.getBalance());
        }

         @Test
        @DisplayName("Should handle setting balance to a large value")
        void setBalance_whenNewBalanceIsLarge_shouldUpdateBalanceCorrectly() {
            double largeBalance = 9_876_543_210.99;
            account.setBalance(largeBalance);
            assertEquals(largeBalance, account.getBalance());
        }
    }
} 