package net.hafssa.ebankbackend.repositories;

import net.hafssa.ebankbackend.entities.AccountOperation;
import net.hafssa.ebankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

}
