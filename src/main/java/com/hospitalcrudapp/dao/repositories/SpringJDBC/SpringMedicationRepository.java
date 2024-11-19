package com.hospitalcrudapp.dao.repositories.SpringJDBC;


import com.hospitalcrudapp.dao.model.Medication;
import com.hospitalcrudapp.dao.repositories.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("active")
public class SpringMedicationRepository implements MedicationRepository {
    @Autowired
    private JdbcClient jdbcClient;

    @Override
    public List<Medication> getAll() {
        return jdbcClient.sql("Select * from prescribed_medications")
                .query(Medication.class)
                .list();
    }
}
