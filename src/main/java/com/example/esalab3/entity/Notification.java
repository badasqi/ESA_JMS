package com.example.esalab3.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "notification_email", nullable = false, length = 255)
    private String notificationEmail;

    @Column(name = "notification_condition", nullable = false, length = 255)
    private String notificationCondition;

}


