package kr.co.study.member.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResLoginDTO {
	private Long id;
	private String userId;
	private String userName;
	private String email;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
