package com.ljuslin.inluppgift1_backend.service;

import com.ljuslin.inluppgift1_backend.dto.*;

import java.util.List;
import java.util.Map;

public interface MemberService {
    List<MemberAdminDto> findAllMembers();
    MemberAdminDto findMemberById(Long id);
    MemberAdminDto updateMember(Long id, UpdateMemberAdminDto dto);
    MemberAdminDto updateMember(Long id, Map<String, Object> fields);
    MemberAdminDto createMember(CreateMemberDto dto);
    void deleteMember(Long id);
    List<MemberUserDto> findAllMembersForUser();
    MemberUserDto updateMemberUser(Long id, UpdateMemberUserDto dto);
}
