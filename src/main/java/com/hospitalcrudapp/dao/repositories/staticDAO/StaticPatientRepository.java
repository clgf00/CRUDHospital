package com.hospitalcrudapp.dao.repositories.staticDAO;
import com.hospitalcrudapp.dao.model.Credential;
import com.hospitalcrudapp.dao.model.Patient;
import com.hospitalcrudapp.dao.repositories.PatientRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Repository
@Profile("null")
public class StaticPatientRepository implements PatientRepository {
    private final List<Patient> patients = new ArrayList<>();
    private final MessageSource messageSource;

    public StaticPatientRepository(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public List<Patient> getAll() {
        //TODO: Sacar inicialización de getAll()

        patients.add(new Patient(1, "Cris", LocalDate.of(2003,11,12),"23456", new Credential("user1","pass1", 12)));
        patients.add(new Patient(2, "Juan", LocalDate.of(2003,3,4),"23456", new Credential("user2","pass2", 13)));
        patients.add(new Patient(3, "Maria", LocalDate.of(2003,1,14),"23456", new Credential("user3","pass3", 14)));
        patients.add(new Patient(4, "Cris", LocalDate.of(2003,5,5),"23456", new Credential("user4","pass4", 15)));

        return patients;
    }

    @Override
    public void update(Patient patient) {
    }

    @Override
    public int delete(int id, boolean confirm) {
        return 0;
    }

    public int add(Patient patient) {
        return 4;
    }
}