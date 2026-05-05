package net.hafssa.ebankbackend.mappers;

import net.hafssa.ebankbackend.dtos.CustomerDTO;
import net.hafssa.ebankbackend.entities.Customer;
import org.springframework.beans.BeanUtils;

public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        // customerDTO.setId(customer.getId());
        // customerDTO.setEmail(customer.getEmail());
        // customerDTO.setName(customer.getName());
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
}
