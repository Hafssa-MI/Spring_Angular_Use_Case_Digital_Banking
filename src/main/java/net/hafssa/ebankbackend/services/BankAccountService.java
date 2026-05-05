package net.hafssa.ebankbackend.services;

import net.hafssa.ebankbackend.entities.BankAccount;
import net.hafssa.ebankbackend.entities.CurrentAccount;
import net.hafssa.ebankbackend.entities.Customer;
import net.hafssa.ebankbackend.entities.SavingAccount;
import net.hafssa.ebankbackend.exceptions.BalanceNotSufficientException;
import net.hafssa.ebankbackend.exceptions.BankAccountNotFoundException;
import net.hafssa.ebankbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccount> bankAccountList();
}
