package com.ljuslin.inluppgift1_backend.repository;

import com.ljuslin.inluppgift1_backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsByDateOfBirthAndIdNot(String dateOfBirth, Long id);
    boolean existsByDateOfBirth(String dateOfBirth);
    boolean existsByAppUserUsernameAndIdNot(String username, Long id);

}
