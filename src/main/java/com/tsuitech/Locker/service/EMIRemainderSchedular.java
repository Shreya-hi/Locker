package com.tsuitech.Locker.service;

import com.tsuitech.Locker.dao.EMISchedule;
import com.tsuitech.Locker.repository.EMIScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class EMIRemainderSchedular {
    @Autowired
    private EMIScheduleRepository emiScheduleRepository;

    @Scheduled(cron = "0 0 12 * * ?") // Run every day at 12:00 PM
    public void sendEMIReminders() {
        Date currentDate = new Date();
        List<EMISchedule> overdueEMIs = emiScheduleRepository.findByDueDateBeforeAndPaidIsFalse(currentDate);

        for (EMISchedule emi : overdueEMIs) {
            // Logic to send reminders for overdue EMIs
            // You can use a messaging service, email, or any other method for reminders
            // Update the lastReminderDate or log the reminders to avoid duplicate reminders
            System.out.println("Reminder sent for EMI with id: " + emi.getId());
        }
    }
}
