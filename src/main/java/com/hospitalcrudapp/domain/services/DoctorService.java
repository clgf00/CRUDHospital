package com.hospitalcrudapp.domain.services;
import com.hospitalcrudapp.dao.model.Doctor;
import com.hospitalcrudapp.dao.repositories.SpringJDBC.SpringDoctorRepository;
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
    private final SpringDoctorRepository doctorRepository;

    public DoctorService(SpringDoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
        
    }

    public List<DoctorUi> findAll() {
        List<Doctor> doctors = doctorRepository.getAll();
        List<DoctorUi> doctorUi = new ArrayList<>();
        doctors.forEach(doctor ->
                doctorUi.add(new DoctorUi(doctor.getDoctor_id(), doctor.getName())));
        return doctorUi;
    }
}


