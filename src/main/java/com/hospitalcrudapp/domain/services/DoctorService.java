package com.hospitalcrudapp.domain.services;
import com.hospitalcrudapp.dao.model.Doctor;
import com.hospitalcrudapp.dao.repositories.JDBC.JDBCDoctorRepository;
import com.hospitalcrudapp.domain.model.DoctorUi;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
@Getter
@Setter
public class DoctorService {
    private final JDBCDoctorRepository doctorRepository;

    public DoctorService(JDBCDoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
        
    }

    public List<DoctorUi> findAll() {
        List<Doctor> doctors = doctorRepository.getAll();
        List<DoctorUi> doctorUi = new ArrayList<>();
        doctors.forEach(doctor ->
                doctorUi.add(new DoctorUi(doctor.getId(), doctor.getName())));
        return doctorUi;
    }
}


