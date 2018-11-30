package org.regeneration.repositories;

import org.regeneration.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Appointment findByDate(String date);
    Appointment findByTime(String time);

}
