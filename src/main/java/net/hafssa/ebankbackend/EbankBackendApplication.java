package net.hafssa.ebankbackend;

import net.hafssa.ebankbackend.entities.AccountOperation;
import net.hafssa.ebankbackend.entities.CurrentAccount;
import net.hafssa.ebankbackend.entities.Customer;
import net.hafssa.ebankbackend.entities.SavingAccount;
import net.hafssa.ebankbackend.enums.AccountStatus;
import net.hafssa.ebankbackend.enums.OperationType;
import net.hafssa.ebankbackend.repositories.AccountOperationRepository;
import net.hafssa.ebankbackend.repositories.BankAccountRepository;
import net.hafssa.ebankbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(EbankBackendApplication.class, args);
    }
    @Bean
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
