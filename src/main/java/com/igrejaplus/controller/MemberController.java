package com.igrejaplus.controller;

import com.igrejaplus.dto.member.MemberResponse;
import com.igrejaplus.dto.member.UpdateMemberRequest;
import com.igrejaplus.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> GetMyProfile() {
        return ResponseEntity.ok(memberService.getMyProfile());
    }

    @PutMapping("/me")
    public ResponseEntity<MemberResponse> UpdateMyProfile(@RequestBody UpdateMemberRequest dto) {
        return ResponseEntity.ok(memberService.updateMyProfile(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MemberResponse>> listAll() {
        return ResponseEntity.ok(memberService.listAllProfiles());
    }
}
