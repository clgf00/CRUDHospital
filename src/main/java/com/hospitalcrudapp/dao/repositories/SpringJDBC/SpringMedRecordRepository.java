package com.hospitalcrudapp.dao.repositories.SpringJDBC;


import com.hospitalcrudapp.dao.model.MedRecord;
import com.hospitalcrudapp.dao.model.Patient;
import com.hospitalcrudapp.dao.repositories.MedRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@Profile("active")
public class SpringMedRecordRepository implements MedRecordRepository {
    @Autowired
    private JdbcClient jdbcClient;
    @Override
    public List<MedRecord> getAll(int idPatient) {
        return jdbcClient.sql("select * from medical_records")
                .query(MedRecord.class)
                .list();
    }

    @Override
    public int update(MedRecord medRecord) {
        return jdbcClient.sql("update medical_record set record id = ?, patient_id = ?, doctor_id = ?, diagnosis = ?, admission_date = ? where record_id = ?")
                .param(1, medRecord.getId())
                .param(2, medRecord.getIdPatient())
                .param(3, medRecord.getIdDoctor())
                .param(4, medRecord.getDescription())
                .param(5, medRecord.getDate())
               .update();
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public int add(MedRecord medRecord) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("insert into medical_records (record_id, patient_id, doctor_id, diagnosis, admission_date) values (?,?,?,?,?)")
                .param(1, medRecord.getId())
                .param(2, medRecord.getIdPatient())
                .param(3, medRecord.getIdDoctor())
                .param(4, medRecord.getDescription())
                .param(5, medRecord.getDate())
                .update(keyHolder);

        int newId = Objects.requireNonNull(keyHolder.getKey(), "Key was not generated").intValue();
        medRecord.setId(newId);

        return newId;
    }
}
