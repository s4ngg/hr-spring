package kr.co.hr.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.co.hr.department.entity.Department;
import kr.co.hr.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDTO {

	private Long memberId;
	private Long departmentId;
	private String deptName;
	private String employeeNo;
	private String name;
	private String email;
	private String role;
	private String status;
	private String employType;
	private LocalDate hireDate;
	private String profileImage;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public MemberResponseDTO(Member member) {
		this.memberId = member.getMemberId();
		this.departmentId = member.getDepartment() != null
				? member.getDepartment().getDepartmentId()
				: null;
		this.deptName = member.getDepartment() != null
				? member.getDepartment().getDeptName()
				: null;
		this.employeeNo = member.getEmployeeNo();
		this.name = member.getName();
		this.email = member.getEmail();
		this.role = member.getRole();
		this.status = member.getStatus();
		this.employType = member.getEmployType();
		this.hireDate = member.getHireDate();
		this.profileImage = member.getProfileImage();
		this.createdAt = member.getCreatedAt();
		this.updatedAt = member.getUpdatedAt();
	}
}