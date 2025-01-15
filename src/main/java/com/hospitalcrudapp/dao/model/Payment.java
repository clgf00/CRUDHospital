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
@Table(name = "patient_payments")
@NamedQueries(
        { @NamedQuery(name = "HQL_GET_TOTAL_PAYMENTS",
                query = "from Payment") }
)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "amount")
    private float amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDate date;

}
