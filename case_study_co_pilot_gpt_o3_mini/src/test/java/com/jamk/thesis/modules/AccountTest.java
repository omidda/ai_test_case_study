package com.jamk.thesis.modules;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;


public class AccountTest {

    @Test
    void constructor_ValidInputs_ShouldInitializeAccountCorrectly() {
        String accountNumber = "ACC123";
        String owner = "John Doe";
        double initialBalance = 100.0;

        Account account = new Account(accountNumber, owner, initialBalance);

        assertThat(account.getAccountNumber()).isEqualTo(accountNumber);
        assertThat(account.getOwner()).isEqualTo(owner);
        assertThat(account.getBalance()).isEqualTo(initialBalance);
    }

    @Test
    void constructor_NegativeInitialBalance_ShouldThrowIllegalArgumentException() {
        String accountNumber = "ACC123";
        String owner = "John Doe";
        double negativeBalance = -50.0;

        Throwable thrown = catchThrowable(() -> new Account(accountNumber, owner, negativeBalance));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Initial balance cannot be negative.");
    }

    @Test
    void deposit_PositiveAmount_ShouldIncreaseBalance() {
        Account account = new Account("ACC123", "John Doe", 100.0);
        double depositAmount = 50.0;

        account.deposit(depositAmount);
        assertThat(account.getBalance()).isEqualTo(150.0);
    }

    @Test
    void deposit_ZeroAmount_ShouldThrowIllegalArgumentException() {
        Account account = new Account("ACC123", "John Doe", 100.0);
        Throwable thrown = catchThrowable(() -> account.deposit(0.0));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Deposit amount must be positive.");
    }

    @Test
    void deposit_NegativeAmount_ShouldThrowIllegalArgumentException() {
        Account account = new Account("ACC123", "John Doe", 100.0);
        Throwable thrown = catchThrowable(() -> account.deposit(-10.0));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Deposit amount must be positive.");
    }

    @Test
    void withdraw_ValidAmount_ShouldDecreaseBalance() {
        Account account = new Account("ACC123", "John Doe", 100.0);
        double withdrawAmount = 30.0;

        account.withdraw(withdrawAmount);
        assertThat(account.getBalance()).isEqualTo(70.0);
    }

    @Test
    void withdraw_ExactBalance_ShouldSetBalanceToZero() {
        Account account = new Account("ACC123", "John Doe", 100.0);
        account.withdraw(100.0);
        assertThat(account.getBalance()).isEqualTo(0.0);
    }

    @Test
    void withdraw_ZeroAmount_ShouldThrowIllegalArgumentException() {
        Account account = new Account("ACC123", "John Doe", 100.0);
        Throwable thrown = catchThrowable(() -> account.withdraw(0.0));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Withdrawal amount must be positive.");
    }

    @Test
    void withdraw_NegativeAmount_ShouldThrowIllegalArgumentException() {
        Account account = new Account("ACC123", "John Doe", 100.0);
        Throwable thrown = catchThrowable(() -> account.withdraw(-20.0));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Withdrawal amount must be positive.");
    }

    @Test
    void withdraw_AmountExceedingBalance_ShouldThrowIllegalArgumentException() {
        Account account = new Account("ACC123", "John Doe", 100.0);
        double withdrawAmount = 150.0;

        Throwable thrown = catchThrowable(() -> account.withdraw(withdrawAmount));

        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Insufficient balance.");
    }

    @Test
    void setBalance_ShouldOverrideBalanceRegardlessOfValue() {
        Account account = new Account("ACC123", "John Doe", 100.0);
        // Set to a higher value
        account.setBalance(200.0);
        assertThat(account.getBalance()).isEqualTo(200.0);
        
        // Set to a lower value (including negative)
        account.setBalance(-50.0);
        assertThat(account.getBalance()).isEqualTo(-50.0);
    }
}