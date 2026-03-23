package kr.co.hr.vacation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor  
@AllArgsConstructor
@Schema(description = "휴가 내역 응답 정보")
public class VacationResponseDTO {
	
	@Schema(description = "휴가 번호", example = "12")
	private Long vacationId; 		// 수정을 대비한 고유 ID
	
	@Schema(description = "신청자 이름", example = "홍길동")
	private String memberName; 		// 신청자 이름
	
	@Schema(description = "휴가 종류", example = "연차,병가,육아휴직")
	private String vacationType; 	//휴가 종류
	
	@Schema(description = "휴가 시작일", example = "2026-03-23")
	private LocalDate startDate; 	//시작일
	
	@Schema(description = "휴가 종료일", example = "2026-03-30")
	private LocalDate endDate; 		//종료일 
	
	@Schema(description = "휴가 일수", example = "8")
	private Integer days; 			//휴가일수
	
	@Schema(description = "휴가 상태", example = "보류중,진행중,거절")
	private String status;			//휴가상태
	
	@Schema(description = "신청 일시", example = "2026-03-21")
	private LocalDateTime createdAt; // 신청일
	

}
