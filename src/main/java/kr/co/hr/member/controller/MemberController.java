package kr.co.hr.member.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.hr.global.response.ApiResponse;
import kr.co.hr.member.dto.MemberRequestDTO;
import kr.co.hr.member.dto.MemberResponseDTO;
import kr.co.hr.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@Tag(name = "직원 관리", description = "직원 관련 API")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "전체 직원 조회 + 이름 검색 (페이징)")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<MemberResponseDTO>>> getMembers(
            @RequestParam(value = "name", required = false) String name,
            @PageableDefault(size = 10, sort = "memberId", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("직원 목록 조회 성공", memberService.getMembers(name, pageable)));
    }

    @Operation(summary = "단일 직원 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponseDTO>> getMember(
            @Parameter(description = "직원 ID", required = true)
            @PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(ApiResponse.success("직원 조회 성공", memberService.getMember(memberId)));
    }

    @Operation(summary = "직원 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponseDTO>> createMember(
            @Valid @RequestBody MemberRequestDTO requestDTO) {
        return ResponseEntity.ok(ApiResponse.success("직원 등록 성공", memberService.createMember(requestDTO)));
    }

    @Operation(summary = "직원 정보 수정")
    @PatchMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponseDTO>> updateMember(
            @PathVariable("memberId") Long memberId,
            @Valid @RequestBody MemberRequestDTO requestDTO) {
        return ResponseEntity.ok(ApiResponse.success("직원 수정 성공", memberService.updateMember(memberId, requestDTO)));
    }

    @Operation(summary = "직원 삭제")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(
            @PathVariable("memberId") Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok(ApiResponse.success("직원 삭제 성공"));
    }
}