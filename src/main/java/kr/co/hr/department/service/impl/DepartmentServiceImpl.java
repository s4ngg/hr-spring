package kr.co.hr.department.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hr.department.dto.DepartmentRequestDto;
import kr.co.hr.department.dto.DepartmentResponseDto;
import kr.co.hr.department.entity.Department;
import kr.co.hr.department.repository.DepartmentRepository;
import kr.co.hr.department.service.DepartmentService;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.co.hr.department.dto.DepartmentRequestDto;
import kr.co.hr.department.dto.DepartmentResponseDto;
import kr.co.hr.department.entity.Department;
import kr.co.hr.department.repository.DepartmentRepository;
import kr.co.hr.department.service.DepartmentService;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final MemberRepository memberRepository;

 // 조회는 @Transactional(readOnly = true) - 읽기 전용이라 성능 최적화
    @Transactional(readOnly = true)
    @Override
    public List<DepartmentResponseDto> getDepartmentList() {
        return departmentRepository.findAll().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    @Override
    public List<DepartmentResponseDto> searchDepartment(String deptName) {
        return departmentRepository.findByDeptNameContaining(deptName).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    @Transactional
    @Override
    public void createDepartment(DepartmentRequestDto dto) {
        Department department = new Department();
  
        // create는 setter 써도 됨 (새로 만드는 거라 캡슐화 문제 없음)
        // 단, Entity에 @Setter를 없앴으니 생성자 or 빌더 사용.
        Member manager = null;
        if (dto.getManagerId() != null) {
            manager = memberRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new RuntimeException("해당 직원이 없습니다."));
        }
        Department newDept = new Department(
                null,
                manager,
                dto.getDeptCode(),
                dto.getDeptName(),
                null  // createdAt은 @PrePersist가 처리
        );
        departmentRepository.save(newDept);
    }

    @Transactional
    @Override
    public void updateDepartment(Long departmentId, DepartmentRequestDto dto) {
        Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new RuntimeException("해당 부서가 없습니다."));

        Member manager = null;
        if (dto.getManagerId() != null) {
            manager = memberRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new RuntimeException("해당 직원이 없습니다."));
        }

        // 엔티티 update 메서드 호출 - save() 없어도 더티체킹으로 자동 반영!
        department.update(dto.getDeptCode(), dto.getDeptName(), manager);
    }

    @Transactional
    @Override
    public void deleteDepartment(Long departmentId) {
    	if (!departmentRepository.existsById(departmentId)) {
            throw new RuntimeException("해당 부서가 없습니다.");
        }
        departmentRepository.deleteById(departmentId);
    }


    private DepartmentResponseDto toDto(Department department) {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setDepartmentId(department.getDepartmentId());
        dto.setDeptCode(department.getDeptCode());
        dto.setDeptName(department.getDeptName());
        dto.setCreatedAt(department.getCreatedAt());

        if (department.getManager() != null) {
            dto.setManagerName(department.getManager().getName());
        }

        return dto;
    }
}