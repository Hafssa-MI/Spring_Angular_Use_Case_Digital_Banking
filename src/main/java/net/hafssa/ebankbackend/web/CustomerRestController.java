package net.hafssa.ebankbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hafssa.ebankbackend.dtos.CustomerDTO;
import net.hafssa.ebankbackend.entities.Customer;
import net.hafssa.ebankbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }
}
