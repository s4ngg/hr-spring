package kr.co.hr.vacation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VacationAdminRequestDTO {
	private String status;      // 승인 또는 거절 
	private String adminComment; // 반려시 사유 
}
