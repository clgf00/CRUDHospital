    package com.hospitalcrudapp.dao.repositories.Files;
    import com.hospitalcrudapp.configuration.Configuration;
    import com.hospitalcrudapp.dao.mappers.DoctorRowMapper;
    import com.hospitalcrudapp.dao.model.Doctor;
    import com.hospitalcrudapp.dao.repositories.DoctorRepository;
    import org.springframework.context.annotation.Profile;
    import org.springframework.stereotype.Repository;
    import java.io.BufferedReader;
    import java.io.BufferedWriter;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.nio.file.StandardOpenOption;
    import java.util.ArrayList;
    import java.util.List;
    
    @Repository
    @Profile("null")
    public class TxtDoctorRepository implements DoctorRepository {
        private final DoctorRowMapper doctorRowMapper;
        private List<Doctor> doctors;
    
        public TxtDoctorRepository(DoctorRowMapper doctorRowMapper) {
            this.doctorRowMapper = doctorRowMapper;
            this.doctors = new ArrayList<>();
        }
    
        private void writeDoctorsToFile(List<Doctor> doctorList, BufferedWriter writer) throws IOException {
            for (Doctor p : doctorList) {
                String doctorData = String.format("%d; %s; %s%n",
                        //%d se refiere a un numero entero, %s se refiere a string y %n es salto de linea
                        // de modo que quedará así: newId; nombre; especialidad
                        p.getId(),
                        p.getName(),
                        p.getSpecialty());
    
                writer.write(doctorData);
            }
        }
    
        private List<String> readFile() {
            List<String> lista = new ArrayList<>();
            Path file = Paths.get(Configuration.getInstance().getProperty("doctorsFilePath"));
            try (BufferedReader reader = Files.newBufferedReader(file)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lista.add(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return lista;
        }

        @Override
        public List<Doctor> getAll() {
            this.doctors = new ArrayList<>();
            List<String> doctorList = readFile();
            for (String line : doctorList) {
                Doctor doctor = doctorRowMapper.mapRow(line);
                if (doctor != null) {
                    doctors.add(doctor);
                }
            }
            return doctors;
        }
    }
