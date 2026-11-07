package ru.medzapis.controller;
import ru.medzapis.dto.ApiDtos.*;
import ru.medzapis.service.MedzapisService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final MedzapisService service;
    public AuthController(MedzapisService service){this.service=service;}
    @PostMapping("/register") public UserResponse register(@Valid @RequestBody RegisterRequest request){return service.register(request);}
    @GetMapping("/me") public UserResponse me(Authentication auth){return service.currentUser(auth.getName());}
}
