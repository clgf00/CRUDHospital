package com.hospitalcrudapp.dao.repositories.staticDAO;
import com.hospitalcrudapp.dao.model.Doctor;
import com.hospitalcrudapp.dao.repositories.DoctorRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
@Profile("null")
public class StaticDoctorRepository implements DoctorRepository {
    private final List<Doctor> doctors = new ArrayList<>();

    public List<Doctor> getAll() {

        doctors.add(new Doctor(1, "Juan", "Dentist"));
        doctors.add(new Doctor(2, "Pedro", "Ongology"));
        doctors.add(new Doctor(3, "Maria", "Pediatry"));
        doctors.add(new Doctor(4, "Lola",  "Family"));

        return doctors;
    }
}
