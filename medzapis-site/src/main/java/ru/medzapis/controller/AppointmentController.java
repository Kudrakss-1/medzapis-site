package ru.medzapis.controller;
import ru.medzapis.dto.ApiDtos.*;
import ru.medzapis.model.Role;
import ru.medzapis.service.MedzapisService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final MedzapisService service;
    public AppointmentController(MedzapisService service){this.service=service;}
    @PostMapping public AppointmentResponse book(Authentication auth,@Valid @RequestBody BookingRequest request){return service.book(auth.getName(),request);}
    @GetMapping("/my") public List<AppointmentResponse> mine(Authentication auth){
        // Роль определяется по учётной записи внутри сервиса через endpoint ниже.
        return service.myAppointments(auth.getName(), currentRole(auth));
    }
    private Role currentRole(Authentication auth){
        if(auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))) return Role.ADMIN;
        if(auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_DOCTOR"))) return Role.DOCTOR;
        return Role.PATIENT;
    }
    @PatchMapping("/{id}/status") public AppointmentResponse status(Authentication auth,@PathVariable Long id,@Valid @RequestBody StatusRequest request){
        Role role=currentRole(auth);
        return service.updateStatus(id,auth.getName(),role,request.status());
    }
}
