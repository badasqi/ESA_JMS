package com.example.esalab3.entity;
import lombok.*;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "changelog")
public class ChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "change_type", nullable = false)
    private String changeType;

    @Column(name = "entity_class", nullable = false)
    private String entityClass;

    @Column(name = "change_details")
    private String changeDetails;

    @Column(name = "change_timestamp", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp changeTimestamp;

    @Override
    public String toString() {
        return "ChangeLog{" +
                "id=" + id +
                ", changeType='" + changeType + '\'' +
                ", entityClass='" + entityClass + '\'' +
                ", changeDetails='" + changeDetails + '\'' +
                ", changeTimestamp=" + changeTimestamp +
                '}';
    }
}
