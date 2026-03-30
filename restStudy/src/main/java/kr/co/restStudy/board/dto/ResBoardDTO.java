package kr.co.restStudy.board.dto;

import java.time.LocalDateTime;   // ⭐ 이 줄 추가
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResBoardDTO {

    private Long id;
    private String category;
    private String title;
    private String content;
    private String writerName;
    private LocalDateTime createdAt; // ⭐ 이름도 통일
    private int viewCount;
    private List<ResBoardFileDTO> files;
}