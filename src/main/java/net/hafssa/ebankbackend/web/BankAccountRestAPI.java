package net.hafssa.ebankbackend.web;

import lombok.AllArgsConstructor;
import net.hafssa.ebankbackend.dtos.AccountHistoryDTO;
import net.hafssa.ebankbackend.dtos.AccountOperationDTO;
import net.hafssa.ebankbackend.dtos.BankAccountDTO;
import net.hafssa.ebankbackend.exceptions.BankAccountNotFoundException;
import net.hafssa.ebankbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")

public class BankAccountRestAPI {
    private BankAccountService bankAccountService;
    @GetMapping("/accounts/{accoundtId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }

}
