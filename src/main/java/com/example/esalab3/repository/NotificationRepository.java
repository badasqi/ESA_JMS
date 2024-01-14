package com.example.esalab3.repository;


import com.example.esalab3.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("SELECT n.notificationEmail FROM Notification n")
    List<String> findAllEmails();
}
