package net.hafssa.ebankbackend.repositories;

import net.hafssa.ebankbackend.entities.BankAccount;
import net.hafssa.ebankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    
}
