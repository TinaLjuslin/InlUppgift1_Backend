package com.ljuslin.inluppgift1_backend.dto;

public record MemberUserDto(
    String firstName,
    String lastName,
    String email,
    String phone,
    AddressUserDto address
)
{}
