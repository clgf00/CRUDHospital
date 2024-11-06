package com.hospitalcrudapp.dao.repositories.Files;

import com.hospitalcrudapp.configuration.Configuration;
import com.hospitalcrudapp.dao.mappers.PatientRowMapper;
import com.hospitalcrudapp.dao.model.Patient;
import com.hospitalcrudapp.dao.repositories.PatientRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("null")
//el txtfiles no esta puesto en application.properties porque el que está activo ahora mismo es el static
public class TxtPatientRepository implements PatientRepository {

    private final PatientRowMapper patientRowMapper;
    private List<Patient> patients;
    private DateTimeFormatter dateFormatter;

    public TxtPatientRepository(PatientRowMapper patientRowMapper) {
        this.patientRowMapper = patientRowMapper;
        this.patients = new ArrayList<>();
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    private void writePatientsToFile(List<Patient> patientsList, BufferedWriter writer) throws IOException {
        for (Patient p : patientsList) {
            //HACER ESTE METODO UN TOSTRING
            String patientData = String.format("%d; %s; %s; %s%n",
                    //%d se refiere a un numero entero, %s se refiere a string y %n es salto de linea
                    // de modo que quedará así: newId; nombre; fechaNacimiento; telefono; usuario; contraseña
                    p.getId(),
                    p.getName(),
                    p.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    p.getPhoneNumber());
            //HASTA AQUI
            //FILES.NEWBUFFEREDWRITER EN VEZ DE WRITE EN WRITER.WRITE
            writer.write(patientData);
        }
    }

    private List<String> readFile() {
        List<String> lista = new ArrayList<>();
        Path file = Paths.get(Configuration.getInstance().getProperty("patientsFilePath"));
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lista.add(line);
            }
        } catch (IOException e) {
        }
        return lista;
    }

    @Override
    public List<Patient> getAll() {
        patients.clear();
        List<String> listaPacientes = readFile();
        for (String line : listaPacientes) { //(forEach)
            Patient patient;
            try {
                patient = patientRowMapper.mapRow2(line);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //Creamos un nuevo objeto Paciente que mapea las lineas String del archivo `para cada una de las lineas
            if (patient != null) {
                patients.add(patient);
            }
        }
        return patients;
    }

    @Override
    public int add(Patient patient) {
        int newId = Integer.parseInt(Configuration.getInstance().getProperty("nextIdPatient"));
        Configuration.getInstance().setProperty("nextIdPatient", String.valueOf(newId + 1));
        patient.setId(newId);
        patients.add(patient);
        //coge el id del paciente y le añade uno para el proximo
        Path file = Paths.get(Configuration.getInstance().getProperty("patientsFilePath"));
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardOpenOption.APPEND)) {
            writePatientsToFile(patients, writer);
            // Esto agrega los datos del paciente al final del archivo con el append
        } catch (IOException e) {
            return 0;
        }
        //update newId property in properties.txt
        ///Write the new patient with APPEND
        //get the next id from properties.txt
        //write the new patients with APPEND
        return newId; //devuelve el id del nuevo paciente agregado
    }

    @Override
    public void update(Patient patient) {
        List<Patient> lista = getAll();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == patient.getId()) {
                lista.set(i, patient);
                break;
            }
        }
        Path file = Paths.get(Configuration.getInstance().getProperty("patientsFilePath"));

        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            try {
                writePatientsToFile(lista, writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //getAll
            //change the patient in the list
            //Transform patients toString() from Patient.class
            //write again the file (REPLACE_EXISTING)
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int delete(int id, boolean confirm) {
        List<Patient> patientsList = getAll();
        patientsList.removeIf(patient -> patient.getId() == id);
        Path file = Paths.get(Configuration.getInstance().getProperty("patientsFilePath"));
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writePatientsToFile(patientsList, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //getALL
        //remove the patient from the list
        //write again the file(REPLACE_EXISTING)
        return id;
    }
}
