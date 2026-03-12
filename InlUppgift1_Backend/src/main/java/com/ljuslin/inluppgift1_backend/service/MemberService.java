package com.ljuslin.inluppgift1_backend.service;

import com.ljuslin.inluppgift1_backend.dto.CreateMemberDto;
import com.ljuslin.inluppgift1_backend.dto.MemberForAdminDto;
import com.ljuslin.inluppgift1_backend.dto.UpdateMemberDto;

import java.util.List;
import java.util.Map;

public interface MemberService {
    List<MemberForAdminDto> findAllMembers();
    MemberForAdminDto findMemberById(Long id);
    MemberForAdminDto updateMember(Long id, UpdateMemberDto dto);
    MemberForAdminDto updateMember(Long id, Map<String, Object> fields);
    MemberForAdminDto createMember(CreateMemberDto dto);
}
