package com.ljuslin.inluppgift1_backend.dto;

import com.ljuslin.inluppgift1_backend.security.Role;

public record CreateMemberDto(
        String firstName,
        String lastName,
        String email,
        String phone,
        String dateOfBirth,
        String username,
        String password,
        Role role,
        CreateAddressDto address
        /*@Past and @PastOrPresent validate that a date value is in the past, or the past including
        the present. We can apply it to date types, including those added in Java 8.
        Så om man använder LocalDateTime som dateOfBirth kan man bara annotera med @Past
        Typ: "@Past(message = "Date of birth must be in the past")"
        */
) {
}
