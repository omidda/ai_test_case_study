package com.jamk.thesis.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bank Class Tests")
class BankTest {

    private Bank bank;
    private Account account1;
    private Account account2;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        account1 = new Account("ACC1", "Owner One", 1000.0);
        account2 = new Account("ACC2", "Owner Two", 500.0);
        bank.addAccount(account1);
        bank.addAccount(account2);
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        @Test
        @DisplayName("Should create an empty bank")
        void constructor_shouldCreateEmptyBank() {
            Bank newBank = new Bank();
            assertNotNull(newBank);
            assertEquals(0.0, newBank.getTotalBalance());
            assertNull(newBank.getAccount("ANY_ACC"));
        }
    }

    @Nested
    @DisplayName("addAccount Method Tests")
    class AddAccountTests {

        @Test
        @DisplayName("Should add a valid account")
        void addAccount_whenAccountIsValid_shouldAddAccount() {
            Account account3 = new Account("ACC3", "Owner Three", 200.0);
            bank.addAccount(account3);
            assertEquals(account3, bank.getAccount("ACC3"));
            assertEquals(1700.0, bank.getTotalBalance());
        }

        @Test
        @DisplayName("Should throw NullPointerException when adding a null account")
        void addAccount_whenAccountIsNull_shouldThrowNullPointerException() {
            NullPointerException exception = assertThrows(NullPointerException.class, () -> {
                bank.addAccount(null);
            });
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when adding an account with existing number")
        void addAccount_whenAccountNumberExists_shouldThrowIllegalArgumentException() {
            Account duplicateAccount = new Account("ACC1", "Another Owner", 50.0);
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                bank.addAccount(duplicateAccount);
            });
            assertEquals("Account already exists.", exception.getMessage());
            // Ensure original account1 is still there and unchanged
            assertEquals(account1, bank.getAccount("ACC1"));
            assertEquals(1000.0, bank.getAccount("ACC1").getBalance());
        }
    }

    @Nested
    @DisplayName("getAccount Method Tests")
    class GetAccountTests {

        @Test
        @DisplayName("Should return the correct account for existing account number")
        void getAccount_whenAccountExists_shouldReturnAccount() {
            assertEquals(account1, bank.getAccount("ACC1"));
            assertEquals(account2, bank.getAccount("ACC2"));
        }

        @Test
        @DisplayName("Should return null for non-existent account number")
        void getAccount_whenAccountDoesNotExist_shouldReturnNull() {
            assertNull(bank.getAccount("NON_EXISTENT"));
        }

        @Test
        @DisplayName("Should return null when searching with null account number")
        void getAccount_whenAccountNumberIsNull_shouldReturnNull() {
             assertNull(bank.getAccount(null));
        }
         @Test
        @DisplayName("Should return null when searching with empty account number")
        void getAccount_whenAccountNumberIsEmpty_shouldReturnNull() {
             assertNull(bank.getAccount(""));
        }
    }

    @Nested
    @DisplayName("getTotalBalance Method Tests")
    class GetTotalBalanceTests {

        @Test
        @DisplayName("Should return correct total balance for multiple accounts")
        void getTotalBalance_whenMultipleAccountsExist_shouldReturnCorrectSum() {
            assertEquals(1500.0, bank.getTotalBalance());
        }

        @Test
        @DisplayName("Should return zero balance for an empty bank")
        void getTotalBalance_whenBankIsEmpty_shouldReturnZero() {
            Bank emptyBank = new Bank();
            assertEquals(0.0, emptyBank.getTotalBalance());
        }

         @Test
        @DisplayName("Should return correct total balance after adding more accounts")
        void getTotalBalance_afterAddingAccounts_shouldReturnCorrectSum() {
             bank.addAccount(new Account("ACC3", "Owner Three", 300.0));
             assertEquals(1800.0, bank.getTotalBalance());
        }

        @Test
        @DisplayName("Should handle accounts with zero balance")
        void getTotalBalance_withZeroBalanceAccounts_shouldCalculateCorrectly() {
            bank.addAccount(new Account("ACC_ZERO", "Zero Owner", 0.0));
            assertEquals(1500.0, bank.getTotalBalance());
        }
    }

    @Nested
    @DisplayName("transfer Method Tests")
    class TransferTests {

        @Test
        @DisplayName("Should successfully transfer amount between two existing accounts")
        void transfer_whenValidAccountsAndSufficientBalance_shouldSucceed() throws Exception {
            boolean result = bank.transfer("ACC1", "ACC2", 200.0);
            assertTrue(result);
            assertEquals(800.0, account1.getBalance());
            assertEquals(700.0, account2.getBalance());
            assertEquals(1500.0, bank.getTotalBalance()); // Total balance unchanged
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException if 'from' account does not exist")
        void transfer_whenFromAccountDoesNotExist_shouldThrowIllegalArgumentException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                bank.transfer("NON_EXISTENT", "ACC2", 100.0);
            });
            assertEquals("Invalid account number(s).", exception.getMessage());
            assertEquals(1000.0, account1.getBalance()); // Balances unchanged
            assertEquals(500.0, account2.getBalance());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException if 'to' account does not exist")
        void transfer_whenToAccountDoesNotExist_shouldThrowIllegalArgumentException() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                bank.transfer("ACC1", "NON_EXISTENT", 100.0);
            });
            assertEquals("Invalid account number(s).", exception.getMessage());
            assertEquals(1000.0, account1.getBalance()); // Balances unchanged
            assertEquals(500.0, account2.getBalance());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException and rollback if insufficient balance")
        void transfer_whenInsufficientBalance_shouldThrowAndRollback() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                bank.transfer("ACC1", "ACC2", 1200.0);
            });
            assertEquals("Insufficient balance.", exception.getMessage());
            assertEquals(1000.0, account1.getBalance()); // Rollback successful
            assertEquals(500.0, account2.getBalance()); // Rollback successful
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException and rollback for zero transfer amount")
        void transfer_whenAmountIsZero_shouldThrowAndRollback() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                bank.transfer("ACC1", "ACC2", 0.0);
            });
            assertEquals("Withdrawal amount must be positive.", exception.getMessage());
            assertEquals(1000.0, account1.getBalance()); // Rollback successful
            assertEquals(500.0, account2.getBalance()); // Rollback successful
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException and rollback for negative transfer amount")
        void transfer_whenAmountIsNegative_shouldThrowAndRollback() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                bank.transfer("ACC1", "ACC2", -100.0);
            });
            assertEquals("Withdrawal amount must be positive.", exception.getMessage());
            assertEquals(1000.0, account1.getBalance()); // Rollback successful
            assertEquals(500.0, account2.getBalance()); // Rollback successful
        }
         @Test
        @DisplayName("Should successfully transfer entire balance")
        void transfer_whenAmountIsTotalBalance_shouldSucceed() throws Exception {
             boolean result = bank.transfer("ACC1", "ACC2", 1000.0);
             assertTrue(result);
             assertEquals(0.0, account1.getBalance());
             assertEquals(1500.0, account2.getBalance());
             assertEquals(1500.0, bank.getTotalBalance());
        }
         @Test
        @DisplayName("Transferring to the same account should throw and rollback (due to withdraw constraint)")
        void transfer_toSameAccount_shouldThrowAndRollback() {
            // The transfer itself *could* work conceptually, but Account.withdraw prevents zero amount
             IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                 bank.transfer("ACC1", "ACC1", 0.0);
             });
             assertEquals("Withdrawal amount must be positive.", exception.getMessage());
             assertEquals(1000.0, account1.getBalance()); // Rollback check

              // Test with positive amount - should work if balance allows
             try {
                 boolean result = bank.transfer("ACC1", "ACC1", 50.0);
                 assertTrue(result);
                 assertEquals(1000.0, account1.getBalance()); // Net effect is zero change
             } catch (Exception e) {
                 fail("Transfer to same account with positive amount failed unexpectedly", e);
             }
        }

    }

    @Nested
    @DisplayName("executeTransaction Method Tests")
    class ExecuteTransactionTests {

        @Test
        @DisplayName("Should execute a successful transaction action")
        void executeTransaction_whenActionSucceeds_shouldCommitChanges() throws Exception {
            bank.executeTransaction(() -> {
                account1.withdraw(100.0);
                account2.deposit(100.0);
            });
            assertEquals(900.0, account1.getBalance());
            assertEquals(600.0, account2.getBalance());
            assertEquals(1500.0, bank.getTotalBalance());
        }

        @Test
        @DisplayName("Should rollback transaction if action throws an exception")
        void executeTransaction_whenActionFails_shouldRollbackChanges() {
            RuntimeException testException = new RuntimeException("Simulated action failure");

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                bank.executeTransaction(() -> {
                    account1.withdraw(100.0); // This succeeds
                    if (true) { // Simulate a condition causing failure
                        throw testException;
                    }
                    account2.deposit(100.0); // This won't be reached
                });
            });

            assertSame(testException, thrown);
            assertEquals(1000.0, account1.getBalance()); // Rollback successful
            assertEquals(500.0, account2.getBalance()); // Rollback successful
            assertEquals(1500.0, bank.getTotalBalance()); // Total balance restored
        }

        @Test
        @DisplayName("Should rollback transaction if withdraw within action fails")
        void executeTransaction_whenWithdrawFails_shouldRollbackChanges() {
             IllegalArgumentException expectedException = new IllegalArgumentException("Insufficient balance.");

             Exception thrown = assertThrows(IllegalArgumentException.class, () -> {
                 bank.executeTransaction(() -> {
                     account2.deposit(50.0); // This succeeds initially
                     account1.withdraw(2000.0); // This will fail
                 });
             });

            assertEquals(expectedException.getMessage(), thrown.getMessage());
            assertEquals(1000.0, account1.getBalance()); // Rollback successful
            assertEquals(500.0, account2.getBalance()); // Rollback successful
            assertEquals(1500.0, bank.getTotalBalance());
        }

          @Test
        @DisplayName("Should execute a complex successful transaction")
        void executeTransaction_withComplexSuccessfulAction_shouldCommit() throws Exception {
             Account account3 = new Account("ACC3", "Owner Three", 200.0);
             bank.addAccount(account3);

             bank.executeTransaction(() -> {
                 account1.withdraw(100.0);
                 account2.deposit(50.0);
                 account3.deposit(50.0);
                 account2.withdraw(25.0);
                 account1.deposit(25.0);
             });

             assertEquals(925.0, account1.getBalance());
             assertEquals(525.0, account2.getBalance());
             assertEquals(250.0, account3.getBalance());
             assertEquals(1700.0, bank.getTotalBalance());
        }

         @Test
        @DisplayName("Should handle empty transaction action")
        void executeTransaction_withEmptyAction_shouldDoNothing() throws Exception {
             bank.executeTransaction(() -> {}); // No operation
             assertEquals(1000.0, account1.getBalance());
             assertEquals(500.0, account2.getBalance());
             assertEquals(1500.0, bank.getTotalBalance());
        }

    }
} 