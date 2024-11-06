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
    @Profile("active")
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
    
        @Override
        public int add(Doctor doctor) {
            int newId = Integer.parseInt(Configuration.getInstance().getProperty("nextId"));
            Configuration.getInstance().setProperty("nextId", String.valueOf(newId + 1));
            Path file = Paths.get(Configuration.getInstance().getProperty("doctorsFilePath"));
            try (BufferedWriter writer = Files.newBufferedWriter(file, StandardOpenOption.APPEND)) {
                writeDoctorsToFile(doctors, writer);
            } catch (IOException e) {
                return 0;
            }
            return newId;
        }
    
        @Override
        public void update(Doctor doctor) {
            List<Doctor> lista = getAll();
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId() == doctor.getId()) {
                    lista.set(i, doctor);
                    break;
                }
            }
            Path file = Paths.get(Configuration.getInstance().getProperty("doctorsFilePath"));
            try (BufferedWriter writer = Files.newBufferedWriter(file, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
                try {
                    writeDoctorsToFile(lista, writer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    
        @Override
        public void delete(int id) {
            List<Doctor> doctorList = getAll();
            doctorList.removeIf(doctor -> doctor.getId() == id);
            Path file = Paths.get(Configuration.getInstance().getProperty("doctorsFilePath"));
            try (BufferedWriter writer = Files.newBufferedWriter(file, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
                writeDoctorsToFile(doctorList, writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
