package ru.medzapis.controller;
import ru.medzapis.dto.ApiDtos.UserResponse;
import ru.medzapis.service.MedzapisService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final MedzapisService service;
    public DoctorController(MedzapisService service){this.service=service;}
    @GetMapping public List<UserResponse> doctors(){return service.doctors();}
}
