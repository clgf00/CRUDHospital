package com.hospitalcrudapp.dao.repositories;
import com.hospitalcrudapp.dao.model.MedRecord;
import java.util.List;
public interface MedRecordRepository {
    List<MedRecord> getAll(int idPatient);

    int update(MedRecord medRecord) ;

    void delete(int id) ;

    int add(MedRecord medRecord);
}
