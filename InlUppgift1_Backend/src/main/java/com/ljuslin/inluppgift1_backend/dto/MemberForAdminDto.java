package com.ljuslin.inluppgift1_backend.dto;

import com.ljuslin.inluppgift1_backend.security.Role;

public record MemberForAdminDto(
    Long id,
    String firstName,
    String lastName,
    String email,
    String phone,
    String dateOfBirth,
    String username,
    String password,
    Role role,
    AddressDto address
    //roll?
)
{}
