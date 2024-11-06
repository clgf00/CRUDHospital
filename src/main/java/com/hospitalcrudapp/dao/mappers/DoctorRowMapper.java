package com.hospitalcrudapp.dao.mappers;


import com.hospitalcrudapp.dao.model.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorRowMapper {

    public Doctor mapRow(String s) {
        String[] parts = s.split(";");
        if (parts.length >= 3) {
            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            String specialty = parts[2].trim();
            return new Doctor(id, name, specialty);
        }
        return null;
    }
}
