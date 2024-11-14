package com.hospitalcrudapp.dao.repositories.staticDAO;
import com.hospitalcrudapp.dao.model.MedRecord;
import com.hospitalcrudapp.dao.model.Medication;
import com.hospitalcrudapp.dao.repositories.MedRecordRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Repository
@Profile("null")
public  class StaticMedRecordRepository implements MedRecordRepository {
    private final List<MedRecord> medRecords = new ArrayList<>();
    private final List<Medication> medications = new ArrayList<>();
    private final List<Medication> meds = new ArrayList<>();
    private final List<Medication> meds2 = new ArrayList<>();
    private static boolean inicializado = false;


    public List<MedRecord> getAll() {
        if (!inicializado) {
            medications.add(new Medication(1, "paracetamol", 1));

            medications.add(new Medication(3, "tylenol", 2));
            meds.add(new Medication(4, "Folic acid", 3));
            meds.add(new Medication(5, "paracetamol", 4));
            meds2.add(new Medication(6, "ibuprofen", 3));

            medRecords.add(new MedRecord(1, 1, 1, "Headache", LocalDate.of(2019, 12, 13), medications));
            medRecords.add(new MedRecord(2, 1, 1, "Stomachache", LocalDate.of(2023, 10, 10), meds));
            medRecords.add(new MedRecord(3, 1, 2, "Diarrhea", LocalDate.of(2024, 5, 20), medications));
            medRecords.add(new MedRecord(4, 1, 4, "Headache", LocalDate.of(2011, 1, 7), meds2));
            inicializado = true;
        }

        return medRecords;
    }


    @Override
    public List<MedRecord> getAll(int idPatient) {
        return List.of();
    }

    @Override
    public int update(MedRecord medRecord) {
        return 0;
    }

    @Override
    public void delete(int id) {
    }

    public int add(MedRecord medRecord) {
        return 0;
    }
}
