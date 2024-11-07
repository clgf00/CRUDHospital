package com.hospitalcrudapp.dao.model;
import com.hospitalcrudapp.dao.mappers.LocalDateAdapter;
import lombok.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlJavaTypeAdapter(LocalDateAdapter.class)
@XmlRootElement(name="medRecord")
@XmlAccessorType(XmlAccessType.FIELD)
public class MedRecord {

    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "idPatient")
    private int idPatient;

    @XmlElement(name = "doctor")
    private int idDoctor;

    @XmlElement(name = "diagnosis")
    private String description;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "date")
    private LocalDate date;
    @XmlElementWrapper(name = "medications")
    @XmlElement(name = "medication")
    private List<Medication> medications = new ArrayList<>();
}

