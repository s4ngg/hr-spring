package kr.co.hr.itcontact.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.hr.itcontact.entity.ItContact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "IT 문의 응답 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItContactResponseDto {

    @Schema(description = "문의 ID", example = "1")
    private Long contactId;

    @Schema(description = "문의자 이름", example = "홍길동")
    private String name;

    @Schema(description = "부서", example = "개발팀")
    private String dept;

    @Schema(description = "연락처", example = "010-0000-0000")
    private String contact;

    @Schema(description = "이메일", example = "example@nexuspro.com")
    private String email;

    @Schema(description = "제목", example = "시스템 오류 문의")
    private String subject;

    @Schema(description = "내용", example = "상세 문의 내용")
    private String message;

    @Schema(description = "문의 접수 시간", example = "2026-01-01T00:00:00")
    private LocalDateTime createdAt;

    public static ItContactResponseDto from(ItContact entity) {
        return ItContactResponseDto.builder()
                .contactId(entity.getContactId())
                .name(entity.getName())
                .dept(entity.getDept())
                .contact(entity.getContact())
                .email(entity.getEmail())
                .subject(entity.getSubject())
                .message(entity.getMessage())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static List<ItContactResponseDto> fromList(List<ItContact> list) {
        return list.stream()
                .map(ItContactResponseDto::from)
                .collect(Collectors.toList());
    }
}