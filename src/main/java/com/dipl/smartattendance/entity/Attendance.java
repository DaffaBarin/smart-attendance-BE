package com.dipl.smartattendance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendances")
/**
 * Entity for Attendance with table name attendances
 */
public class Attendance {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    String id;

    @Column(name = "long", nullable = false)
    Double longitude;

    @Column(name = "lat", nullable = false)
    Double latitude;

    @Column(name = "attendance_time", columnDefinition = "TIME", nullable = false)
    private LocalTime time;

    @Column(name = "attendance_status", nullable = false)
    String attendanceStatus;

    @Column(name = "attendance_note")
    String note;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;
}
