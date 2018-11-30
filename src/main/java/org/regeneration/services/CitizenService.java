package org.regeneration.services;

import org.regeneration.dto.EditAppointmentDTO;
import org.regeneration.dto.NewAppointmentDTO;
import org.regeneration.exceptions.*;
import org.regeneration.models.*;
import org.regeneration.repositories.AppointmentRepository;
import org.regeneration.repositories.DoctorRepository;
import org.regeneration.repositories.SpecialtyRepository;
import org.regeneration.repositories.UserRepository;
import org.regeneration.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {

    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SpecialtyRepository specialtyRepository;


    @PreAuthorize("hasRole('CITIZEN')")
    public Appointment createCitizenAppointment(@RequestBody NewAppointmentDTO newAppointmentDTO, Principal principal) {
        Appointment appointment = new Appointment();
        User loggedInUser = userRepository.findByUsername(principal.getName());
        if (loggedInUser.getRole() == Role.CITIZEN) {
            Citizen citizen = loggedInUser.getCitizen();

            LocalDateTime ldt = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String now = format.format(ldt);

            if (newAppointmentDTO.getDoctorId() == 0) {
                throw new DoctorNotFoundException(0L);
            }

            if (newAppointmentDTO.getDate().compareTo(now) < 0) {
                throw new AppointmentInPastException();
            }

            Doctor doctor = doctorRepository.findById(newAppointmentDTO.getDoctorId()).get();
            for (Appointment doctorAppointment : doctor.getAppointments()) {
                if (doctorAppointment.getDate().equals(newAppointmentDTO.getDate()) && doctorAppointment.getTime().equals((newAppointmentDTO.getTime()))) {
                    throw new DoctorAppointmentConflictException();
                }
            }
            for (Appointment citizenAppointment : citizen.getAppointments()) {
                if (citizenAppointment.getDate().equals(newAppointmentDTO.getDate()) && citizenAppointment.getTime().equals((newAppointmentDTO.getTime()))) {
                    throw new CitizenAppointmentConflictException();
                }
            }
            appointment.setCitizen(citizen);
            appointment.setDoctor(doctor);
            appointment.setDate(newAppointmentDTO.getDate());
            appointment.setTime(newAppointmentDTO.getTime());
            appointment.setIllnessHistory(newAppointmentDTO.getIllnessHistory());
            appointment.setNotes(newAppointmentDTO.getNotes());
            appointment = appointmentRepository.save(appointment);
            return appointment;
        }
        return null;
    }

    @PreAuthorize("hasRole('CITIZEN')")
    //@PostAuthorize("returnObject.citizen.user.username == authentication.principal.user.username") todo an mas erthei empneusi!!
    public Appointment getAppointmentById(@PathVariable Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @PreAuthorize("hasRole('CITIZEN')")
    public void deleteAppointmentById(@PathVariable Long appointmentId) {
        getAppointmentById(appointmentId);
        appointmentRepository.deleteById(appointmentId);
    }

    @PreAuthorize("hasRole('CITIZEN')")
    public Appointment editAppointment(@RequestBody EditAppointmentDTO editAppointmentDTO, Principal principal) {

        Appointment appointmentToEdit = appointmentRepository.findById(editAppointmentDTO.getAppointmentId()).get();
        Doctor doctor = doctorRepository.findById(appointmentToEdit.getDoctor().getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException(appointmentToEdit.getDoctor().getDoctorId()));

        User loggedInUser = userRepository.findByUsername(principal.getName());
        Citizen citizen = loggedInUser.getCitizen();

        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = format.format(ldt);

        if (editAppointmentDTO.getDate().compareTo(now) < 0) {
            throw new AppointmentInPastException();
        }

        for (Appointment doctorsAppointment : doctor.getAppointments()) {
            if (doctorsAppointment.getAppointmentId() != editAppointmentDTO.getAppointmentId()) {//apofeugw to na sygkrinw to idio rantevou
                if (doctorsAppointment.getDate().equals(editAppointmentDTO.getDate()) && doctorsAppointment.getTime().equals(editAppointmentDTO.getTime())) {
                    throw new DoctorAppointmentConflictException();
                }
            }
        }
        for (Appointment citizenAppointment : citizen.getAppointments()) {
            if (citizenAppointment.getAppointmentId() != editAppointmentDTO.getAppointmentId()) {//apofeugw to na sygkrinw to idio rantevou
                if (citizenAppointment.getDate().equals(editAppointmentDTO.getDate()) && citizenAppointment.getTime().equals(editAppointmentDTO.getTime())) {
                    throw new CitizenAppointmentConflictException();
                }
            }
        }
        appointmentToEdit.setDate(editAppointmentDTO.getDate());
        appointmentToEdit.setTime(editAppointmentDTO.getTime());
        appointmentToEdit.setIllnessHistory(editAppointmentDTO.getIllnessHistory());
        appointmentToEdit.setNotes(editAppointmentDTO.getNotes());

        appointmentRepository.save(appointmentToEdit);
        return appointmentToEdit;
    }

    @PreAuthorize("hasRole('CITIZEN')")
    public List<Specialty> getSpecialties() {
        return specialtyRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_CITIZEN')")
    public List<Doctor> getDoctors(@PathVariable Long specialtyId) {
        List<Doctor> doctors = new ArrayList<Doctor>();
        Optional<Specialty> specialtyOptional = specialtyRepository.findById(specialtyId);
        if (specialtyOptional.isPresent()) {
            Specialty specialty = specialtyOptional.get();
            doctors = doctorRepository.findBySpecialty(specialty);
            if (!doctors.isEmpty()) {
                return doctors;
            } else {
                throw new DoctorWithSelectedSpecilatyNotFoundException(specialty.getSpecialty());
            }
        } else {
            throw new SpecialtyNotFoundException(specialtyId);
        }
    }

    @PreAuthorize("hasRole('CITIZEN')")
    public List<Appointment> getCitAppointments(@RequestParam(value = "fromDate", defaultValue = "") String fromDate,
                                                @RequestParam(value = "toDate", defaultValue = "") String toDate,
                                                @RequestParam(value = "specialty", defaultValue = "") String specialty,
                                                Principal principal) {

        User loggedInUser = userRepository.findByUsername(principal.getName());
        if (loggedInUser.getRole() == Role.CITIZEN) {
            Long citId = loggedInUser.getCitizen().getCitizenId();
            List<Appointment> appointments = appointmentRepository.findAll();
            List<Appointment> response = new ArrayList<>();
            if (fromDate.equals("")) {
                LocalDateTime ldt = LocalDateTime.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String now = format.format(ldt);
                fromDate = now;
            }
            if (toDate.equals("")) {
                //from+1 month
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                TemporalAccessor date = formatter.parse(fromDate);
                LocalDate ldt = LocalDate.from(date).plusMonths(1L);
                String fromDatePlusOneMonth = formatter.format(ldt);
                toDate = fromDatePlusOneMonth;
            }
            if (specialty.equals("")) {
                for (Appointment a : appointments) {
                    if (a.getDate().compareTo(fromDate) >= 0 && a.getDate().compareTo(toDate) <= 0 && a.getCitizen().getCitizenId() == citId) {
                        response.add(a);
                    }
                }
            } else {
                for (Appointment a : appointments) {
                    if (a.getDate().compareTo(fromDate) >= 0 && a.getDate().compareTo(toDate) <= 0 && a.getCitizen().getCitizenId() == citId) {
                        if (a.getDoctor().getSpecialty().getSpecialtyId() == Long.parseLong(specialty)) {
                            response.add(a);
                        }
                    }
                }
            }
            return response;
        }
        return null;
    }

}
