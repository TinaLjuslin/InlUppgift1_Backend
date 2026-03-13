package com.ljuslin.inluppgift1_backend.service;

import com.ljuslin.inluppgift1_backend.dto.*;
import com.ljuslin.inluppgift1_backend.entity.Address;
import com.ljuslin.inluppgift1_backend.entity.Member;
import com.ljuslin.inluppgift1_backend.exception.DataConflictException;
import com.ljuslin.inluppgift1_backend.exception.IllegalActionException;
import com.ljuslin.inluppgift1_backend.exception.IllegalDataException;
import com.ljuslin.inluppgift1_backend.exception.MemberNotFoundException;
import com.ljuslin.inluppgift1_backend.mapper.MemberMapper;
import com.ljuslin.inluppgift1_backend.repository.AddressRepository;
import com.ljuslin.inluppgift1_backend.repository.AppUserRepository;
import com.ljuslin.inluppgift1_backend.repository.MemberRepository;
import com.ljuslin.inluppgift1_backend.security.AppUser;
import com.ljuslin.inluppgift1_backend.security.Role;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

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
    public List<MemberAdminDto> findAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(MemberMapper::toMemberForAdminDto)
                .toList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public List<MemberUserDto> findAllMembersForUser() {
        return memberRepository.findAll()
                .stream()
                .map(MemberMapper::toMemberForUserDto)
                .toList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public MemberAdminDto findMemberById(Long id) {
        return memberRepository.findById(id)
                .map(MemberMapper::toMemberForAdminDto)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public MemberAdminDto updateMember(Long id, UpdateMemberAdminDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
        member.setFirstName(dto.firstName());
        member.setLastName(dto.lastName());
        member.setEmail(dto.email());
        member.setPhone(dto.phone());
        checkDateOfBirth(dto.dateOfBirth(), id);
        member.setDateOfBirth(dto.dateOfBirth());
        checkUsername(dto.username(), id);
        member.getAppUser().setUsername(dto.username());
        member.getAppUser().setPassword(checkAndEncodePassword(dto.password()));
        member.getAppUser().setRole(dto.role());
        member.setAddress(checkAndGetAddress(dto.address().street(), dto.address().postalCode(), dto.address().city()));
        Member savedMember = memberRepository.save(member);
        return MemberMapper.toMemberForAdminDto(savedMember);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public MemberAdminDto updateMember(Long id, Map<String, Object> fields) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
        fields.forEach((key, value) -> {
            switch (key) {
                case "firstName" -> member.setFirstName((String) value);
                case "lastName" -> member.setLastName((String) value);
                case "email" -> member.setEmail((String) value);
                case "phone" -> member.setPhone((String) value);
                case "dateOfBirth" -> {
                    checkDateOfBirth((String) value, id);
                    member.setDateOfBirth((String) value);
                }
                case "username" -> {
                    checkUsername((String) value, id);
                    member.getAppUser().setUsername((String) value);
                }
                case "password" ->
                        member.getAppUser().setPassword(checkAndEncodePassword((String) value));
                case "role" -> member.getAppUser().setRole((Role) value);
                case "address" -> {
                    Map<String, String> addressFields = (Map<String, String>) value;
                    String street = addressFields.getOrDefault("street",
                            member.getAddress().getStreet());
                    String postalCode = addressFields.getOrDefault("postalCode",
                            member.getAddress().getPostalCode());
                    String city = addressFields.getOrDefault("city",
                            member.getAddress().getCity());

                    member.setAddress(checkAndGetAddress(street, postalCode, city));
                }
            }
        });

        Member savedMember = memberRepository.save(member);
        return MemberMapper.toMemberForAdminDto(savedMember);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public MemberUserDto updateMemberUser(Long id, UpdateMemberUserDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!currentUser.equals(member.getAppUser().getUsername())) {
            throw new AccessDeniedException("Du har inte tillgång till denna användare.");
        }
        checkDateOfBirth(dto.dateOfBirth(), id);
        member.setId(id);
        member.setFirstName(dto.firstName());
        member.setLastName(dto.lastName());
        member.setEmail(dto.email());
        member.setPhone(dto.phone());
        member.setDateOfBirth(dto.dateOfBirth());
        member.setAddress(checkAndGetAddress(dto.address().street(), dto.address().postalCode(), dto.address().city()));
        Member savedMember = memberRepository.save(member);
        return MemberMapper.toMemberForUserDto(savedMember);

    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public MemberAdminDto createMember(CreateMemberDto dto) {
        checkDateOfBirth(dto.dateOfBirth());
        checkUsername(dto.username());
        //member_id sparas inte i app user
        AppUser appUser = new AppUser(dto.username(), checkAndEncodePassword(dto.password()),
                dto.role());
        Member member = new Member(
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.phone(),
                dto.dateOfBirth(),
                checkAndGetAddress(dto.address().street(), dto.address().postalCode(),
                        dto.address().city()),
                appUser
        );
        appUser.setMember(member);
        Member savedMember = memberRepository.save(member);
        return MemberMapper.toMemberForAdminDto(savedMember);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMember(Long id) {
        Member memberToDelete = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
        String currentUsername = SecurityContextHolder
                .getContext().getAuthentication().getName();

        if (memberToDelete.getAppUser().getUsername().equals(currentUsername)) {
            throw new IllegalActionException("Du kan inte radera ditt eget konto!");
        }
        memberRepository.deleteById(id);
    }

    private void checkUsername(String username, Long id) {
        if (memberRepository.existsByAppUserUsernameAndIdNot(username, id)) {
            throw new DataConflictException("Användarnamn är upptaget.");
        }
    }

    private void checkUsername(String username) {
        if (appUserRepository.existsByUsername(username)) {
            throw new DataConflictException("Användarnamn är upptaget.");
        }
    }

    private String checkAndEncodePassword(String password) {
        if (password != null && !password.isBlank()) {
            return passwordEncoder.encode(password);
        } else {
            throw new IllegalDataException(password);
        }
    }

    private void checkDateOfBirth(String dateOfBirth, Long id) {
        if (memberRepository.existsByDateOfBirthAndIdNot(dateOfBirth, id)) {
            throw new DataConflictException("Personnummer används redan.");
        }
    }

    private void checkDateOfBirth(String dateOfBirth) {
        if (memberRepository.existsByDateOfBirth(dateOfBirth)) {
            throw new DataConflictException("Personnummer används redan.");
        }
    }

    private Address checkAndGetAddress(String street, String postalCode, String city) {
        return addressRepository.findByStreetAndPostalCodeAndCity(street,
                postalCode, city).orElseGet(() ->
                addressRepository.save(new Address(street, postalCode,
                        city)));

    }
}