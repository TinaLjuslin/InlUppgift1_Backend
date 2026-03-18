package com.ljuslin.inluppgift1_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAddressDto(
        @NotBlank @Size(max = 50)String street,
        @NotBlank @Size(max = 10)String postalCode,
        @NotBlank @Size(max = 50)String city
) {
}
