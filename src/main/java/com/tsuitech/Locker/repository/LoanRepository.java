package com.tsuitech.Locker.repository;

import com.tsuitech.Locker.dao.LoanDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface LoanRepository extends JpaRepository<LoanDetails, Integer> {

    //List<LoanDetails> findByRetailerId(Integer retailerId);
    List<LoanDetails> findByRetailerTable_RetailerId(Integer retailerId);
}
