package com.tsuitech.Locker.repository;

import com.tsuitech.Locker.dao.EMISchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EMIScheduleRepository extends JpaRepository<EMISchedule, Integer> {
    List<EMISchedule> findByDueDateBeforeAndPaidIsFalse(Date currentDate);
}
