package com.example.OfflinePaymentSystemProject.service;

import com.example.OfflinePaymentSystemProject.dto.CreateAccountRequestDTO;
import com.example.OfflinePaymentSystemProject.dto.CreateAccountResponseDTO;
import com.example.OfflinePaymentSystemProject.entity.Account;
import com.example.OfflinePaymentSystemProject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //Create account
    public CreateAccountResponseDTO createAccount(CreateAccountRequestDTO account){

        if(accountRepository.findByUpiId(account.getUpiId()).isPresent()){
            throw new RuntimeException("UpiId already exists");
        }

        if(account.getBalance()<0){
            throw new RuntimeException("Balance cannot be negative");
        }

        Account savedAccount = accountRepository.save(mapDtoToAccount(account));

        return mapAccountToDTO(savedAccount);
    }

    public List<Account> getAllAccounts(){
        return null;
    }

    public Account getAccountById(Long id){
        return null;
    }

    // DTO to Account
    public Account mapDtoToAccount(CreateAccountRequestDTO account){
        Account response = new Account();
        response.setName(account.getName());
        response.setUpiId(account.getUpiId());
        response.setBalance(account.getBalance());
        return response;
    }

    // Account to DTO
    public CreateAccountResponseDTO mapAccountToDTO(Account account){
        CreateAccountResponseDTO response = new CreateAccountResponseDTO();
        response.setId(account.getId());
        response.setName(account.getName());
        response.setUpiId(account.getUpiId());
        response.setBalance(account.getBalance());
        return response;
    }




}
