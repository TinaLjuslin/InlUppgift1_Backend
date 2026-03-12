package com.ljuslin.inluppgift1_backend.service;

import com.ljuslin.inluppgift1_backend.dto.CreateMemberDto;
import com.ljuslin.inluppgift1_backend.dto.MemberForAdminDto;
import com.ljuslin.inluppgift1_backend.dto.UpdateMemberDto;
import com.ljuslin.inluppgift1_backend.entity.Address;
import com.ljuslin.inluppgift1_backend.entity.Member;
import com.ljuslin.inluppgift1_backend.exception.DateOfBirthAlreadyExistsException;
import com.ljuslin.inluppgift1_backend.exception.IllegalPasswordException;
import com.ljuslin.inluppgift1_backend.exception.MemberNotFoundException;
import com.ljuslin.inluppgift1_backend.mapper.MemberMapper;
import com.ljuslin.inluppgift1_backend.repository.AddressRepository;
import com.ljuslin.inluppgift1_backend.repository.AppUserRepository;
import com.ljuslin.inluppgift1_backend.repository.MemberRepository;
import com.ljuslin.inluppgift1_backend.security.AppUser;
import com.ljuslin.inluppgift1_backend.security.Role;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository,
                             AddressRepository addressRepository,
                             AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.addressRepository = addressRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public List<MemberForAdminDto> findAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(MemberMapper::toMemberForAdminDto)
                .toList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public MemberForAdminDto findMemberById(Long id) {
        return memberRepository.findById(id)
                .map(MemberMapper::toMemberForAdminDto)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public MemberForAdminDto updateMember(Long id, UpdateMemberDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
        member.setId(id);
        member.setFirstName(dto.firstName());
        member.setLastName(dto.lastName());
        member.setEmail(dto.email());
        member.setPhone(dto.phone());

        if (memberRepository.existsByDateOfBirthAndIdNot(dto.dateOfBirth(), id)) {
            throw new DateOfBirthAlreadyExistsException();
        }
        member.setDateOfBirth(dto.dateOfBirth());

        member.getAppUser().setUsername(dto.username());
        if (dto.password() != null && !dto.password().isBlank()) {
            member.getAppUser().setPassword(passwordEncoder.encode(dto.password()));
        } else {
            throw new IllegalPasswordException(dto.password());
        }
        member.getAppUser().setRole(dto.role());
        Address address = addressRepository.findByStreetAndPostalCodeAndCity(
                        dto.address().street(), dto.address().postalCode(), dto.address().city())
                .orElseGet(() ->
                        addressRepository.save(new Address(dto.address().street(), dto.address().postalCode(),
                                dto.address().city())));
        member.setAddress(address);
        Member savedMember = memberRepository.save(member);
        return MemberMapper.toMemberForAdminDto(savedMember);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public MemberForAdminDto updateMember(Long id, Map<String, Object> fields) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
        fields.forEach((key, value) -> {
            switch (key) {
                case "firstName" -> member.setFirstName((String) value);
                case "lastName" -> member.setLastName((String) value);
                case "email" -> member.setEmail((String) value);
                case "phone" -> member.setPhone((String) value);
                case "dateOfBirth" -> {
                    if (memberRepository.existsByDateOfBirthAndIdNot((String) value, id)) {
                        throw new DateOfBirthAlreadyExistsException();
                    }
                    member.setDateOfBirth((String) value);
                }
                case "username" -> member.getAppUser().setUsername((String) value);
                case "password" -> member.getAppUser().setPassword((String) value);
                case "role" -> //kolla så roll stämmer

                        member.getAppUser().setRole((Role) value);
                // value);
                case "address" -> {
                    Map<String, String> addressFields = (Map<String, String>) value;
                    String street = addressFields.getOrDefault("street",
                            member.getAddress().getStreet());
                    String postalCode = addressFields.getOrDefault("postalCode",
                            member.getAddress().getPostalCode());
                    String city = addressFields.getOrDefault("city",
                            member.getAddress().getCity());
                    Address address =
                            addressRepository.findByStreetAndPostalCodeAndCity(street,
                                    postalCode, city).orElseGet(() ->
                                    addressRepository.save(new Address(street, postalCode,
                                            city)));

                    member.setAddress(address);
                }
            }
        });

        Member savedMember = memberRepository.save(member);
        return MemberMapper.toMemberForAdminDto(savedMember);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public MemberForAdminDto createMember(CreateMemberDto dto) {
        //kolla dateOfBirth
        //kolla username


        return null;
    }
}
/*@PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.username")
public MemberForAdminDto update(String email, MemberUpdateDto dto) { ... }*/