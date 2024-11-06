package com.hospitalcrudapp.dao.repositories;
import com.hospitalcrudapp.dao.model.Doctor;
import java.util.List;
public interface DoctorRepository {
    List<Doctor> getAll();
    int add(Doctor doctor);
    void update(Doctor doctor);
    void delete(int id);
}
