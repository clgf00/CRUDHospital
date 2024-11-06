package com.hospitalcrudapp.dao.model;
import lombok.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Patient {
    private int id;
    private String name;
    private LocalDate birthDate;
    private String phoneNumber;
    private Credential credentials;
    private List<MedRecord> medRecords;

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
        this.medRecords = new ArrayList<>();
    }

}
