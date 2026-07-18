package com.example.OfflinePaymentSystemProject.service;

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
    public Account createAccount(Account account){

        if(accountRepository.findByUpiId(account.getUpiId()).isPresent()){
            throw new RuntimeException("UpiId already exists");
        }

        if(account.getBalance()<0){
            throw new RuntimeException("Balance cannot be negative");
        }

        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts(){
        return null;
    }

    public Account getAccountById(Long id){
        return null;
    }




}
