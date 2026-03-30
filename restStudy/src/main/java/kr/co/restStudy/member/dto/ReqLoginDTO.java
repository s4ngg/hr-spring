package kr.co.restStudy.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="로그인 요청 DTO")
@ToString
public class ReqLoginDTO {
	
	@Schema(description="유저 아이디", example="test")
	private String userId;
	
	@Schema(description="비밀번호", example="qwer1234!")
	private String password;
}