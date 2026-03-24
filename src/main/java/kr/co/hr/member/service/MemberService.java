package kr.co.hr.member.service;

import java.util.List;

import kr.co.hr.member.dto.MemberRequestDTO;
import kr.co.hr.member.dto.MemberResponseDTO;

public interface MemberService {

	List<MemberResponseDTO> getAllMembers();
	
	MemberResponseDTO getMember(Long memberId);
	
	MemberResponseDTO createMember(MemberRequestDTO requestDTO);
	
	MemberResponseDTO updateMember(Long memberId, MemberRequestDTO requestDTO);
	
	void deleteMember(Long memberId);
}