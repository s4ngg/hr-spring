package kr.co.hr.attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "근태 요청 DTO")
public class AttendanceRequestDTO {

	@Schema(description = "직원 ID", example = "1")
	private Long memberId;
	
	@Schema(description = "근무 날짜", example = "2024-01-01")
	private LocalDate workDate;
	
	@Schema(description = "출근 시간", example = "09:00:00")
	private LocalTime checkIn;
	
	@Schema(description = "퇴근 시간", example = "18:00:00")
	private LocalTime checkOut;
	
	@Schema(description = "근무 시간", example = "8.0")
	private Double workHours;
	
	@Schema(description = "근태 상태 (정상/지각/조퇴/결근)", example ="정상")
	private String status;
}
