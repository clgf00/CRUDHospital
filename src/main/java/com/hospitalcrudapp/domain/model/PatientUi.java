package com.hospitalcrudapp.domain.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientUi {
    private int id;
    private String name;
    private LocalDate birthDate;
    private String phone;
    private String userName;
    private String password;
    private double paid;

}