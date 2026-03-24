package kr.co.hr.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.hr.department.entity.Department;
import kr.co.hr.member.entity.Member;
import lombok.Getter;

@Getter
@Schema(description = "직원 응답 DTO")
public class MemberResponseDTO {

	@Schema(description= "직원 ID", example = "1")
	private Long memberId;
	
	@Schema(description = "부서 ID", example ="1")
	private Long departmentId;
	
	@Schema(description = "부서명", example = "개발팀")
	private String deptName;
	
	@Schema(description = "사번", example = "EMP001")
	private String employeeNo;
	
	@Schema(description = "직원 이름", example = "홍길동")
	private String name;
	
	@Schema (description = "이메일", example = "hong@company.com")
	private String email;
	
	@Schema(description = "권한 (ADMIN/USER)", example = "USER")
	private String role;
	
	@Schema(description = "재직 상태 (재직/휴직/퇴직)", example = "재직")
	private String status;
	
	@Schema(description = "고용 형태 (정규직/계약직/인턴)", example = "정규직")
	private String employType;
	
	@Schema(description = "입사일", example ="2024-01-01")
	private LocalDate hireDate;
	
	@Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
	private String profileImage;
	
	@Schema(description = "생성일시", example = "2024-01-01T09:00:00")
	private LocalDateTime createdAt;
	
	@Schema(description = "수정일시", example = "2024-01-01T09:00:00")
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