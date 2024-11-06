package com.hospitalcrudapp.dao.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credential {
    private String username;
    private String password;
    private int patientId;


}