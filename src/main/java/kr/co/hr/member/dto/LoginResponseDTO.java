package kr.co.hr.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDTO {
	private Long memberId;
	private String employeeNo;
	private String name;
	private String role;
	private String deptName;

}
