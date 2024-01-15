package com.tsuitech.Locker.repository;

import com.tsuitech.Locker.dao.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Profile findByEmailAndMobile(String email, Long mobile);
}
