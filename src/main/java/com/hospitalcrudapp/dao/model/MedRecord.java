package com.hospitalcrudapp.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "medical_records")
@NamedQueries(
        {@NamedQuery(name = "HQL_GET_ALL_MEDRECORDS",
                query = "SELECT mr FROM MedRecord mr LEFT JOIN FETCH mr.medications WHERE mr.patient.id = :patient_id")}
)
public class MedRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "diagnosis")
    private String description;

    @Column(name = "admission_date")
    private LocalDate date;

    @OneToMany(mappedBy = "medRecord", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Medication> medications = new ArrayList<>();
}
