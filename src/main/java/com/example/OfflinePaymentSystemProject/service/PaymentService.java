package com.example.OfflinePaymentSystemProject.service;


import com.example.OfflinePaymentSystemProject.dto.CreatePaymentRequestDTO;
import com.example.OfflinePaymentSystemProject.dto.CreatePaymentResponseDTO;
import com.example.OfflinePaymentSystemProject.entity.Account;
import com.example.OfflinePaymentSystemProject.entity.Transaction;
import com.example.OfflinePaymentSystemProject.enums.TransactionStatus;
import com.example.OfflinePaymentSystemProject.repository.AccountRepository;
import com.example.OfflinePaymentSystemProject.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    @Autowired
    public PaymentService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public CreatePaymentResponseDTO transfer(CreatePaymentRequestDTO request) {

        Account sender = accountRepository.findByUpiId(request.getSenderUpiId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Account receiver = accountRepository.findByUpiId(request.getReceiverUpiId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if(sender.getBalance() < request.getAmount()){
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - request.getAmount());

        receiver.setBalance(receiver.getBalance() + request.getAmount());

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction transaction = new Transaction();

        transaction.setTransactionId(UUID.randomUUID().toString());

        transaction.setSender(sender);

        transaction.setReceiver(receiver);

        transaction.setAmount(request.getAmount());

        transaction.setStatus(TransactionStatus.SUCCESS);

        transaction.setCreatedAt(LocalDateTime.now());

        transactionRepository.save(transaction);

        return new CreatePaymentResponseDTO(
                transaction.getTransactionId(),
                "Payment Successful",
                sender.getBalance(),
                receiver.getBalance()
        );
    }


}

