package net.hafssa.ebankbackend.dtos;

import lombok.Data;
import net.hafssa.ebankbackend.entities.AccountOperation;

import java.util.List;

@Data
public class AccountHistoryDTO {
    private String accountId;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private int page;
    private int size;
    private List<AccountOperationDTO> accountOperationDTOs;
}
