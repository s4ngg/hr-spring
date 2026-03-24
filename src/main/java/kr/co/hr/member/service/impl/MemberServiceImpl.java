package kr.co.hr.member.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hr.member.dto.MemberRequestDTO;
import kr.co.hr.member.dto.MemberResponseDTO;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;
import kr.co.hr.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 전체 직원 조회
    @Override
    @Transactional(readOnly = true)
    public List<MemberResponseDTO> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 단일 직원 조회
    @Override
    @Transactional(readOnly = true)
    public MemberResponseDTO getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("직원을 찾을 수 없습니다. id: " + memberId));
        return new MemberResponseDTO(member);
    }

    // 직원 등록
    @Override
    @Transactional
    public MemberResponseDTO createMember(MemberRequestDTO requestDTO) {
        Member member = new Member();
        member.setEmployeeNo(requestDTO.getEmployeeNo());
        member.setName(requestDTO.getName());
        member.setEmail(requestDTO.getEmail());
        member.setPassword(requestDTO.getPassword());
        member.setRole(requestDTO.getRole());
        member.setStatus(requestDTO.getStatus());
        member.setEmployType(requestDTO.getEmployType());
        member.setHireDate(requestDTO.getHireDate());
        member.setProfileImage(requestDTO.getProfileImage());
        return new MemberResponseDTO(memberRepository.save(member));
    }

    // 직원 정보 수정
    @Override
    @Transactional
    public MemberResponseDTO updateMember(Long memberId, MemberRequestDTO requestDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("직원을 찾을 수 없습니다. id: " + memberId));
        member.setName(requestDTO.getName());
        member.setEmail(requestDTO.getEmail());
        member.setRole(requestDTO.getRole());
        member.setStatus(requestDTO.getStatus());
        member.setEmployType(requestDTO.getEmployType());
        member.setHireDate(requestDTO.getHireDate());
        member.setProfileImage(requestDTO.getProfileImage());
        return new MemberResponseDTO(member);
    }

    // 직원 삭제
    @Override
    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("직원을 찾을 수 없습니다. id: " + memberId));
        memberRepository.delete(member);
    }
}