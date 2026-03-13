package com.ljuslin.inluppgift1_backend.mapper;

import com.ljuslin.inluppgift1_backend.dto.AddressAdminDto;
import com.ljuslin.inluppgift1_backend.dto.AddressUserDto;
import com.ljuslin.inluppgift1_backend.dto.MemberAdminDto;
import com.ljuslin.inluppgift1_backend.dto.MemberUserDto;
import com.ljuslin.inluppgift1_backend.entity.Address;
import com.ljuslin.inluppgift1_backend.entity.Member;

public final class MemberMapper {

    public static MemberAdminDto toMemberForAdminDto(Member member) {
        MemberAdminDto memberDto = new MemberAdminDto(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getPhone(),
                member.getDateOfBirth(),
                member.getAppUser().getUsername(),
                member.getAppUser().getPassword(),
                member.getAppUser().getRole(),
                toAddressAdminDto(member.getAddress())
        );
        return memberDto;
    }

    public static MemberUserDto toMemberForUserDto(Member member) {
        MemberUserDto dto = new MemberUserDto(

                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getPhone(),
                toAddressUserDto(member.getAddress())
        );
        return dto;

    }

    private static AddressAdminDto toAddressAdminDto(Address address) {
        AddressAdminDto addressDto = new AddressAdminDto(
                address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity());
        return addressDto;
    }

    private static AddressUserDto toAddressUserDto(Address address) {
        AddressUserDto addressDto = new AddressUserDto(
                address.getStreet(),
                address.getPostalCode(),
                address.getCity());
        return addressDto;
    }

    private static Address toAddressFromDto(AddressAdminDto dto) {
        Address address = new Address(
                dto.id(),
                dto.street(),
                dto.postalCode(),
                dto.city());
        return address;
    }

}
