package kr.co.restStudy.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResBoardFileDTO {
	private Long id;
	private String originalFileName;
	private String storedFileName;
	private Long fileSize;
	private String filePath;
	private String contentType;
}
