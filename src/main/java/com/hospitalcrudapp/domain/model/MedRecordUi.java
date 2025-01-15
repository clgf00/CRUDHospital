package com.hospitalcrudapp.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Data
public class MedRecordUi {

    private int id;
    private int idPatient;
    private int idDoctor;
    private String description;
    private LocalDate date;
    private List<String> medications;
}
