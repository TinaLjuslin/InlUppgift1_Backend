package com.ljuslin.inluppgift1_backend.controller;

import com.ljuslin.inluppgift1_backend.dto.CreateMemberDto;
import com.ljuslin.inluppgift1_backend.dto.MemberForAdminDto;
import com.ljuslin.inluppgift1_backend.dto.UpdateMemberDto;
import com.ljuslin.inluppgift1_backend.service.MemberServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/members")
@PreAuthorize("hasRole('ADMIN')")

public class AdminController {
    private final MemberServiceImpl memberService;

    public AdminController(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<MemberForAdminDto> getAllMembers() {
        return memberService.findAllMembers();
    }
    @GetMapping("/{id}")
    public MemberForAdminDto getMembers(@PathVariable Long id) {
        return memberService.findMemberById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberForAdminDto> updateMember(@PathVariable Long id,
                                     @RequestBody @Valid UpdateMemberDto dto) {
        return new ResponseEntity<>(memberService.updateMember(id, dto), HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<MemberForAdminDto> patchMember(@PathVariable Long id,
                                                         @RequestBody Map<String, Object> fields) {
        return new ResponseEntity<>(memberService.updateMember(id, fields), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<MemberForAdminDto> createMember(@RequestBody CreateMemberDto dto) {
        memberService.createMember(dto);
        return null;
    }
    /*----------Lista medlemmar GET ”/admin/members” – All data på respektive medlem ska hämtas och
    visas
    ------------Hämta enskild medlem GET ”/admin/members/{id}” – All data på vald medlem ska hämtas
    och visas
    -----------Uppdatera uppgifter PUT ”/admin/members/{id}” – Samtlig data för vald medlem ska
    uppdateras
    -----------Uppdatera uppgifter PATCH ”/admin/members/{id}” – Viss data för vald medlem ska
    uppdateras
    Lägga till medlem POST ”/admin/members” – Ny medlem ska läggas till i databasen
    Ta bort medlem DELETE ”/admin/members/{id}” – Angiven medlem ska raderas från databasen*/

}
