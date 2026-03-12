package com.ljuslin.inluppgift1_backend.repository;

import com.ljuslin.inluppgift1_backend.security.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUsername(String username);
    Optional<AppUser> findByUsername(String username);
}
