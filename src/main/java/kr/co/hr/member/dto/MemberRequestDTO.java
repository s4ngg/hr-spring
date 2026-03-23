package kr.co.hr.member.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDTO {

	private Long departmentId;
	private String employeeNo;
	private String name;
	private String email;
	private String password;
	private String role;
	private String employType;
	private LocalDate hireDate;
	private String profileImage;
	private String status;
}
