package com.hospitalcrudapp.domain.services;
import com.hospitalcrudapp.dao.model.MedRecord;
import com.hospitalcrudapp.dao.model.Medication;
import com.hospitalcrudapp.dao.repositories.Files.XmlMedRecordRepository;
import com.hospitalcrudapp.dao.repositories.JDBC.JDBCMedRecordRepository;
import com.hospitalcrudapp.dao.repositories.JDBC.JDBCPatientRepository;
import com.hospitalcrudapp.dao.repositories.MedRecordRepository;
import com.hospitalcrudapp.dao.repositories.MedicationRepository;
import com.hospitalcrudapp.dao.repositories.Files.TxtPatientRepository;
import com.hospitalcrudapp.domain.model.MedRecordUi;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Service
public class MedRecordService {
   private final JDBCMedRecordRepository medRecordRepository;

    public MedRecordService(JDBCMedRecordRepository medRecordRepository) {
        this.medRecordRepository = medRecordRepository;
    }


    public List<MedRecordUi> getAll(int idPatient){
        List<MedRecord> medRecords = medRecordRepository.getAll(idPatient).stream().filter(record -> record.getIdPatient() == idPatient).toList();
        List<MedRecordUi> medRecordUi = new ArrayList<>();
        for (MedRecord medRecord : medRecords) {
            medRecordUi.add(new MedRecordUi(medRecord.getId(),
                    medRecord.getIdPatient(),
                    medRecord.getIdDoctor(),
                    medRecord.getDescription(),
                    medRecord.getDate(),
                    medRecord.getMedications()
                            .stream().filter(medication -> medication.getMedRecordId() == medRecord.getId()).map(Medication::getMedicationName)
                            .toList()));
        }
        return medRecordUi;

    }

    public int add(MedRecordUi medRecordui) {

        ArrayList<Medication> medications = new ArrayList<>();
        LocalDate date= LocalDate.parse(medRecordui.getDate().toString());

        medRecordui.getMedications().forEach(medication ->
                medications.add(new Medication(medRecordui.getIdPatient(), medication, medRecordui.getId())));

        MedRecord medRecord= new MedRecord(medRecordui.getId(), medRecordui.getIdPatient(), medRecordui.getIdDoctor(),
                medRecordui.getDescription(), date,medications);

        return medRecordRepository.add(medRecord);
    }

    public void update(MedRecordUi medRecordui) {

        ArrayList<Medication> medications = new ArrayList<>();
        LocalDate date=LocalDate.parse(medRecordui.getDate().toString());

        medRecordui.getMedications().forEach(medication ->
                medications.add(new Medication(medRecordui.getIdPatient(), medication, medRecordui.getId())));

        MedRecord medRecord= new MedRecord(medRecordui.getId(), medRecordui.getIdPatient(), medRecordui.getIdDoctor(),
                medRecordui.getDescription(), date,medications);

        medRecordRepository.update(medRecord);
    }
    public void delete(int id) throws Exception {
        medRecordRepository.delete(id);
           }

}
