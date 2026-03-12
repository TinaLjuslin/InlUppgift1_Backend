package com.ljuslin.inluppgift1_backend.repository;

import com.ljuslin.inluppgift1_backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsByDateOfBirthAndIdNot(String dateOfBirth, Long id);
}
