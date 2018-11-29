package org.regeneration.controllers;

import org.regeneration.dto.EditAppointmentDTO;
import org.regeneration.dto.NewAppointmentDTO;
import org.regeneration.models.Appointment;
import org.regeneration.models.Doctor;
import org.regeneration.models.Specialty;
import org.regeneration.services.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class CitizenController {

    @Autowired
    CitizenService citizenService;

    @PostMapping("/api/citizen/appointment")
    public Appointment createCitizenAppointment(@RequestBody NewAppointmentDTO newAppointmentDTO, Principal principal) {
        return citizenService.createCitizenAppointment(newAppointmentDTO, principal);
    }


    @GetMapping("/api/citizen/appointment/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return citizenService.getAppointmentById(id);
    }


    @DeleteMapping("/api/citizen/appointment/{appointmentId}")
    public void deleteAppointmentById(@PathVariable Long appointmentId) {
        citizenService.deleteAppointmentById(appointmentId);
    }

    @PutMapping("/api/citizen/appointment")
    public Appointment editAppointment(@RequestBody EditAppointmentDTO editAppointmentDTO, Principal principal) {
        return citizenService.editAppointment(editAppointmentDTO, principal);
    }

    @GetMapping("/api/citizen/specialties")
    public List<Specialty> getSpecialties(Authentication authentication) {
        return citizenService.getSpecialties();
    }

    @GetMapping("/api/citizen/doctor/{specialtyId}")
    public List<Doctor> getDoctorsBySpecialtyId(@PathVariable Long specialtyId) {
        return citizenService.getDoctors(specialtyId);
    }


    @GetMapping("/api/citizen/appointments")
    public List<Appointment> getCitAppointments(@RequestParam(value = "fromDate", defaultValue = "") String fromDate,
                                                @RequestParam(value = "toDate", defaultValue = "") String toDate,
                                                @RequestParam(value = "specialty", defaultValue = "") String specialty,
                                                Principal principal) {
        return citizenService.getCitAppointments(fromDate,toDate,specialty, principal);
    }
}