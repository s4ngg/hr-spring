package kr.co.hr.vacation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VacationQuotaResponseDTO {
    private Long memberId;
    private Integer year;
    private Integer totalDays;
    private Integer usedDays;
    private Integer pendingDays;
    private Integer remainingDays;
}