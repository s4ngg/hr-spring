package kr.co.hr.member.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hr.department.entity.Department;
import kr.co.hr.department.repository.DepartmentRepository;
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
    private final DepartmentRepository departmentRepository;

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
        Department department = null;
        if(requestDTO.getDepartmentId() != null) {
        	department = departmentRepository.findById(requestDTO.getDepartmentId())
        			.orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));
        }
        Member member = new Member();
        member.update(requestDTO, department);
        return new MemberResponseDTO(memberRepository.save(member));
    }

    // 직원 정보 수정
    @Override
    @Transactional
    public MemberResponseDTO updateMember(Long memberId, MemberRequestDTO requestDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("직원을 찾을 수 없습니다. id: " + memberId));
        Department department = null;
        if(requestDTO.getDepartmentId() != null) {
        	department = departmentRepository.findById(requestDTO.getDepartmentId())
        			.orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));
        }
        
        member.update(requestDTO, department);
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
    
    //직원 검색
    @Override
    @Transactional(readOnly = true)
    public List<MemberResponseDTO> searchByName(String name) {
        return memberRepository.findByNameContaining(name)
                .stream()
                .map(MemberResponseDTO::new)
                .toList();
    }
    
 // 전체 직원 목록 조회 (페이징 처리)
    @Override
    @Transactional(readOnly = true)
    public Page<MemberResponseDTO> getAllMembers(Pageable pageable) {
    	return memberRepository.findAll(pageable)
    			.map(MemberResponseDTO::new);
    }
    
 // 직원 이름 검색 (페이징 처리)
    @Override
    @Transactional(readOnly = true)
    public Page<MemberResponseDTO> searchByName(String name, Pageable pageable) {
        return memberRepository.findByNameContaining(name, pageable)
                .map(MemberResponseDTO::new);
    }
    
}
