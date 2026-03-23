package kr.co.hr.department.service.impl;

import java.util.List;
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

    @Override
    public List<DepartmentResponseDto> getDepartmentList() {
        return departmentRepository.findAll().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<DepartmentResponseDto> searchDepartment(String deptName) {
        return departmentRepository.findByDeptNameContaining(deptName).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void createDepartment(DepartmentRequestDto dto) {
        Department department = new Department();
        department.setDeptCode(dto.getDeptCode());
        department.setDeptName(dto.getDeptName());

        if (dto.getManagerId() != null) {
            Member manager = memberRepository.findById(dto.getManagerId())
                .orElseThrow(() -> new RuntimeException("해당 직원이 없습니다."));
            department.setManager(manager);
        }

        departmentRepository.save(department);
    }

    @Override
    public void updateDepartment(Long departmentId, DepartmentRequestDto dto) {
        Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new RuntimeException("해당 부서가 없습니다."));

        department.setDeptCode(dto.getDeptCode());
        department.setDeptName(dto.getDeptName());

        if (dto.getManagerId() != null) {
            Member manager = memberRepository.findById(dto.getManagerId())
                .orElseThrow(() -> new RuntimeException("해당 직원이 없습니다."));
            department.setManager(manager);
        }

        departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
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