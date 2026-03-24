package kr.co.hr.member.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.hr.member.dto.MemberRequestDTO;
import kr.co.hr.member.dto.MemberResponseDTO;

public interface MemberService {

	List<MemberResponseDTO> getAllMembers();
	
	MemberResponseDTO getMember(Long memberId);
	
	MemberResponseDTO createMember(MemberRequestDTO requestDTO);
	
	MemberResponseDTO updateMember(Long memberId, MemberRequestDTO requestDTO);
	
	void deleteMember(Long memberId);
	
	List<MemberResponseDTO> searchByName(String name);
	
	Page<MemberResponseDTO> getAllMembers(Pageable pageable);
	
	Page<MemberResponseDTO> searchByName(String name, Pageable pageable);
}
