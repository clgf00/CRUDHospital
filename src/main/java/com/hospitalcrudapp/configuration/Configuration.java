package com.hospitalcrudapp.configuration;

import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

@Component
public class Configuration {
    private static Configuration instance=null;
    private Properties properties;

    private Configuration() {
        try {
            properties = new Properties();
            properties.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream("configFiles/properties.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration getInstance() {
        if (instance==null) {
            instance=new Configuration();
        }
        return instance;
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    public String getPatientFilePath() {
        return properties.getProperty("data/patients.csv");
    }
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        try (OutputStream output = new FileOutputStream("src/main/resources/configFiles/properties.xml")) {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDoctorFilePath() {
        return properties.getProperty("data/doctors.csv");
    }

    public int getNextIdPatient() {
        String nextIdStr = properties.getProperty("nextIdPatient");
        return Integer.parseInt(nextIdStr);
    }


    public int getNextIdDoctor() {
        return Integer.parseInt(properties.getProperty("nextIdDoctor"));
    }

}
