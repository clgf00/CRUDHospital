package com.hospitalcrudapp.dao.repositories.SpringJDBC;

import com.hospitalcrudapp.dao.model.Doctor;
import com.hospitalcrudapp.dao.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Profile("active")
public class SpringDoctorRepository implements DoctorRepository {

    @Autowired
    private JdbcClient jdbcClient;

    @Override
    public List<Doctor> getAll() {
        return jdbcClient.sql("select * from doctors")
                .query(Doctor.class)
                .list();
    }
}