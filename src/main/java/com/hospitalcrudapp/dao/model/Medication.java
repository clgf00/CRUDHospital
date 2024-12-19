package com.hospitalcrudapp.dao.model;

import lombok.*;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Medication {
    @XmlTransient
    private int id;
    @XmlValue
    private String medicationName;
    @XmlTransient
    private int medRecordId;
}