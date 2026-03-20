package kr.co.studyProject.member.dto;

import java.time.LocalDateTime;

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
public class ResLoginDTO {
	private Long id;
	private String userId;
	private String userName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
