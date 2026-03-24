package kr.co.hr.member.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
	
	// 전체 직원 조회
	@Operation(summary = "전체 직원 조회", description = "등록된 모든 직원 목록을 조회합니다.")
	@GetMapping
	public ResponseEntity<List<MemberResponseDTO>> getAllMembers() {
	    return ResponseEntity.ok(memberService.getAllMembers());
	}
	
	// 단일 직원 조회
	@Operation(summary = "단일 직원 조회", description = "특정 직원의 정보를 조회합니다.")
	@GetMapping("/{memberId}")
	public ResponseEntity<MemberResponseDTO> getMember(@PathVariable("memberId") Long memberId) {
		return ResponseEntity.ok(memberService.getMember(memberId));
	}
	
	// 직원 등록
	@Operation(summary = "직원 등록", description = "새로운 직원을 등록합니다.")
	@PostMapping
	public ResponseEntity<MemberResponseDTO> createMember(@RequestBody MemberRequestDTO requestDTO) {
		return ResponseEntity.ok(memberService.createMember(requestDTO));
	}
	
	// 직원 수정
	@Operation(summary = "직원 정보 수정", description = "특정 직원의 정보를 수정합니다.")
	@PutMapping("/{memberId}")
	public ResponseEntity<MemberResponseDTO> updateMember(@PathVariable("memberId") Long memberId, @RequestBody MemberRequestDTO requestDTO) {
		return ResponseEntity.ok(memberService.updateMember(memberId, requestDTO));
	}
	
	// 직원 삭제
	@Operation(summary = "직원 삭제", description = "특정 직원을 삭제합니다.")
	@DeleteMapping("/{memberId}")
	public ResponseEntity<Void> deleteMember(@PathVariable("memberId") Long memberId) {
	memberService.deleteMember(memberId);
	return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "직원 이름 검색", description = "이름으로 직원을 검색합니다.")
	@GetMapping("/search")
	public ResponseEntity<Page<MemberResponseDTO>> getAllMembers(
	        @PageableDefault(size = 10, sort = "memberId", direction = Sort.Direction.DESC)
	        Pageable pageable){
	    return ResponseEntity.ok(memberService.getAllMembers(pageable));
	}
}
