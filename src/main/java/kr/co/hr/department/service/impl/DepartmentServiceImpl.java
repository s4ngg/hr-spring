package kr.co.hr.department.service.impl;

import java.util.List;

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

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public List<DepartmentResponseDto> getDepartmentList() {
        return DepartmentResponseDto.fromList(
                departmentRepository.findAll(),
                memberRepository::countByDepartment_DepartmentId
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<DepartmentResponseDto> searchDepartment(String deptName) {
        List<Department> result = departmentRepository.findByDeptNameContaining(deptName);

        if (result.isEmpty()) {
            throw new RuntimeException("검색 결과가 없습니다.");
        }

        return DepartmentResponseDto.fromList(
                result,
                memberRepository::countByDepartment_DepartmentId
        );
    }

    @Transactional
    @Override
    public void createDepartment(DepartmentRequestDto dto) {
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
                null
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
}