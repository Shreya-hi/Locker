package com.tsuitech.Locker.repository;

import com.tsuitech.Locker.dao.RegistrationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationDao, Integer> {
}
