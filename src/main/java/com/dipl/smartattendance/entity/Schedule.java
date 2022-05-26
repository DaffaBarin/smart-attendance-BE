package com.dipl.smartattendance.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedules")
/**
 * Entity for Schedule with table name schedules
 */
public class Schedule {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    String id;

    @Column(name = "long", nullable = false)
    String longitude;

    @Column(name = "lat", nullable = false)
    String latitude;

    @Column(name = "date", columnDefinition = "DATE", nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "schedule")
    private Set<Attendance> attendances;
}
