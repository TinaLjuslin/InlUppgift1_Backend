package com.ljuslin.inluppgift1_backend.dto;

public record CreateAddressDto(
        String street,
        String postalCode,
        String city
) {
}
/*kolla upp @Pattern
@NotBlank
    @Pattern(regexp = "^\d{5}$", message = "Postal code must be exactly 5 digits")
    String postalCode,
 */