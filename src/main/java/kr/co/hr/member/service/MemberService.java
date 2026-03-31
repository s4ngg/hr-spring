package kr.co.hr.member.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kr.co.hr.member.dto.MemberRequestDTO;
import kr.co.hr.member.dto.MemberResponseDTO;

//✅ 수정된 인터페이스
public interface MemberService {
 Page<MemberResponseDTO> getMembers(String name, Pageable pageable);
 MemberResponseDTO getMember(Long memberId);
 MemberResponseDTO createMember(MemberRequestDTO requestDTO);
 MemberResponseDTO updateMember(Long memberId, MemberRequestDTO requestDTO);
 void deleteMember(Long memberId);
}

