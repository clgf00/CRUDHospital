package com.hospitalcrudapp.dao.model;
import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "doctors")
@NamedQueries(
        { @NamedQuery(name = "HQL_GET_ALL_DOCTORS",
                query = "from Doctor") }
)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private int doctor_id;
    @Column(name = "name")
    private String name;
    @Column(name = "specialization")
    private String specialty;

}
