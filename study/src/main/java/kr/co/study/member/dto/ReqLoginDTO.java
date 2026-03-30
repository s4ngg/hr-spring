package kr.co.study.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 로그인 요청 데이터를 받을 DTO입니다.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqLoginDTO {
	private String userId;
	private String password;
}