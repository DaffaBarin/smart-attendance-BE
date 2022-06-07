package com.dipl.smartattendance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    Double longitude;

    @Column(name = "lat", nullable = false)
    Double latitude;

    @Column(name = "date", columnDefinition = "DATE", nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private Set<Attendance> attendances;
}
