package com.ljuslin.inluppgift1_backend.controller;

import com.ljuslin.inluppgift1_backend.dto.MemberUserDto;
import com.ljuslin.inluppgift1_backend.dto.UpdateMemberAdminDto;
import com.ljuslin.inluppgift1_backend.dto.UpdateMemberUserDto;
import com.ljuslin.inluppgift1_backend.service.MemberServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mypages/members")
@PreAuthorize("hasRole('USER')")

public class MemberController {
    private final MemberServiceImpl memberService;

    public MemberController(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }
    @GetMapping
    public List<MemberUserDto> getAllMembers() {
        return memberService.findAllMembersForUser();
    }
    @PutMapping("/{id}")
    public ResponseEntity<MemberUserDto> updateMember(@PathVariable Long id,
                                                      @RequestBody @Valid UpdateMemberUserDto dto) {
        return new ResponseEntity<>(memberService.updateMemberUser(id, dto), HttpStatus.OK);
    }


}
/*
Lista medlemmar GET ”/mypages/members” – firstName, lastName, address, email och phone på
samtliga medlemmar ska hämtas och visas
Uppdatera uppgifter PUT ”/mypages/members/{id}” – Data för den inloggade medlemmen ska
uppdateras*/
