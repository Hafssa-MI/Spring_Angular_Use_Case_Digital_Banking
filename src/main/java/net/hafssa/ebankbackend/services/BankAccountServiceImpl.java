package net.hafssa.ebankbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hafssa.ebankbackend.dtos.CustomerDTO;
import net.hafssa.ebankbackend.entities.*;
import net.hafssa.ebankbackend.enums.OperationType;
import net.hafssa.ebankbackend.exceptions.BalanceNotSufficientException;
import net.hafssa.ebankbackend.exceptions.BankAccountNotFoundException;
import net.hafssa.ebankbackend.exceptions.CustomerNotFoundException;
import net.hafssa.ebankbackend.mappers.BankAccountMapperImpl;
import net.hafssa.ebankbackend.repositories.AccountOperationRepository;
import net.hafssa.ebankbackend.repositories.BankAccountRepository;
import net.hafssa.ebankbackend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;

    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Saving new customer");
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        CurrentAccount savedBankAccount= bankAccountRepository.save(currentAccount);

        return savedBankAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        SavingAccount savedBankAccount= bankAccountRepository.save(savingAccount);

        return savedBankAccount;
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> collect = customers.stream()
                .map(customer->dtoMapper.fromCustomer(customer))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException{
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Bank Account not Found"));
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = getBankAccount(accountId);
        if(bankAccount.getBalance()<amount){
            throw new BalanceNotSufficientException("Balance Not Sufficient");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = getBankAccount(accountId);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource,amount,"Transfer to" + accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from" + accountIdSource);
    }
    @Override
    public List<BankAccount> bankAccountList(){
        return bankAccountRepository.findAll();
    };
}
