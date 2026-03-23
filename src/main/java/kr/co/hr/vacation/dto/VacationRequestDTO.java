package kr.co.hr.vacation.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VacationRequestDTO {
	private Long memberId;
	private String type;
	
	private LocalDate startDate;
	private LocalDate endDate;
	
	private String reason;
	
	private Long proxyMemberId;
}
