package kr.co.hr.vacation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "휴가 보유 및 사용 현황 정보")

public class VacationQuotaResponseDTO {
	@Schema(description = "휴가 신청자 번호", example = "15")
	private Long memberId;
	
	@Schema(description = "휴가 기준 연도", example = "2018")
    private Integer year;
	
	@Schema(description = "총 휴가 일수", example = "20")
    private Integer totalDays;
	
	@Schema(description = "사용된 휴가 일수", example = "4")
    private Integer usedDays;
	
	@Schema(description = "승인 대기중인 휴가 일수", example = "3")
    private Integer pendingDays;
	
	@Schema(description = "현재 사용 가능한 잔여 휴가 일수", example = "11")
    private Integer remainingDays;
}