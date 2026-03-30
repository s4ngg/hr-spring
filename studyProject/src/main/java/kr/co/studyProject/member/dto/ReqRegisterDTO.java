package kr.co.studyProject.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReqRegisterDTO {
	private String userName;
	private String email;
	private String nickName;
	private String password;
	private String passwordCheck;
	private String phoneNumber;
}
