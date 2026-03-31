package kr.co.hr.department.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hr.department.dto.DepartmentRequestDto;
import kr.co.hr.department.dto.DepartmentResponseDto;
import kr.co.hr.department.entity.Department;
import kr.co.hr.department.repository.DepartmentRepository;
import kr.co.hr.department.service.DepartmentService;
import kr.co.hr.global.exception.BusinessException;
import kr.co.hr.global.exception.ErrorCode;
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
        	throw new BusinessException(ErrorCode.SEARCH_RESULT_NOT_FOUND);
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
            		.orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        }
        Department newDept = new Department(
                null,
                manager,
                dto.getDeptCode(),
                dto.getDeptName(),
                null
        );
        departmentRepository.save(newDept);
        if (manager != null) {
            manager.updateDepartment(newDept);
        }
    }

    @Transactional
    @Override
    public void updateDepartment(Long departmentId, DepartmentRequestDto dto) {
        Department department = departmentRepository.findById(departmentId)
        		.orElseThrow(() -> new BusinessException(ErrorCode.DEPARTMENT_NOT_FOUND));

        Member manager = null;
        if (dto.getManagerId() != null) {
            manager = memberRepository.findById(dto.getManagerId())
            		.orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        }

        department.update(dto.getDeptCode(), dto.getDeptName(), manager);
        if (manager != null) {
            manager.updateDepartment(department);
        }
    }

    @Transactional
    @Override
    public void deleteDepartment(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
        	throw new BusinessException(ErrorCode.DEPARTMENT_NOT_FOUND);
        }
        departmentRepository.deleteById(departmentId);
    }
}