package ru.medzapis.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false) private AppUser patient;
    @ManyToOne(optional=false) private AppUser doctor;
    @Column(nullable=false) private LocalDateTime appointmentTime;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private AppointmentStatus status;
    private String reason;

    protected Appointment() {}
    public Appointment(AppUser patient, AppUser doctor, LocalDateTime appointmentTime, String reason) {
        this.patient=patient; this.doctor=doctor; this.appointmentTime=appointmentTime;
        this.reason=reason; this.status=AppointmentStatus.BOOKED;
    }
    public Long getId(){return id;}
    public AppUser getPatient(){return patient;}
    public AppUser getDoctor(){return doctor;}
    public LocalDateTime getAppointmentTime(){return appointmentTime;}
    public AppointmentStatus getStatus(){return status;}
    public String getReason(){return reason;}
    public void setStatus(AppointmentStatus status){this.status=status;}
}
