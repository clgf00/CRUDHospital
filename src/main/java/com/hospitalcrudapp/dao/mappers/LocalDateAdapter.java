package com.hospitalcrudapp.dao.mappers;


import org.springframework.context.annotation.Profile;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Profile("TxtFiles")
    public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public LocalDate unmarshal(String v) {
            return LocalDate.parse(v, dateFormatter);
        }
        public String marshal(LocalDate v)  {
            return v.format(dateFormatter);
        }
    }
