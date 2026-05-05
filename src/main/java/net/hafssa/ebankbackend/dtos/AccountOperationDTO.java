package net.hafssa.ebankbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.hafssa.ebankbackend.entities.BankAccount;
import net.hafssa.ebankbackend.enums.OperationType;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date  operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
