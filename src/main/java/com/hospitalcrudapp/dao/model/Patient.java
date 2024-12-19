package com.hospitalcrudapp.dao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patients")
@NamedQueries(
        { @NamedQuery(name = "HQL_GET_ALL_PATIENTS",
        query = "from Patient") }
)
public class Patient { //POJO
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "date_of_birth")
    private LocalDate birthDate;
    @Column(name = "phone")
    private String phoneNumber;
    @Transient
    private Credential credentials;


    public Patient(int id, String name, LocalDate birthDate, String phoneNumber, Credential credentials) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.credentials = credentials;
    }
    public Patient(int id, String name, LocalDate birthDate, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }
}
