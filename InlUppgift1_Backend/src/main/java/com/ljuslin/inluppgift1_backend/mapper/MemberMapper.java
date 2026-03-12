package com.ljuslin.inluppgift1_backend.mapper;

import com.ljuslin.inluppgift1_backend.dto.AddressDto;
import com.ljuslin.inluppgift1_backend.dto.MemberForAdminDto;
import com.ljuslin.inluppgift1_backend.entity.Address;
import com.ljuslin.inluppgift1_backend.entity.Member;

public final class MemberMapper {
/*public static fromMemberDto(MemberWithAddressDTO memberDto) {

}*/
    public static MemberForAdminDto toMemberForAdminDto(Member member) {
        MemberForAdminDto memberDto = new MemberForAdminDto(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getPhone(),
                member.getDateOfBirth(),
                member.getAppUser().getUsername(),
                member.getAppUser().getPassword(),
                member.getAppUser().getRole(),
                toAddressDto(member.getAddress())
        );
        return memberDto;

    }
    private static AddressDto toAddressDto(Address address) {
        AddressDto addressDto = new AddressDto(address.getStreet(),
                address.getPostalCode(),
                address.getCity());
return addressDto;
    }
    private static Address toAddressFromDto(AddressDto dto) {
        Address address = new Address(dto.street(),
                dto.postalCode(),
                dto.city());
        return address;
    }
/*
//ej korrekt!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static void applyUpdate(Member member, MemberUpdateDto dto) {
        Address address = member.getAddress();
        if (address == null) {
            address = new Address(dto.address().street(), dto.address().postalCode(), dto.address().city());
            member.setAddress(address);
        } else {
            member.getAddress().setStreet(dto.address().street());
            member.getAddress().setPostalCode(dto.address().postalCode());
            member.getAddress().setCity(dto.address().city());
        }
    }*/

}
