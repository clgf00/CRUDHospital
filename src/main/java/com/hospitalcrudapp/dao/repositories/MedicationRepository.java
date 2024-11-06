package com.hospitalcrudapp.dao.repositories;
import com.hospitalcrudapp.dao.model.Medication;
import java.util.List;
public interface MedicationRepository {

    List<Medication> getAll();
}
