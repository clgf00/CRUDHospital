package com.hospitalcrudapp.dao.repositories.Files;

import com.hospitalcrudapp.configuration.Configuration;
import com.hospitalcrudapp.dao.model.MedRecord;
import com.hospitalcrudapp.dao.model.MedRecords;
import com.hospitalcrudapp.dao.repositories.MedRecordRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.xml.bind.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("active")
public class XmlMedRecordRepository implements MedRecordRepository {
    private final Path xmlFile;
    private final JAXBContext context;
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;


    public XmlMedRecordRepository() {
        xmlFile = Paths.get(Configuration.getInstance().getProperty("xmlMedRecordsPath"));
        try {
            context = JAXBContext.newInstance(MedRecords.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        try {
            marshaller = context.createMarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        try {
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (PropertyException e) {
            throw new RuntimeException(e);
        }
    }

    private MedRecords loadMedRecords() {
        if (Files.exists(xmlFile)) {
            try {
                return (MedRecords) unmarshaller.unmarshal(Files.newInputStream(xmlFile));
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new MedRecords();
    }

    private void saveMedRecords(MedRecords medRecords)  {
        try {
            marshaller.marshal(medRecords, Files.newOutputStream(xmlFile));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<MedRecord> getAll(int idPatient) {
        MedRecords medRecords = loadMedRecords();
        return medRecords.getMedRecords().stream()
                .filter(record -> record.getIdPatient() == idPatient)
                .collect(Collectors.toList());
    }

    @Override
    public int add(MedRecord medRecord)  {
        MedRecords medRecords = loadMedRecords();
        int newId = medRecords.getMedRecords().stream()
                .mapToInt(MedRecord::getId)
                .max()
                .orElse(0) + 1;
        medRecord.setId(newId);

        medRecords.addMedRecord(medRecord);
        saveMedRecords(medRecords);

        return newId;
    }

    @Override
    public int update(MedRecord medRecord) {
        MedRecords medRecords = loadMedRecords();
        Optional<MedRecord> existingRecord = medRecords.getMedRecords().stream()
                .filter(record -> record.getId() == medRecord.getId())
                .findFirst();

        if (existingRecord.isPresent()) {
            medRecords.getMedRecords().remove(existingRecord.get());
            medRecords.addMedRecord(medRecord);
            saveMedRecords(medRecords);
            return medRecord.getId();
        }
        return 0;
    }


    @Override
    public void delete(int id)  {
        MedRecords medRecords = loadMedRecords();
        medRecords.getMedRecords().removeIf(record -> record.getId() == id);
        saveMedRecords(medRecords);
    }
}

