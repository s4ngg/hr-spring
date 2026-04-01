package kr.co.hr.member.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import kr.co.hr.global.response.ApiResponse;
import kr.co.hr.member.controller.docs.MemberControllerDocs;
import kr.co.hr.member.dto.MemberRequestDTO;
import kr.co.hr.member.dto.MemberResponseDTO;
import kr.co.hr.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<Page<MemberResponseDTO>>> getAllMembers(
            @PageableDefault(size = 10, sort = "memberId", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String name) {
        return ApiResponse.success("직원 목록 조회 성공", memberService.getMembers(name, pageable));
    }

    @Override
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponseDTO>> getMember(
            @PathVariable Long memberId) {
        return ApiResponse.success("직원 조회 성공", memberService.getMember(memberId));
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponseDTO>> createMember(
            @Valid @RequestBody MemberRequestDTO requestDTO) {
        return ApiResponse.success("직원 등록 성공", memberService.createMember(requestDTO));
    }

    @Override
    @PatchMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponseDTO>> updateMember(
            @PathVariable Long memberId,
            @Valid @RequestBody MemberRequestDTO requestDTO) {
        return ApiResponse.success("직원 수정 성공", memberService.updateMember(memberId, requestDTO));
    }

    @Override
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(
            @PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ApiResponse.success("직원 삭제 성공");
    }
}