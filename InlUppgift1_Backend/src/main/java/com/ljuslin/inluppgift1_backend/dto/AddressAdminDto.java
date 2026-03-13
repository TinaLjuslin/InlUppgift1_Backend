package com.ljuslin.inluppgift1_backend.dto;

public record AddressAdminDto(
        Long id,
        String street,
        String postalCode,
        String city
) {
}
