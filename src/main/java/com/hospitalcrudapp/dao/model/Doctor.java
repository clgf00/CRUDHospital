package com.hospitalcrudapp.dao.model;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {
    private int doctor_id;
    private String name;
    private String specialty;
}
