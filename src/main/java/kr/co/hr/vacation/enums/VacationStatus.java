package kr.co.hr.vacation.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VacationStatus {
	PENDING("대기"),
    APPROVED("승인"),
    REJECTED("반려");
	
	private final String description;
}
