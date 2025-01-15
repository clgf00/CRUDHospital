package com.hospitalcrudapp.ui;
import com.hospitalcrudapp.domain.model.MedRecordUi;
import com.hospitalcrudapp.domain.services.MedRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class RestMedicalRecord {
    private final MedRecordService medRecordService;

    public RestMedicalRecord(MedRecordService medRecordService) {
        this.medRecordService = medRecordService;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/patients/{idPatient}/medRecords")
    public List<MedRecordUi> getAll(@PathVariable int idPatient) throws Exception {
        return medRecordService.getAll(idPatient);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/patients/medRecords")
    public int addMedRecord(@RequestBody MedRecordUi medRecordui) {
        return medRecordService.add(medRecordui);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/patients/medRecords/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedRecord(@PathVariable int id) throws Exception {
        medRecordService.delete(id);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/patients/medRecords")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMedRecord(@RequestBody MedRecordUi medRecordUi) {
        medRecordService.update(medRecordUi);
    }
}

