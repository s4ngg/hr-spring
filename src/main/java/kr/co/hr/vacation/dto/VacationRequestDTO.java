package kr.co.hr.vacation.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "휴가 신청 요청 ")
public class VacationRequestDTO {
	
	@Schema(description = "휴가 신청자 번호", example = "15")
	private Long memberId;
	
	@Schema(description = "휴가 종류", example = "병가,연차")
	private String vacationType;
	
	@Schema(description = "휴가 시작일", example = "2026-03-25")
	private LocalDate startDate;
	
	@Schema(description = "휴가 종료일", example = "2026-03-28")
	private LocalDate endDate;
	
	@Schema(description = "사유", example = "힘들어서")
	private String reason;
	
	@Schema(description = "대리 신청자 번호", example = "훈이")
	private Long proxyMemberId;
	
	@Schema(description = "남은 휴가일수", example = "5")
	private Long days;
}
