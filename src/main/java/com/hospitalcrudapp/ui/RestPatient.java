package com.hospitalcrudapp.ui;

import com.hospitalcrudapp.domain.model.PatientUi;
import com.hospitalcrudapp.domain.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class RestPatient {
    private final PatientService patientService;

    public RestPatient(PatientService patientService) {
        this.patientService = patientService;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/patients")
    public List<PatientUi> getPatients() {
        return patientService.getPatients();
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/patients")
    public int addPatient(@RequestBody PatientUi patientui) {
        return patientService.addPatient(patientui);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/patients")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePatient(@RequestBody PatientUi patientUi) {
        patientService.updatePatient(patientUi);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/patients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable int id,@RequestParam boolean confirm) {
        patientService.deletePatient(id, confirm);
    }
}