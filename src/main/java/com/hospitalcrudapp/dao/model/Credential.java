package com.hospitalcrudapp.dao.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_login")
@NamedQueries(
        { @NamedQuery(name = "HQL_GET_CREDENTIAL",
                query = "SELECT c FROM Credential c WHERE c.username = ?1") }
)

public class Credential {
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int patientId;


}