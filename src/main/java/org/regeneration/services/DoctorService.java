package org.regeneration.services;

import org.regeneration.exceptions.AppointmentNotFoundException;
import org.regeneration.exceptions.CitizenNotFoundException;
import org.regeneration.models.Appointment;
import org.regeneration.models.Citizen;
import org.regeneration.models.User;
import org.regeneration.repositories.AppointmentRepository;
import org.regeneration.repositories.CitizenRepository;
import org.regeneration.repositories.DoctorRepository;
import org.regeneration.repositories.UserRepository;
import org.regeneration.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    CitizenRepository citizenRepository;

    @PreAuthorize("hasRole('DOCTOR')")
    public List<Appointment> getAppointments(@RequestParam(value = "fromDate", defaultValue = "") String fromDate,
                                             @RequestParam(value = "toDate", defaultValue = "") String toDate,
                                             @RequestParam(value = "search", defaultValue = "") String search,
                                             Principal principal) {

        User loggedInUser = userRepository.findByUsername(principal.getName());

        if (loggedInUser.getRole() == Role.DOCTOR) {

            Long docId = loggedInUser.getDoctor().getDoctorId();

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

            if (search.equals("")) {
                for (Appointment a : appointments) {
                    if (a.getDate().compareTo(fromDate) >= 0 && a.getDate().compareTo(toDate) <= 0 && a.getDoctor().getDoctorId() == docId) {
                        response.add(a);
                    }
                }
            } else {
                for (Appointment a : appointments) {
                    if (a.getDate().compareTo(fromDate) >= 0 && a.getDate().compareTo(toDate) <= 0 && a.getDoctor().getDoctorId() == docId) {
                        if (a.getIllnessHistory().toLowerCase().contains(search.toLowerCase())) {
                            response.add(a);
                        }
                    }
                }
            }
            return response;
        }
        return null;
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostAuthorize("returnObject.doctor.user.username == authentication.principal.username")
    public Appointment doctorGetAppointment(@PathVariable Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    public Citizen getCitizen(@PathVariable Long id) {
        return citizenRepository.findById(id)
                .orElseThrow(() -> new CitizenNotFoundException(id));
    }

}