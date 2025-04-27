package com.jamk.thesis.modules;

import com.jamk.thesis.modules.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private List<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        if(!accounts.stream().noneMatch(f->f.getAccountNumber().equals(account.getAccountNumber()))) {
            throw new IllegalArgumentException("Account already exists.");
        }
        accounts.add(account);
    }

    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public double getTotalBalance() {
        double total = 0.0;
        for (Account account : accounts) {
            total += account.getBalance();
        }
        return total;
    }

    /**
     * A transactional transfer that withdraws an amount from the source account and deposits it to the destination.
     * If any exception occurs, the transaction is rolled back.
     *
     * @param fromAccount The account number to withdraw from.
     * @param toAccount   The account number to deposit to.
     * @param amount      The amount to transfer.
     * @return true if the transaction was successful.
     * @throws Exception if the transaction fails.
     */
    public boolean transfer(String fromAccount, String toAccount, double amount) throws Exception {
        Account src = getAccount(fromAccount);
        Account dest = getAccount(toAccount);
        if (src == null || dest == null) {
            throw new IllegalArgumentException("Invalid account number(s).");
        }
        // Save snapshot of current balances.
        Map<Account, Double> snapshot = createSnapshot();
        try {
            // Perform operations.
            src.withdraw(amount);
            dest.deposit(amount);
            // Transaction successful.
            return true;
        } catch (Exception e) {
            // Rollback to snapshot in case of any exception.
            restoreSnapshot(snapshot);
            throw e;
        }
    }

    /**
     * Executes a custom transaction defined by the TransactionAction.
     * If the action fails, the bank state is rolled back to the saved snapshot.
     *
     * @param action The transaction actions to perform.
     * @throws Exception if the transaction fails.
     */
    public void executeTransaction(TransactionAction action) throws Exception {
        Map<Account, Double> snapshot = createSnapshot();
        try {
            action.execute();
        } catch(Exception e) {
            restoreSnapshot(snapshot);
            throw e;
        }
    }

    // Create a snapshot of current account balances.
    private Map<Account, Double> createSnapshot() {
        Map<Account, Double> snapshot = new HashMap<>();
        for (Account account : accounts) {
            snapshot.put(account, account.getBalance());
        }
        return snapshot;
    }

    // Restore account balances from a snapshot.
    private void restoreSnapshot(Map<Account, Double> snapshot) {
        for (Map.Entry<Account, Double> entry : snapshot.entrySet()) {
            entry.getKey().setBalance(entry.getValue());
        }
    }

    @FunctionalInterface
    public interface TransactionAction {
        void execute() throws Exception;
    }
}
