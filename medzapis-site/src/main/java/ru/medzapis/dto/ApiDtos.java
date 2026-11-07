package ru.medzapis.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class ApiDtos {
    public record RegisterRequest(@NotBlank String username, @Size(min=6) String password, @NotBlank String fullName) {}
    public record CreateDoctorRequest(@NotBlank String username, @Size(min=6) String password,
                                      @NotBlank String fullName, @NotBlank String specialization) {}
    public record BookingRequest(@NotNull Long doctorId, @NotNull LocalDateTime appointmentTime, String reason) {}
    public record StatusRequest(@NotNull ru.medzapis.model.AppointmentStatus status) {}
    public record UserResponse(Long id, String username, String fullName, String role, String specialization) {}
    public record AppointmentResponse(Long id, String patient, String doctor, String specialization,
                                      LocalDateTime appointmentTime, String status, String reason) {}
}
