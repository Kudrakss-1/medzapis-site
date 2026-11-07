package ru.medzapis.service;

import ru.medzapis.dto.ApiDtos.*;
import ru.medzapis.model.*;
import ru.medzapis.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedzapisService {
    private final UserRepository users;
    private final AppointmentRepository appointments;
    private final PasswordEncoder encoder;
    public MedzapisService(UserRepository users, AppointmentRepository appointments, PasswordEncoder encoder){
        this.users=users; this.appointments=appointments; this.encoder=encoder;
    }
    public UserResponse register(RegisterRequest r){
        if(users.existsByUsername(r.username())) throw new IllegalArgumentException("Это имя пользователя уже занято");
        var u=users.save(new AppUser(r.username(),encoder.encode(r.password()),r.fullName(),Role.PATIENT,null));
        return userDto(u);
    }
    public UserResponse createDoctor(CreateDoctorRequest r){
        if(users.existsByUsername(r.username())) throw new IllegalArgumentException("Это имя пользователя уже занято");
        var u=users.save(new AppUser(r.username(),encoder.encode(r.password()),r.fullName(),Role.DOCTOR,r.specialization()));
        return userDto(u);
    }
    public UserResponse currentUser(String username){ var u=users.findByUsername(username).orElseThrow(); return userDto(u); }
    public List<UserResponse> doctors(){
        return users.findAll().stream().filter(u->u.getRole()==Role.DOCTOR).map(this::userDto).toList();
    }
    public List<UserResponse> allUsers(){return users.findAll().stream().map(this::userDto).toList();}
    @Transactional
    public AppointmentResponse book(String username, BookingRequest r){
        var patient=users.findByUsername(username).orElseThrow(()->new IllegalArgumentException("Пациент не найден"));
        var doctor=users.findById(r.doctorId()).orElseThrow(()->new IllegalArgumentException("Врач не найден"));
        if(doctor.getRole()!=Role.DOCTOR) throw new IllegalArgumentException("Выбранный пользователь не является врачом");
        if(r.appointmentTime().isBefore(LocalDateTime.now())) throw new IllegalArgumentException("Нельзя записаться на прошедшее время");
        if(appointments.existsByDoctorIdAndAppointmentTimeAndStatusNot(doctor.getId(),r.appointmentTime(),AppointmentStatus.CANCELLED))
            throw new IllegalArgumentException("Это время у врача уже занято");
        return appointmentDto(appointments.save(new Appointment(patient,doctor,r.appointmentTime(),r.reason())));
    }
    public List<AppointmentResponse> myAppointments(String username, Role role){
        var u=users.findByUsername(username).orElseThrow();
        var list=role==Role.DOCTOR ? appointments.findByDoctorIdOrderByAppointmentTimeAsc(u.getId())
                                  : appointments.findByPatientIdOrderByAppointmentTimeDesc(u.getId());
        return list.stream().map(this::appointmentDto).toList();
    }
    @Transactional
    public AppointmentResponse updateStatus(Long id, String username, Role role, AppointmentStatus status){
        var a=appointments.findById(id).orElseThrow(()->new IllegalArgumentException("Запись не найдена"));
        if(role==Role.DOCTOR && !a.getDoctor().getUsername().equals(username))
            throw new IllegalArgumentException("Можно изменять только свои приёмы");
        if(role==Role.PATIENT) {
            if(!a.getPatient().getUsername().equals(username))
                throw new IllegalArgumentException("Можно изменять только собственные записи");
            if(status!=AppointmentStatus.CANCELLED)
                throw new IllegalArgumentException("Пациент может только отменить запись");
        }
        a.setStatus(status);
        return appointmentDto(a);
    }
    public List<AppointmentResponse> allAppointments(){return appointments.findAll().stream().map(this::appointmentDto).toList();}
    private UserResponse userDto(AppUser u){return new UserResponse(u.getId(),u.getUsername(),u.getFullName(),u.getRole().name(),u.getSpecialization());}
    private AppointmentResponse appointmentDto(Appointment a){return new AppointmentResponse(a.getId(),a.getPatient().getFullName(),a.getDoctor().getFullName(),a.getDoctor().getSpecialization(),a.getAppointmentTime(),a.getStatus().name(),a.getReason());}
}
