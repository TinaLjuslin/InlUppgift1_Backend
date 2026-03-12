package com.ljuslin.inluppgift1_backend.controller;

import com.ljuslin.inluppgift1_backend.service.MemberServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypages/members")
public class MemberController {
    private final MemberServiceImpl memberService;

    public MemberController(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

}
