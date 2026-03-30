package kr.co.hr.member.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kr.co.hr.member.dto.MemberRequestDTO;
import kr.co.hr.member.dto.MemberResponseDTO;

public interface MemberService {

    // 전체 조회 + 이름 검색 통합 (페이징)
    Page<MemberResponseDTO> getMembers(String name, Pageable pageable);

    MemberResponseDTO getMember(Long memberId);

    MemberResponseDTO createMember(MemberRequestDTO requestDTO);

    MemberResponseDTO updateMember(Long memberId, MemberRequestDTO requestDTO);

    void deleteMember(Long memberId);
}