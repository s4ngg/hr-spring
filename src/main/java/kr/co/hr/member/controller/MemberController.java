package kr.co.hr.member.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import kr.co.hr.member.controller.docs.MemberControllerDocs;
import kr.co.hr.member.dto.MemberRequestDTO;
import kr.co.hr.member.dto.MemberResponseDTO;
import kr.co.hr.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController implements MemberControllerDocs{
	
	private final MemberService memberService;
	
	// 전체 직원 조회
	@Override
	@GetMapping
	public ResponseEntity<Page<MemberResponseDTO>> getAllMembers(
	        @PageableDefault(size = 10, sort = "memberId", direction = Sort.Direction.DESC) Pageable pageable,
	        @RequestParam(required = false) String name) {
	    return ResponseEntity.ok(memberService.getMembers(name, pageable));
	}
	
	// 단일 직원 조회
	@Override
	@GetMapping("/{memberId}")
	public ResponseEntity<MemberResponseDTO> getMember(@PathVariable Long memberId) {
		return ResponseEntity.ok(memberService.getMember(memberId));
	}
	
	// 직원 등록
	@Override
	@PostMapping
	public ResponseEntity<MemberResponseDTO> createMember(@RequestBody MemberRequestDTO requestDTO) {
		return ResponseEntity.ok(memberService.createMember(requestDTO));
	}
	
	// 직원 수정
	@Override
	@PutMapping("/{memberId}")
	public ResponseEntity<MemberResponseDTO> updateMember(@PathVariable Long memberId, @RequestBody MemberRequestDTO requestDTO) {
		return ResponseEntity.ok(memberService.updateMember(memberId, requestDTO));
	}
	
	// 직원 삭제
	@Override
	@DeleteMapping("/{memberId}")
	public ResponseEntity<Void> deleteMember(@PathVariable("memberId") Long memberId) {
	memberService.deleteMember(memberId);
	return ResponseEntity.noContent().build();
	}
}