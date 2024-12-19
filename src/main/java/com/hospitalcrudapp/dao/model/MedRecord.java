package com.hospitalcrudapp.dao.model;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

    public class MedRecord {

        private int id;
        private int idPatient;
        private int idDoctor;
        private String description;
        private LocalDate date;
        private List<Medication> medications = new ArrayList<>();
    }

