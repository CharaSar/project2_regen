package org.regeneration.exceptions;

public class DoctorAppointmentConflictException extends RuntimeException {
    public DoctorAppointmentConflictException() {
        super("Doctor is not available at selected date/time.");
    }
}
