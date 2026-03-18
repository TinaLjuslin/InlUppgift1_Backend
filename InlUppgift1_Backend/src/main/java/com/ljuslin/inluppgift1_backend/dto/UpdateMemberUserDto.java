package com.ljuslin.inluppgift1_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateMemberUserDto(
        @NotBlank @Size(max = 50)String firstName,
        @NotBlank @Size(max = 100)String lastName,
        @NotBlank @Size(max = 100) @Email String email,
        @Size(max = 20) String phone,
        @NotBlank @Size(max = 20) String dateOfBirth,
        AddressAdminDto address
) {
}
