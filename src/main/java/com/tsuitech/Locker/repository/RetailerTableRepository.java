package com.tsuitech.Locker.repository;

import com.tsuitech.Locker.dao.RetailerTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailerTableRepository extends JpaRepository<RetailerTable, Integer> {

        RetailerTable findByEmail(String email);

        RetailerTable findByMobile(String mobile);

}

