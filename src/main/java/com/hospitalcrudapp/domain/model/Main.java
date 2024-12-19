package com.hospitalcrudapp.domain.model;

import com.hospitalcrudapp.dao.model.Patient;
import com.hospitalcrudapp.dao.repositories.JPA.PatientHibDAO;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
public class Main {

    public static void main(final String[] args)  {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        PatientHibDAO patient = container.select(PatientHibDAO.class).get();
        String dobString = "29-12-1998";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dob = LocalDate.parse(dobString, formatter);
        List<Patient> listPatients;
        listPatients = patient.getAll();
        System.out.println("List of Patients: ");
        listPatients.forEach(System.out::println);

        System.out.println("Patient 49: " + patient.get(49));

        System.out.println("Adding new patient");
        Patient newPatient= new Patient(1,"st. bla bla", dob, "SP");
        patient.add(newPatient);
        System.out.println("Patient 1 added: " + patient.get(1));

        System.out.println("Modifying patient's name");
        newPatient.setName("Hola");
        patient.update(newPatient);

        System.out.println("Patient 1 modified: " + patient.get(1));

        System.out.println("Deleting patient 1");
        patient.delete(newPatient);

        listPatients = patient.getAll();
        System.out.println("List of Patients: ");
        listPatients.forEach(System.out::println);

    }
}
