package com.ljuslin.inluppgift1_backend.controller;

import com.ljuslin.inluppgift1_backend.dto.CreateMemberDto;
import com.ljuslin.inluppgift1_backend.dto.MemberAdminDto;
import com.ljuslin.inluppgift1_backend.dto.UpdateMemberAdminDto;
import com.ljuslin.inluppgift1_backend.service.MemberServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/members")
@PreAuthorize("hasRole('ADMIN')")

public class AdminController {
    private final MemberServiceImpl memberService;

    public AdminController(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<MemberAdminDto> getAllMembers() {
        return memberService.findAllMembers();
    }
    @GetMapping("/{id}")
    public MemberAdminDto getMembers(@PathVariable Long id) {
        return memberService.findMemberById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberAdminDto> updateMember(@PathVariable Long id,
                                                       @RequestBody @Valid UpdateMemberAdminDto dto) {
        return new ResponseEntity<>(memberService.updateMember(id, dto), HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<MemberAdminDto> patchMember(@PathVariable Long id,
                                                      @RequestBody Map<String, Object> fields) {
        return new ResponseEntity<>(memberService.updateMember(id, fields), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<MemberAdminDto> createMember(@RequestBody CreateMemberDto dto) {
        return new ResponseEntity<>(memberService.createMember(dto), HttpStatus.CREATED);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
    /*--------Lista medlemmar GET ”/admin/members” – All data på respektive medlem ska hämtas och
    visas
    ----------Hämta enskild medlem GET ”/admin/members/{id}” – All data på vald medlem ska hämtas
    och visas
    ----------Uppdatera uppgifter PUT ”/admin/members/{id}” – Samtlig data för vald medlem ska
    uppdateras
    ----------Uppdatera uppgifter PATCH ”/admin/members/{id}” – Viss data för vald medlem ska
    uppdateras
    -----------Lägga till medlem POST ”/admin/members” – Ny medlem ska läggas till i databasen
    -----------Ta bort medlem DELETE ”/admin/members/{id}” – Angiven medlem ska raderas från databasen*/

}
