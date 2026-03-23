package kr.co.hr.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.hr.member.dto.MemberRequestDTO;
import kr.co.hr.member.dto.MemberResponseDTO;
import kr.co.hr.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	// 전체 직원 조회
	@GetMapping
	public ResponseEntity<List<MemberResponseDTO>> getAllMembers() {
	    return ResponseEntity.ok(memberService.getAllMembers());
	}
	
	// 단일 직원 조회
	@GetMapping("/{memberId}")
	public ResponseEntity<MemberResponseDTO> getMember(@PathVariable Long memberId) {
		return ResponseEntity.ok(memberService.getMember(memberId));
	}
	
	// 직원 등록
	@PostMapping
	public ResponseEntity<MemberResponseDTO> createMember(@RequestBody MemberRequestDTO requestDTO) {
		return ResponseEntity.ok(memberService.createMember(requestDTO));
	}
	
	// 직원 수정
	@PutMapping("/{memberId}")
	public ResponseEntity<MemberResponseDTO> updateMember(@PathVariable Long memberId, @RequestBody MemberRequestDTO requestDTO) {
		return ResponseEntity.ok(memberService.updateMember(memberId, requestDTO));
	}
	
	// 직원 삭제
	@DeleteMapping("/{memberId}")
	public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
	memberService.deleteMember(memberId);
	return ResponseEntity.noContent().build();
	}
}
