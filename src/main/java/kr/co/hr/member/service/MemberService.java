package kr.co.hr.member.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kr.co.hr.member.dto.MemberRequestDTO;
import kr.co.hr.member.dto.MemberResponseDTO;

public interface MemberService {

    Page<MemberResponseDTO> getMembers(String name, Pageable pageable);
    MemberResponseDTO getMember(Long memberId);
    MemberResponseDTO createMember(MemberRequestDTO requestDTO);
    MemberResponseDTO updateMember(Long memberId, MemberRequestDTO requestDTO);
    void deleteMember(Long memberId);
    
    // ❌ 아래 메서드들이 있다면 모두 제거
    // List<MemberResponseDTO> searchByName(String name);
    // Page<MemberResponseDTO> getAllMembers(Pageable pageable);
    // Page<MemberResponseDTO> searchByName(String name, Pageable pageable);
}