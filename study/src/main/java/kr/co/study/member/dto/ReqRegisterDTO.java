package kr.co.study.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원가입 요청을 받는 DTO입니다.
 * 회원가입에 필요한 정보(id, name, password, ...)를 받습니다.
 */

@Getter	//Getter 생성
@Setter	//Setter 생성
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드가 다 있는 생성자
@ToString // 모든 필드에 있는 값을 문자열 형태로 반환
@Data // (권장하지 않음) Getter+Setter+ToString+, ... 이것저것 다 합쳐진 어노테이션 캡슐화, 정보은닉에 성능이 좋지 않음
public class ReqRegisterDTO {
	private String userName;
	private String userId;
	private String email;
	private String password;
	private String passwordCheck;
}
