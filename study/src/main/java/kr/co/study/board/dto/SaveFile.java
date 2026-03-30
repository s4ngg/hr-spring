package kr.co.study.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveFile {
	private final String originalFileName;
	private final String storedFileName;
	private final String contentType;
	private final long fileSize;
	private final String filePath;
}
