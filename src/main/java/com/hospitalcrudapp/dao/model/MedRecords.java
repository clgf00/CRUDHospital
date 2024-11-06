package com.hospitalcrudapp.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@XmlRootElement(name = "medRecords")
@XmlAccessorType(XmlAccessType.FIELD)
public class MedRecords {
    @XmlElement(name="medRecord")
    private List<MedRecord> medRecords;

    public MedRecords() {
        medRecords = new ArrayList<>();
    }

    public void addMedRecord(MedRecord medRecord) {
        medRecords.add(medRecord);
    }
    @Override
    public String toString() {
        return "MedRecords [medRecords=" + medRecords + "]";
    }
}
