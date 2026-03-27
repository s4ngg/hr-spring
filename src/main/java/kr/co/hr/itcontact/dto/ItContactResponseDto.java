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

    private Long contactId;
    private String name;
    private String dept;
    private String contact;
    private String email;
    private String subject;
    private String message;
    private LocalDateTime createdAt;

    // 헬퍼 메서드
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