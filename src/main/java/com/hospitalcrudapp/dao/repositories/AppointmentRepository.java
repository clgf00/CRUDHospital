package com.hospitalcrudapp.dao.repositories;

import com.hospitalcrudapp.dao.model.Appointment;

public interface AppointmentRepository {
    Appointment get(int PatientId);
}
