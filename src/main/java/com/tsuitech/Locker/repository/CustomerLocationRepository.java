package com.tsuitech.Locker.repository;

import com.tsuitech.Locker.dao.CustomerLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerLocationRepository extends JpaRepository<CustomerLocation,Long> {
}
