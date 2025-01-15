package com.hospitalcrudapp.dao.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "prescribed_medications")
@NamedQueries(
        { @NamedQuery(name = "HQL_GET_ALL_MEDICATIONS",
                query = "from Medication m WHERE m.medRecord.id = :record_id") }
)
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_id")
    private int id;

    @Column(name = "medication_name")
    private String medicationName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private MedRecord medRecord;
}
