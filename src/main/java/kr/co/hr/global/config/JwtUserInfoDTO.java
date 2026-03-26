package kr.co.hr.global.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtUserInfoDTO {
	private Long memberId;    // DB의 member_id (PK)
    private String loginId;   // 사번 + 이메일 합친 값
    private String name;
    private String role;	

}
