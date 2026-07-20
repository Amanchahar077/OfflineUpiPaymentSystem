package com.example.OfflinePaymentSystemProject.repository;

import com.example.OfflinePaymentSystemProject.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {


}
