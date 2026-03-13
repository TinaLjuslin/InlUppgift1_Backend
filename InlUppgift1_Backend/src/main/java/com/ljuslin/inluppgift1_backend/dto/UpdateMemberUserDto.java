package com.ljuslin.inluppgift1_backend.dto;

public record UpdateMemberUserDto(
        String firstName,
        String lastName,
        String email,
        String phone,
        String dateOfBirth,
        AddressAdminDto address
) {
}
