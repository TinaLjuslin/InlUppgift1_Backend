package com.ljuslin.inluppgift1_backend.security;

import com.ljuslin.inluppgift1_backend.entity.Member;
import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",  nullable = false, length = 10)
    private Role role;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    protected AppUser() {
    }

    public AppUser(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public AppUser(String username, String password, Role role, Member member) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.member = member;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
