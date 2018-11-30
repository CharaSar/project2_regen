package org.regeneration.exceptions;

public class CitizenAppointmentConflictException extends RuntimeException {
    public CitizenAppointmentConflictException() {
        super("There is already an appointment at selected date/time!");
    }
}
