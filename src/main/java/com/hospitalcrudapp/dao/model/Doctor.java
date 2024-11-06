package com.hospitalcrudapp.dao.model;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {
    private int id;
    private String name;
    private String specialty;
}
