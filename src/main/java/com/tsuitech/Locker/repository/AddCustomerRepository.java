package com.tsuitech.Locker.repository;

import com.tsuitech.Locker.dao.AddCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AddCustomerRepository extends JpaRepository<AddCustomer, Integer> {
    List<AddCustomer> findByUserNameAndEmailIdAndMobileAndImeiNumber1AndCodeAndCreatedDate(
            String userName, String emailId, String mobile, String imeiNumber1, String code,
            Date createdDate);

    AddCustomer findByUserName(String username);
}
