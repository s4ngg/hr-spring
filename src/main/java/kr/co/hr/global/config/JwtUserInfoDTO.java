package kr.co.hr.global.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtUserInfoDTO {
	private String loginId;
    private String name;
    private String role;	// 추후 칠요하면 추가 
//    private String email;	// 추후 필요하면 추가
}
