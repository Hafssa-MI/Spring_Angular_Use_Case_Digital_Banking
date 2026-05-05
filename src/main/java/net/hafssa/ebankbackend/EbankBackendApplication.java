package net.hafssa.ebankbackend;

import net.hafssa.ebankbackend.entities.*;
import net.hafssa.ebankbackend.enums.AccountStatus;
import net.hafssa.ebankbackend.enums.OperationType;
import net.hafssa.ebankbackend.exceptions.BalanceNotSufficientException;
import net.hafssa.ebankbackend.exceptions.BankAccountNotFoundException;
import net.hafssa.ebankbackend.exceptions.CustomerNotFoundException;
import net.hafssa.ebankbackend.repositories.AccountOperationRepository;
import net.hafssa.ebankbackend.repositories.BankAccountRepository;
import net.hafssa.ebankbackend.repositories.CustomerRepository;
import net.hafssa.ebankbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(EbankBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Hassan","Imane","Mohamed").forEach(name -> {
               Customer customer = new Customer();
               customer.setName(name);
               customer.setEmail(name+"@mail.com");
               bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try{
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*90000,5.5,customer.getId());
                    List<BankAccount> bankAccounts = bankAccountService.bankAccountList();
                    for(BankAccount bankAccount : bankAccounts){
                        for(int i=0;i<10;i++){
                            bankAccountService.credit(bankAccount.getId(), 10000+Math.random()*12000,"Credit");
                            bankAccountService.debit(bankAccount.getId(),1000+Math.random()*9000,"Debit");

                        }
                    }
                }catch(CustomerNotFoundException | BankAccountNotFoundException | BalanceNotSufficientException e){
                    e.printStackTrace();
                }
            });
        };
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository
                            ) {
        return args -> {
            Stream.of("Hassan","Yassine","Aicha").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setCustomer(cust);
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCreatedAt(new Date());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setCustomer(cust);
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCreatedAt(new Date());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc -> {
               for(int i=0;i<10;i++){
                  AccountOperation accountOperation = new AccountOperation();
                  accountOperation.setOperationDate(new Date());
                  accountOperation.setAmount(Math.random()*12000);
                  accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                  accountOperation.setBankAccount(acc);
                  accountOperationRepository.save(accountOperation);
               }
            });
        };
    }

}
