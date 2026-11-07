package ru.medzapis.config;

import ru.medzapis.model.*;
import ru.medzapis.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean CommandLineRunner seed(UserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (!users.existsByUsername("admin"))
                users.save(new AppUser("admin",encoder.encode("admin123"),"Администратор клиники",Role.ADMIN,null));
            if (!users.existsByUsername("doctor1"))
                users.save(new AppUser("doctor1",encoder.encode("doctor123"),"Иванов Иван",Role.DOCTOR,"Терапевт"));
            if (!users.existsByUsername("doctor2"))
                users.save(new AppUser("doctor2",encoder.encode("doctor123"),"Петрова Анна",Role.DOCTOR,"Кардиолог"));
        };
    }
}
