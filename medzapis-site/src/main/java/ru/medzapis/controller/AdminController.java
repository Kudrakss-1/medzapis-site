package ru.medzapis.controller;
import ru.medzapis.dto.ApiDtos.*;
import ru.medzapis.service.MedzapisService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final MedzapisService service;
    public AdminController(MedzapisService service){this.service=service;}
    @PostMapping("/doctors") public UserResponse createDoctor(@Valid @RequestBody CreateDoctorRequest r){return service.createDoctor(r);}
    @GetMapping("/users") public List<UserResponse> users(){return service.allUsers();}
    @GetMapping("/appointments") public List<AppointmentResponse> appointments(){return service.allAppointments();}
}
