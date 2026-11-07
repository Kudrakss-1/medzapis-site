package ru.medzapis.repository;
import ru.medzapis.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment> findByPatientIdOrderByAppointmentTimeDesc(Long patientId);
    List<Appointment> findByDoctorIdOrderByAppointmentTimeAsc(Long doctorId);
    boolean existsByDoctorIdAndAppointmentTimeAndStatusNot(Long doctorId, LocalDateTime time, AppointmentStatus status);
}
